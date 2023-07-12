package ru.mpei.latushkina.fqw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mpei.latushkina.fqw.model.measurement.AnalogMeas;
import ru.mpei.latushkina.fqw.model.measurement.DigitalMeas;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasList {
    private double time;
    private List<AnalogMeas> meas = new ArrayList<>();
    private List<DigitalMeas> dmeas = new ArrayList<>();
}
