package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NumberingFormatIntervalRepository extends JpaRepository<NumberingFormatInterval, Long> {

    @Transactional
    @Modifying
    void deleteNumberingFormatIntervalByNumberUsageAndNumberFormatAndReservedStartAndReservedEnd(@Param("usage") String usage,
                                                                                                 @Param("format") String format,
                                                                                                 @Param("start") Long start,
                                                                                                 @Param("end") Long end);

    List<NumberingFormatInterval> findAllByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                                      @Param("format") String format);

    @Query("SELECT n FROM NumberingFormatInterval n WHERE n.numberUsage = :usage and n.numberFormat = :format and n.reservedEnd > :serial ORDER BY n.numberUsage ASC")
    List<NumberingFormatInterval> findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(@Param("usage") String usage,
                                                                                                    @Param("format") String format,
                                                                                                    @Param("serial") Long serial);

}
