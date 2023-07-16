package ru.mpei.latushkina.fqw.model.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateChart {
    private Source source;
    private List<Point> points;

    public static List<DateChart> fromMap(Map<Source, List<Point>> map) {
        return map.entrySet().stream()
                .map(x -> new DateChart(
                        x.getKey(),
                        x.getValue()
                ))
                .toList();
    }
}
