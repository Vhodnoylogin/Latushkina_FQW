package ru.mpei.latushkina.fqw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.Measurements;
import ru.mpei.latushkina.fqw.model.measurement.AnalogMeas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class RmsFilterService {
    @Value("${filter.rmsSuffix}")
    private final String RMS_SUFFIX = "RMS";

    private int getMinPoints(double freq, double dt) {
        return (int) (1 / dt / freq);
    }


    public int rmsByPhase(List<Measurements> measurementList, double freq) {

        if (measurementList.size() < 2) {
            return -1;
        }

        int N = getMinPoints(freq, measurementList.get(1).getTime() - measurementList.get(0).getTime());

        if (measurementList.size() < N) {
            return -1;
        }

        List<String> names = measurementList.get(0).getAnalogMeas().stream()
                .map(AnalogMeas::getName).toList();
        Map<String, RmsCalc> rms = new HashMap<>();

        names.forEach(name -> rms.put(name, new RmsCalc(N)));

        for (Measurements m : measurementList) {
            m.setRmsMeas(new ArrayList<>());
            for (AnalogMeas am : m.getAnalogMeas()) {
                m.getRmsMeas().add(
                        new AnalogMeas(
                                am.getName() + "_" + RMS_SUFFIX,
                                rms.get(am.getName()).calcNext(am.getVal())
                        )
                );
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
