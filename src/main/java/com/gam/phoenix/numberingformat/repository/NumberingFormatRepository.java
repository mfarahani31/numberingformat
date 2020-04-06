package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NumberingFormatRepository extends JpaRepository<NumberingFormat, Long> {


    NumberingFormat findByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                     @Param("format") String format);

    @Transactional
    @Modifying
    void deleteNumberingFormatByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                           @Param("format") String format);

    @Transactional
    @Modifying
    @Query("UPDATE NumberingFormat n set n.lastAllocatedSerial = n.lastAllocatedSerial+1 where n.numberUsage = :usage and n.numberFormat = :format")
    void updateNumberingFormatPlusOneToLastAllocatedSerial(@Param("usage") String usage,
                                                           @Param("format") String format);
}
