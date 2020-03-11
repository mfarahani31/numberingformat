package com.gam.phoenix.numberingformat.repository;

import com.gam.phoenix.numberingformat.model.NumberFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberingFormatRepository extends JpaRepository<NumberFormat,Long> {
}
