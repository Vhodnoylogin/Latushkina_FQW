package ru.mpei.latushkina.fqw.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    private Double time;
    private Double value;
}
