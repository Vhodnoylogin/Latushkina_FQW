package ru.mpei.latushkina.fqw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mpei.latushkina.fqw.model.jra.ChartPointEntity;

import java.util.List;

@Repository
public interface ChartPointRepository extends JpaRepository<ChartPointEntity, Long> {
    List<ChartPointEntity> findBySource(String source);

    List<ChartPointEntity> findBySourceAndTimeBetween(String source, Double t1, Double t2);

//    List<ChartPointEntity> saveAll(List<ChartPointEntity> chartPoints);
}
