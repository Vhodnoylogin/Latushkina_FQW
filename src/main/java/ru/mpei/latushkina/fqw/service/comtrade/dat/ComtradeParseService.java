package ru.mpei.latushkina.fqw.service.comtrade.dat;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.comtrade.cfg.CfgFile;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Point;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.util.ParserUtil;

import java.io.InputStream;
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
    private static List<ChartPoint> binaryRead(InputStream inputStream, CfgFile cfg) {
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();

        List<ChartPoint> measurements = new ArrayList<>();
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

        return ChartPoint.fromMap(channelPoints);
    }

    public List<ChartPoint> parseComtradeFile(InputStream cfgFile, InputStream datFile) {
        var cfg = CfgFile.readCfg(cfgFile);
        if (cfg.getFileType() == CfgFile.FileType.BINARY) {
            return binaryRead(datFile, cfg);
        }
        return List.of();
    }
}
