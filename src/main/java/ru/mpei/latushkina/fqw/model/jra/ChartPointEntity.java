package ru.mpei.latushkina.fqw.model.jra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "chart_point")
@Data
@AllArgsConstructor
public class ChartPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private Double time;
    private Double value;
}
