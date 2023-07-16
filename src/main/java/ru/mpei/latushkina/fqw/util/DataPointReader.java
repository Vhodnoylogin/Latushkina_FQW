package ru.mpei.latushkina.fqw.util;

import lombok.SneakyThrows;
import ru.mpei.latushkina.fqw.model.point.DateChart;
import ru.mpei.latushkina.fqw.model.point.Point;
import ru.mpei.latushkina.fqw.model.point.Source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPointReader {

    @SneakyThrows
    public static List<DateChart> readDataPointsFromFile(String filePath) {
        Map<Source, List<Point>> dataPoints = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String sourceName = parts[0].trim();
                    Double time = Double.parseDouble(parts[1].trim());
                    Double value = Double.parseDouble(parts[2].trim());

                    var source = new Source(sourceName);
                    var point = new Point(time, value);

                    var list = dataPoints.getOrDefault(source, new ArrayList<>());
                    list.add(point);
                    dataPoints.put(source, list);
                }
            }
        }

        return DateChart.fromMap(dataPoints);
    }

}