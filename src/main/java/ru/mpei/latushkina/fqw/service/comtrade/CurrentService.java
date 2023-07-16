package ru.mpei.latushkina.fqw.service.comtrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Point;
import ru.mpei.latushkina.fqw.persistence.ChartPointMapping;
import ru.mpei.latushkina.fqw.repository.ChartPointRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrentService {
    private final ChartPointMapping chartPointMapping;
    private final ChartPointRepository chartPointRepository;


    @Autowired
    public CurrentService(ChartPointMapping chartPointMapping, ChartPointRepository chartPointRepository) {
        this.chartPointMapping = chartPointMapping;
        this.chartPointRepository = chartPointRepository;
    }

    public List<String> getAllSources() {
        return chartPointRepository.getDistinctBySource();
    }

    public Map<String, List<Double>> getShortCircuit(Double settingCurrent) {
        var entities = chartPointRepository.findByValueGreaterThan(settingCurrent);
        var chartPoints = chartPointMapping.mapToDto(entities);

        Map<String, List<Double>> res = new HashMap<>();
        for (ChartPoint chartPoint : chartPoints) {
            var listOfTime = chartPoint
                    .getPoints()
                    .stream()
                    .map(Point::getTime)
                    .toList();
            res.put(chartPoint.getSource().getDescription(), listOfTime);
        }

        return res;
    }
}
