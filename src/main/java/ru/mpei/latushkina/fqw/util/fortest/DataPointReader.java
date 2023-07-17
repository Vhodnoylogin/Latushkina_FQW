package ru.mpei.latushkina.fqw.util.fortest;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.jra.ChartPointEntity;
import ru.mpei.latushkina.fqw.persistence.ChartPointMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class DataPointReader {
    private static final String filePath = "test/source/src.csv";
    public static final ClassPathResource RESOURCE = new ClassPathResource(filePath);


    @SneakyThrows
    public static List<ChartPoint> readDataPointsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            var list = br.lines()
                    .map(x -> x.split(","))
                    .map(x -> new ChartPointEntity(
                            null,
                            x[0].trim(),
                            Double.parseDouble(x[1].trim()),
                            Double.parseDouble(x[2].trim())
                    ))
                    .toList();
            return ChartPointMapping.entityToCartPoint(list);
        }
    }


}