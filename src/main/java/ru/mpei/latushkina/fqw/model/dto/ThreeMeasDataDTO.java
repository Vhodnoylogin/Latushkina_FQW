package ru.mpei.latushkina.fqw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mpei.latushkina.fqw.model.MetaInf;
import ru.mpei.latushkina.fqw.model.measurement.ThreeMeasData;

import java.util.List;

@Data
@AllArgsConstructor
public class ThreeMeasDataDTO {
    private MetaInf meta;
    private List<ThreeMeasData> meas;
}
