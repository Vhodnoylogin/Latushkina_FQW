package ru.mpei.latushkina.fqw.model.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalMeas {
    private String name;
    private boolean val;
}
