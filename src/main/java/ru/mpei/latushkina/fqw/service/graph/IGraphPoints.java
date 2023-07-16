package ru.mpei.latushkina.fqw.service.graph;

import ru.mpei.latushkina.fqw.model.dto.point.Source;

import java.util.List;

public interface IGraphPoints<T> {
    T getGraphPointsBySource(List<Source> sources);

    T getGraphPointsBySourceAndPeriod(List<Source> sources, Double timeBegin, Double timeEnd);
}
