package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberingFormatRepository extends JpaRepository<NumberingFormat, Long> {


    @Query("SELECT n FROM NumberingFormat n WHERE n.numberFormat = :format and n.numberUsage = :usage")
    NumberingFormat findNumberingFormatsByNumberUsageAndNumberFormatIsLike(@Param("usage") String usage,
                                                                           @Param("format") String format);


    @Query("SELECT n FROM NumberingFormat n WHERE n.numberFormat = :format and n.numberUsage = :usage")
    void deleteNumberingFormatByNumberUsageAndNumberFormat(@Param("usage") String usage,
                                                           @Param("format") String format);


}
