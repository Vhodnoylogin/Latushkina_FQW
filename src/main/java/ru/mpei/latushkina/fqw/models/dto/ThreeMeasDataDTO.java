package ru.mpei.latushkina.fqw.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mpei.latushkina.fqw.models.MetaInf;
import ru.mpei.latushkina.fqw.models.measurement.ThreeMeasData;

import java.util.List;

@Data
@AllArgsConstructor
public class ThreeMeasDataDTO {
    private MetaInf meta;
    private List<ThreeMeasData> meas;
}
