package ru.mpei.latushkina.fqw.service.comtrade.dat;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.comtrade.cfg.CfgFile;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Point;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.util.ParserUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComtradeParseService {
    private final static int TWO = 2;
    private final static int FOUR = TWO * TWO;
    private final static int SIXTEEN = FOUR * FOUR;

    @SneakyThrows
    private static Map<Source, List<Point>> binaryRead(InputStream inputStream, CfgFile cfg) {
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();

        int i = 0;

        Map<Source, List<Point>> channelPoints = new HashMap<>();

        while (i < bytes.length) {
            i = i + FOUR;
            var time = (ParserUtil.bArrTo32UInt(bytes, i)) / 1_000_000d;
            i = i + FOUR;

            for (var aCh : cfg.getAnalogChannels()) {
                var name = aCh.getSource(null);
                var val = aCh.getCurrentValue((double) ParserUtil.bArrTo16Int(bytes, i));

                var points = channelPoints.getOrDefault(name, new ArrayList<>());
                points.add(new Point(time, val));
                channelPoints.put(name, points);

                i = i + TWO;
            }

            for (int j = 0; j < -Math.floorDiv(-cfg.getDigitalChannels().size(), SIXTEEN); j++) {
                List<Boolean> arr = ParserUtil.bArrTo16Bit(bytes, i);
                for (int k = 0; k < SIXTEEN; k++) {
                    if (cfg.getDigitalChannels().size() < j * SIXTEEN + k) break;

                    var name = cfg.getDigitalChannels().get(j * SIXTEEN + k).getSource(null);
                    var val = arr.get(k) ? 1. : 0.;

                    var points = channelPoints.getOrDefault(name, new ArrayList<>());
                    points.add(new Point(time, val));
                    channelPoints.put(name, points);
                }
                i = i + TWO;
            }
        }

        return channelPoints;
    }

    @SneakyThrows
    private static Map<Source, List<Point>> asciiRead(InputStream inputStream, CfgFile cfg) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8.newDecoder()))) {
            Map<Source, List<Point>> channelPoints = new HashMap<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                int offset = 0;

                offset++;
                var time = Double.parseDouble(splitLine[offset]) / 1_000_000;

                offset++;

                for (int i = 0; i < cfg.getAnalogChannels().size(); i++) {
                    var aCh = cfg.getAnalogChannels().get(i);

                    var name = aCh.getSource(null);
                    var val = aCh.getCurrentValue(Double.parseDouble(splitLine[i + offset]));

                    var points = channelPoints.getOrDefault(name, new ArrayList<>());
                    points.add(new Point(time, val));
                    channelPoints.put(name, points);
                }
                offset = offset + cfg.getAnalogChannels().size();
                for (int i = 0; i < cfg.getDigitalChannels().size(); i++) {
                    var dCh = cfg.getDigitalChannels().get(i);

                    var name = dCh.getSource(null);
                    var val = Integer.parseInt(splitLine[i + offset]) == 1 ? 1. : 0.;

                    var points = channelPoints.getOrDefault(name, new ArrayList<>());
                    points.add(new Point(time, val));
                    channelPoints.put(name, points);
                }
            }
            return channelPoints;
        }
    }

    public List<ChartPoint> parseComtradeFile(InputStream cfgFile, InputStream datFile) {
        var cfg = CfgFile.readCfg(cfgFile);

        var res = cfg.getFileType() == CfgFile.FileType.BINARY ?
                binaryRead(datFile, cfg)
                : asciiRead(datFile, cfg);
        return ChartPoint.fromMap(res);
    }
}
