package ru.mpei.latushkina.fqw.repository;

import ru.mpei.latushkina.fqw.models.*;
import ru.mpei.latushkina.fqw.models.dto.FileInfo;
import ru.mpei.latushkina.fqw.models.dto.MeasByName;
import ru.mpei.latushkina.fqw.models.measurement.ThreeMeasData;

import java.util.List;
import java.util.Optional;

public interface MeasurementsRepository {
    void saveMeas(List<Measurements> measurements, MetaInf metaInf);

    Optional<List<String>> getMeasNames(long id);

    List<MeasByName> getMeasByNames(long id, List<String> names, int start, int end);

    List<ThreeMeasData> getThreeMeas(long id, String phA, String phB, String phC);

    Optional<MetaInf> getMetaInf(long id);

    List<FileInfo> getFilesInfo();
}
