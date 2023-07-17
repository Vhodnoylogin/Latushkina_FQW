package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
@Data
@NoArgsConstructor
public class CfgFile {
    private String name;
    private List<CfgAnalog> analogChannels;
    private List<CfgDigital> digitalChannels;
    private Map<Integer, DataType> dataTypes;
    private double freq;
    private List<CfgSampling> samplingsFreq;
    private String dateStart;
    private String dateEnd;
    private FileType fileType;

    @SneakyThrows
    public static CfgFile readCfg(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            var cfg = new CfgFile();
            cfg.setName(bufferedReader.readLine());

            String[] line = bufferedReader.readLine().split(",");

            int aCount;
            int dCount;

            String value = line[1];
            if (value.contains("A")) {
                aCount = Integer.parseInt(value.substring(0, value.length() - 1));
                value = line[2];
                dCount = Integer.parseInt(value.substring(0, value.length() - 1));
            } else {
                dCount = Integer.parseInt(value.substring(0, value.length() - 1));
                value = line[2];
                aCount = Integer.parseInt(value.substring(0, value.length() - 1));
            }

            List<CfgAnalog> analogCfgList = new ArrayList<>();
            List<CfgDigital> digitalCfgList = new ArrayList<>();
            Map<Integer, CfgFile.DataType> dataTypes = new HashMap<>(aCount + dCount);

            for (int i = 0; i < aCount + dCount; i++) {
                line = bufferedReader.readLine().split(",");
                if (line.length == 13) {
                    analogCfgList.add(CfgAnalog.lineToCfg(line));
                    dataTypes.put(i, CfgFile.DataType.A);
                } else if (line.length == 5) {
                    digitalCfgList.add(CfgDigital.lineToCfg(line));
                    dataTypes.put(i, CfgFile.DataType.D);
                } else {
                    log.error("unsupported line type {}", Arrays.toString(line));
                }
            }
            cfg.setAnalogChannels(analogCfgList);
            cfg.setDigitalChannels(digitalCfgList);
            cfg.setDataTypes(dataTypes);
            cfg.setFreq(Double.parseDouble(bufferedReader.readLine()));

            var sCount = Integer.parseInt(bufferedReader.readLine());
            List<CfgSampling> samplingCfgList = new ArrayList<>();
            for (int i = 0; i < sCount; i++) {
                line = bufferedReader.readLine().split(",");
                var samplingCfg = new CfgSampling();
                samplingCfg.setSamplingFreq(Double.parseDouble(line[0]));
                samplingCfg.setLastNumber(Integer.parseInt(line[1]));
                samplingCfgList.add(samplingCfg);
            }
            cfg.setSamplingsFreq(samplingCfgList);
            cfg.setDateStart(bufferedReader.readLine());
            cfg.setDateEnd(bufferedReader.readLine());

            cfg.setFileType(bufferedReader.readLine().contains("BINARY") ? CfgFile.FileType.BINARY : CfgFile.FileType.ASCII);

            return cfg;
        }
    }

    public enum FileType {
        ASCII, BINARY
    }

    public enum DataType {
        A, D
    }
}
