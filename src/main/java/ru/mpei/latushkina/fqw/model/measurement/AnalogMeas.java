package ru.mpei.latushkina.fqw.model.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalogMeas {
    private String name;
    private double val;
}
