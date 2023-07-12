package ru.mpei.latushkina.fqw.model;

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
public class Measurements {
    private double time;
    private List<AnalogMeas> analogMeas = new ArrayList<>();
    private List<AnalogMeas> rmsMeas = new ArrayList<>();
    private List<DigitalMeas> digitalMeas = new ArrayList<>();
}
