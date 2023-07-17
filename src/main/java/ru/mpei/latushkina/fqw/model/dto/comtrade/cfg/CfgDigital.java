package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mpei.latushkina.fqw.model.dto.point.Source;

@Data
@NoArgsConstructor
public class CfgDigital implements Cfg {
    private static String digitalSuffix = "BOOL";
    private int channelNumber;
    private String channelId;
    private int normalState;

    public static CfgDigital lineToCfg(String[] line) {
        var cfgDigital = new CfgDigital();

        cfgDigital.setChannelNumber(Integer.parseInt(line[0]));
        cfgDigital.setChannelId(line[0] + "_" + line[1].replaceAll(" ", "") + "_" + digitalSuffix);
        cfgDigital.setNormalState(!line[2].equals("") ? Integer.parseInt(line[2]) : 0);

        return cfgDigital;
    }

    @Override
    public Source getSource(Double input) {
        return Source.makeSource(getChannelId());
    }

    @Override
    public Double getCurrentValue(Double input) {
        return input;
    }
}
