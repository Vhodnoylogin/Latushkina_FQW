package ru.mpei.latushkina.fqw.service;

import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.latushkina.fqw.model.Measurements;
import ru.mpei.latushkina.fqw.model.MetaInf;
import ru.mpei.latushkina.fqw.model.measurement.AnalogMeas;
import ru.mpei.latushkina.fqw.model.measurement.DigitalMeas;
import ru.mpei.latushkina.fqw.repository.MeasurementsRepository;
import ru.mpei.latushkina.fqw.util.ParserUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsvService {

    private final MeasurementsRepository measurementsRepository;
    private final RmsFilterService filterService;
    @Value("${parser.digitalSuffix}")
    private final String digitalSuffix = "BOOL";
    @Value("${parser.csv.analogKeyword}")
    private final String analogKeyword = "branch";

    @Autowired
    public CsvService(MeasurementsRepository measurementsRepository, RmsFilterService filterService) {
        this.measurementsRepository = measurementsRepository;
        this.filterService = filterService;
    }

    @SneakyThrows
    public void parseFile(MultipartFile dat) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(dat.getInputStream(), StandardCharsets.UTF_8.newDecoder()));

        List<Measurements> measurements = parseData(bufferedReader, parseHeader(bufferedReader.readLine()));

        bufferedReader.close();

        log.info("read data complete");
        int N = filterService.rmsByPhase(measurements, 50);
        log.info("rms calculate complete with N={}", N);

        MetaInf metaInf = new MetaInf(N, 50);
        if (dat.getOriginalFilename() != null)
            metaInf.setName(dat.getOriginalFilename().split("\\.csv")[0]);
        metaInf.setFile1Name(dat.getOriginalFilename());
        metaInf.setAnalog(measurements.get(0).getAnalogMeas().size());
        metaInf.setDigital(measurements.get(0).getDigitalMeas().size());
        metaInf.setType("csv");

        measurementsRepository.saveMeas(measurements, metaInf);
    }

    private HeaderData parseHeader(String header) {
        HeaderData headerData = new HeaderData();

        String[] s = header.split(",");

        for (int i = 1; i < s.length; i++) {
            Type type = getType(s[i]);
            headerData.types.add(type);
            headerData.names.add(type == Type.DIGITAL ? getName(s[i]) + "_" + digitalSuffix : getName(s[i]));
        }

        return headerData;
    }

    private String getName(String s) {
        String[] h = s.split("\\|");
        return ParserUtil.toCorrectStr(h[h.length - 1]);
    }

    private Type getType(String s) {
        return s.toLowerCase().contains(analogKeyword) ? Type.ANALOG : Type.DIGITAL;
    }

    @SneakyThrows
    private List<Measurements> parseData(BufferedReader bufferedReader, HeaderData headerData) {
        String line;
        List<Measurements> measurements = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            Measurements m = new Measurements();
            String[] l = line.split(",");
            m.setTime(Double.parseDouble(l[0]));

            for (int i = 1; i < l.length; i++) {
                switch (headerData.types.get(i - 1)) {
                    case DIGITAL -> m.getDigitalMeas().add(
                            new DigitalMeas(headerData.names.get(i - 1), l[i].equals("1.0")));
                    case ANALOG -> m.getAnalogMeas().add(
                            new AnalogMeas(headerData.names.get(i - 1), Double.parseDouble(l[i])));
                }
            }
            measurements.add(m);
        }
        return measurements;
    }

    private enum Type {
        ANALOG, DIGITAL
    }

    @ToString
    private static class HeaderData {
        private final List<Type> types = new ArrayList<>();
        private final List<String> names = new ArrayList<>();
    }

}
