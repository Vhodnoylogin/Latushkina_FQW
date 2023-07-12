package ru.mpei.latushkina.fqw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MeasByName {
    private double time;
    private String[] names;
    private double[] values;
}
