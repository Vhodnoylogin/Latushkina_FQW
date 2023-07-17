package ru.mpei.latushkina.fqw.service.fourier;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Point;
import ru.mpei.latushkina.fqw.model.dto.point.Source;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FourierFilterService {
    @Value("${fourier.filter.cutoff-frequency}")
    private final double cutOffFrequency = 5.;

    private static Complex[] applyFourierTransform(double[] values) {
        var transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return transformer.transform(values, TransformType.FORWARD);
    }

    private static double[] applyFrequencyFilter(Complex[] complexValues, double[] time, double cutOffFrequency) {
        int arrSize = time.length;

        if (arrSize == 1) {
            return new double[]{complexValues[0].getReal()};
        }

        var frequencies = new double[arrSize];
        for (int i = 0; i < arrSize - 1; i++) {
            frequencies[i] = Math.abs(i / (time[i + 1] - time[i]) / arrSize);
        }
        frequencies[arrSize - 1] = Math.abs((arrSize - 1) / (time[arrSize - 2] - time[arrSize - 1]) / arrSize);

        var realArr = Arrays.stream(complexValues)
                .map(Complex::getReal)
                .mapToDouble(Double::doubleValue)
                .toArray();

        for (int i = 0; i < realArr.length; i++) {
            if (frequencies[i] > cutOffFrequency) {
                realArr[i] = 0;
            }
        }

        return realArr;
    }

    private static Point[] applyInverseFourierTransform(double[] realValues, double[] time) {
        int n = realValues.length;
        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        var filteredValues = transformer.transform(realValues, TransformType.INVERSE);

        Point[] filteredPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            filteredPoints[i] = new Point(time[i], filteredValues[i].getReal());
        }

        return filteredPoints;
    }

    public List<Point> applyPointFilter(List<Point> points, double cutOffFrequency) {
        var values = points.stream()
                .map(Point::getValue)
                .mapToDouble(Double::doubleValue)
                .toArray();
        var time = points.stream()
                .map(Point::getTime)
                .mapToDouble(Double::doubleValue)
                .toArray();

        /**
         * Ошибка "30 is not a power of 2, consider padding for fix" возникает потому, что библиотека Apache Commons Math требует,
         * чтобы длина массива, на котором применяется преобразование Фурье, была степенью двойки.
         */
        int originalSize = points.size();
        int newSize = 1 << (int) Math.ceil(Math.log(originalSize) / Math.log(2));
        double[] paddedValues = new double[newSize];
        System.arraycopy(values, 0, paddedValues, 0, originalSize);
        double[] paddedTime = new double[newSize];
        System.arraycopy(time, 0, paddedTime, 0, originalSize);

        var fourier = applyFourierTransform(paddedValues);
        var filt = applyFrequencyFilter(fourier, paddedTime, cutOffFrequency);
        var res = applyInverseFourierTransform(filt, paddedTime);

        return Arrays.stream(res).toList();
    }

    public List<ChartPoint> applyFilter(List<ChartPoint> points) {
        return points.stream()
                .map(x -> new ChartPoint(
                        Source.makeSource(x.getSource().getDescription() + "_RMS"),
                        applyPointFilter(x.getPoints(), cutOffFrequency)
                ))
                .toList();
    }
}
