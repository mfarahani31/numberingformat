package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/
@Repository
public interface NumberingFormatIntervalRepository extends JpaRepository<NumberingFormatInterval, Long> {


    //@Query("select n from NumberingFormatInterval n where n.numberingFormat.id = :numberingFormatId order by n.id ASC")
    List<NumberingFormatInterval> findByNumberingFormatId(Long numberingFormatId);


    @Query("SELECT n FROM NumberingFormatInterval n WHERE n.numberingFormat.id = :numberingFormatId and n.reservedEnd > :serial ORDER BY n.id ASC")
    List<NumberingFormatInterval> findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(@Param("numberingFormatId") Long numberingFormatId,
                                                                                                    @Param("serial") Long serial);


    Optional<NumberingFormatInterval> findByIdAndNumberingFormatId(Long id, Long numberingFormatId);

}
