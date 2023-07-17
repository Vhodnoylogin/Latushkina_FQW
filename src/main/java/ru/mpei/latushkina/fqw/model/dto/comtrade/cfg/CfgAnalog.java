package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CfgAnalog {
    private int channelNumber;
    private String channelId;
    private String phaseId;
    private String component;
    private String unit;
    private double a;
    private double b;
    private double skew;
    private int min;
    private int max;
    private double primary;
    private double secondary;
    private Value value;

    public static CfgAnalog lineToCfg(String[] line) {
        var cfgAnalog = new CfgAnalog();

        cfgAnalog.setChannelNumber(Integer.parseInt(line[0]));
        cfgAnalog.setChannelId(Integer.parseInt(line[0]) + "_" + line[1].replaceAll(" ", ""));
        cfgAnalog.setPhaseId(line[2]);
        cfgAnalog.setComponent(line[3]);
        cfgAnalog.setUnit(line[4]);
        cfgAnalog.setA(Double.parseDouble(line[5]));
        cfgAnalog.setB(Double.parseDouble(line[6]));
        cfgAnalog.setSkew(Double.parseDouble(line[7]));
        cfgAnalog.setMin(Integer.parseInt(line[8]));
        cfgAnalog.setMax(Integer.parseInt(line[9]));
        cfgAnalog.setPrimary(Double.parseDouble(line[10]));
        cfgAnalog.setSecondary(Double.parseDouble(line[11]));
        cfgAnalog.setValue(line[12].contains("S") ? CfgAnalog.Value.S : CfgAnalog.Value.P);

        return cfgAnalog;
    }

    public enum Value {
        P, S
    }
}
