package ru.mpei.latushkina.fqw.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.latushkina.fqw.models.Measurements;
import ru.mpei.latushkina.fqw.models.MetaInf;
import ru.mpei.latushkina.fqw.models.comtrade.AnalogCfg;
import ru.mpei.latushkina.fqw.models.comtrade.Cfg;
import ru.mpei.latushkina.fqw.models.comtrade.DigitalCfg;
import ru.mpei.latushkina.fqw.models.comtrade.SamplingCfg;
import ru.mpei.latushkina.fqw.models.measurement.AnalogMeas;
import ru.mpei.latushkina.fqw.models.measurement.DigitalMeas;
import ru.mpei.latushkina.fqw.repository.MeasurementsRepository;
import ru.mpei.latushkina.fqw.utils.ParserComtrade;
import ru.mpei.latushkina.fqw.utils.ParserUtil;
import ru.mpei.latushkina.fqw.utils.RmsFilter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class ComtradeService {

    private String charSetName = "Windows-1251";
    private String digitalSuffix = "BOOL";
    private final MeasurementsRepository measurementsRepository;
    private final RmsFilter filterService;

    @Autowired
    public ComtradeService(MeasurementsRepository measurementsRepository, RmsFilter filterService) {
        this.measurementsRepository = measurementsRepository;
        this.filterService = filterService;
    }

    @SneakyThrows
    public void parseFile(MultipartFile cfg, MultipartFile dat) {
        var res = ParserComtrade.parseFile(
                cfg.getInputStream(),
                dat.getInputStream(),
                cfg.getOriginalFilename(),
                dat.getOriginalFilename()
        );
        measurementsRepository.saveMeas(res.first, res.second);
    }
}
