package ru.mpei.latushkina.fqw.model.dto.comtrade.cfg;

import ru.mpei.latushkina.fqw.model.dto.point.Source;

public interface Cfg {
    String DELIMITER = "_";

    Source getSource(Double input);

    Double getCurrentValue(Double input);
}
