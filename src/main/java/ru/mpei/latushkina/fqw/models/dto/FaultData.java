package ru.mpei.latushkina.fqw.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mpei.latushkina.fqw.models.measurement.ThreeMeasData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaultData {
    private String fault;
    private double time;
    private double set;
    private ThreeMeasData normalCurrent;
    private ThreeMeasData faultCurrent;
}

