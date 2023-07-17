package ru.mpei.latushkina.fqw.repository.provider;

import org.springframework.stereotype.Component;

@Component
public class ChartPointQueryProvider {
//    @Value("${mydb.tables.chartpoint}")
private final String chartPointTableName = "chart_point";

    public String getDistinctSourcesQuery() {
        return "SELECT DISTINCT tbl.source FROM " + chartPointTableName + " tbl";
    }
}
