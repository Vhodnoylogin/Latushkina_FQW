package ru.mpei.latushkina.fqw.model.jra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "${mydb.tables.chart-point-table-name}")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private Double time;
    @Column(name = "value_column")
    private Double value;
}
