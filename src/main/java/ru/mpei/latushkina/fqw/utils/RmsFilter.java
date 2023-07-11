package ru.mpei.latushkina.fqw.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.models.Measurements;
import ru.mpei.latushkina.fqw.models.measurement.AnalogMeas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RmsFilter {

    private static final String RMS_SUFFIX = "RMS";

    private static int getMinPoints(double freq, double dt) {
        log.debug("dt = {}", dt);
        return (int) (1 / dt / freq);
    }


    public static int rmsByPhase(List<Measurements> measurementList, double freq) {

        if (measurementList.size() < 2) {
            log.warn("measurementList.size() < 2");
            return -1;
        }

        int N = getMinPoints(freq, measurementList.get(1).getTime() - measurementList.get(0).getTime());

        if (measurementList.size() < N) {
            log.warn("measurementList.size() < N");
            return -1;
        }

        Map<String, RmsCalc> rms = measurementList.get(0).getAnalogMeas()
                .stream()
                .map(AnalogMeas::getName)
                .collect(Collectors.toMap(
                        name -> name, // ключ - каждый элемент списка
                        name -> new RmsCalc(N) // значение - длина каждого элемента списка
                ));

        for (Measurements m : measurementList) {
            m.setRmsMeas(new ArrayList<>());
            for (AnalogMeas am : m.getAnalogMeas()) {
                m.getRmsMeas().add(new AnalogMeas(am.getName() + "_" + RMS_SUFFIX,
                        rms.get(am.getName()).calcNext(am.getVal())
                ));
            }

        }

        return N;
    }

    private static class RmsCalc {
        private final double[] buffer;
        private int i = 0;

        public RmsCalc(int N) {
            this.buffer = new double[N];
        }

        public double calcNext(double val) {
            if (i >= buffer.length) i = 0;
            buffer[i] = val;
            i++;
            return rms();
        }

        private double rms() {
            double x = 0;
            for (double value : buffer) {
                x = x + Math.pow(value, 2);
            }
            return Math.sqrt(x / buffer.length);
        }

    }

}
