package ru.mpei.latushkina.fqw.service.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.persistence.ChartPointMapping;
import ru.mpei.latushkina.fqw.repository.ChartPointRepository;
import ru.mpei.latushkina.fqw.service.graph.interfaces.GraphPoints;

import java.util.List;

@Service
public class ExtractPointsService implements GraphPoints<List<ChartPoint>> {
    private final ChartPointMapping chartPointMapping;
    private final ChartPointRepository chartPointRepository;

    @Autowired
    public ExtractPointsService(ChartPointMapping chartPointMapping, ChartPointRepository chartPointRepository) {
        this.chartPointMapping = chartPointMapping;
        this.chartPointRepository = chartPointRepository;
    }

    public List<ChartPoint> getGraphPointsBySource(List<Source> sources) {
        var listSources = sources.stream().map(Source::getName).toList();

        var repo = chartPointRepository.findBySourceIn(listSources);
        var res = chartPointMapping.mapToDto(repo);

        return res;
    }

    public List<ChartPoint> getGraphPointsBySourceAndPeriod(List<Source> sources, Double timeBegin, Double timeEnd) {
        var listSources = sources.stream().map(Source::getName).toList();

        var repo = chartPointRepository.findBySourceInAndTimeBetween(listSources, timeBegin, timeEnd);
        var res = chartPointMapping.mapToDto(repo);

        return res;
    }
}
