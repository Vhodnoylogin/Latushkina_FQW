package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CfgSampling {
    private double samplingFreq;
    private int lastNumber;
}
