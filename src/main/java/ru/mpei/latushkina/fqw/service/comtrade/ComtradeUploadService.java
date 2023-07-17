package ru.mpei.latushkina.fqw.service.comtrade;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.persistence.ChartPointMapping;
import ru.mpei.latushkina.fqw.repository.ChartPointRepository;
import ru.mpei.latushkina.fqw.service.comtrade.dat.ComtradeParseService;

import java.util.List;

@Service
public class ComtradeUploadService {
    private final ChartPointMapping chartPointMapping;

    private final ComtradeParseService comtradeParseService;
    private final ChartPointRepository chartPointRepository;

    @Autowired
    public ComtradeUploadService(ChartPointMapping chartPointMapping, ComtradeParseService comtradeParseService, ChartPointRepository chartPointRepository) {
        this.chartPointMapping = chartPointMapping;
        this.comtradeParseService = comtradeParseService;
        this.chartPointRepository = chartPointRepository;
    }


    @SneakyThrows
    public List<ChartPoint> upload(
            @RequestParam MultipartFile cfg,
            @RequestParam MultipartFile dat) {
//        File file = DataPointReader.RESOURCE.getFile();
//        var dtoList = DataPointReader.readDataPointsFromFile(file.getPath());
        var dtoList = comtradeParseService.parseComtradeFile(
                cfg.getInputStream(),
                dat.getInputStream()
        );


        var entities = chartPointMapping.mapToEntity(dtoList);

        var res = chartPointRepository.saveAll(entities);

        var savedDto = chartPointMapping.mapToDto(res);

        return savedDto;
    }
}
