package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CfgDigital {
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
}
