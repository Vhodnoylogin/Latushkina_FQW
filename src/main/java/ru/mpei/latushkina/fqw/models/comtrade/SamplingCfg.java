package ru.mpei.latushkina.fqw.models.comtrade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SamplingCfg {
    private double samplingFreq;
    private int lastNumber;
}
