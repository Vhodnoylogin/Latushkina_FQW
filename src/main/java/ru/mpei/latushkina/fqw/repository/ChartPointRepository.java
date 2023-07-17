package ru.mpei.latushkina.fqw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mpei.latushkina.fqw.model.jra.ChartPointEntity;

import java.util.List;

@Repository
public interface ChartPointRepository extends JpaRepository<ChartPointEntity, Long> {
    @Query("SELECT DISTINCT tbl.source FROM ChartPointEntity tbl")
    List<String> getDistinctBySource();

    List<ChartPointEntity> findBySourceIn(List<String> source);

    List<ChartPointEntity> findBySourceInAndTimeBetween(List<String> source, Double timeBegin, Double timeEnd);

    List<ChartPointEntity> findBySourceAndTimeGreaterThan(String source, Double time);

    List<ChartPointEntity> findBySourceAndTimeLessThan(String source, Double time);

    List<ChartPointEntity> findByValueGreaterThan(Double settingCurrent);
}
