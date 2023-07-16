package ru.mpei.latushkina.fqw.repository.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChartPointQueryProvider {
    @Value("${mydb.tables.chart-point-table-name}")
    private String chartPointTableName;

    public String getDistinctSourcesQuery() {
        return "SELECT DISTINCT tbl.source FROM " + chartPointTableName + " tbl";
    }
}
