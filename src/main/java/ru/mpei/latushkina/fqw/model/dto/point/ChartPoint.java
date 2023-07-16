package ru.mpei.latushkina.fqw.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartPoint {
    private Source source;
    private List<Point> points;

    public static List<ChartPoint> fromMap(Map<Source, List<Point>> map) {
        return map.entrySet().stream()
                .map(x -> new ChartPoint(
                        x.getKey(),
                        x.getValue()
                ))
                .toList();
    }
}
