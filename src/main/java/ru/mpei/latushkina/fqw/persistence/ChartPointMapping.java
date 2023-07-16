package ru.mpei.latushkina.fqw.persistence;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.mpei.latushkina.fqw.model.jra.ChartPointEntity;
import ru.mpei.latushkina.fqw.model.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.point.Point;
import ru.mpei.latushkina.fqw.model.point.Source;
import ru.mpei.latushkina.fqw.persistence.common.DtoEntityMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChartPointMapping implements DtoEntityMapper<List<ChartPoint>, List<ChartPointEntity>> {
    public static List<ChartPoint> entityToCartPoint(List<ChartPointEntity> listEntities) {
        Map<Source, List<Point>> dataPoints = new HashMap<>();
        for (ChartPointEntity chartPointEntity : listEntities) {
            var source = new Source(chartPointEntity.getSource());
            var point = new Point(chartPointEntity.getTime(), chartPointEntity.getValue());

            var list = dataPoints.getOrDefault(source, new ArrayList<>());
            list.add(point);
            dataPoints.put(source, list);
        }
        return ChartPoint.fromMap(dataPoints);
    }

    @Override
    public List<ChartPointEntity> mapToEntity(List<ChartPoint> listOfDto) {
        return listOfDto.stream()
                .flatMap(x -> x.getPoints()
                        .stream()
                        .map(y -> Pair.of(
                                x.getSource(),
                                y
                        ))
                )
                .map(x -> new ChartPointEntity(
                        x.getFirst().getName(),
                        x.getSecond().getTime(),
                        x.getSecond().getValue()
                ))
                .toList();
    }

    @Override
    public List<ChartPoint> mapToDto(List<ChartPointEntity> entities) {
        return entityToCartPoint(entities);
    }
}
