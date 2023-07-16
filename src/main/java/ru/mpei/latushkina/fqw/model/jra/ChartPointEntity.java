package ru.mpei.latushkina.fqw.model.jra;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "chart_point")
//@Table(name = "global_uuid_table", schema = "my_schema")
@Data
@AllArgsConstructor
public class ChartPointEntity {
    private String source;
    private Double time;
    private Double value;
}
