package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Repository
public interface NumberingFormatRepository extends JpaRepository<NumberingFormat, Long> {


    NumberingFormat findByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                     @Param("format") String format);

    @Modifying
    Long deleteNumberingFormatByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                           @Param("format") String format);

}
