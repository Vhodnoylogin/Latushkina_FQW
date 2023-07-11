package ru.mpei.latushkina.fqw.models.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalMeas {
    private String name;
    boolean val;
}
