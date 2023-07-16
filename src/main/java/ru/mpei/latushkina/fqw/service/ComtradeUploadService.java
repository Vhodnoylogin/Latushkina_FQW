package ru.mpei.latushkina.fqw.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.persistence.ChartPointMapping;
import ru.mpei.latushkina.fqw.repository.ChartPointRepository;
import ru.mpei.latushkina.fqw.util.fortest.DataPointReader;

import java.io.File;
import java.util.List;

@Service
public class ComtradeUploadService {
    private final ChartPointMapping chartPointMapping;
    private final ChartPointRepository chartPointRepository;

    @Autowired
    public ComtradeUploadService(ChartPointMapping chartPointMapping, ChartPointRepository chartPointRepository) {
        this.chartPointMapping = chartPointMapping;
        this.chartPointRepository = chartPointRepository;
    }


    @SneakyThrows
    public List<ChartPoint> upload(
            @RequestParam MultipartFile cfg,
            @RequestParam MultipartFile dat) {
        File file = DataPointReader.RESOURCE.getFile();
        var dtoList = DataPointReader.readDataPointsFromFile(file.getPath());
        var entities = chartPointMapping.mapToEntity(dtoList);

        var res = chartPointRepository.saveAll(entities);

        var savedDto = chartPointMapping.mapToDto(res);

        return savedDto;
    }
}
