package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NFM_RESERVED_NUMBER_INTERVAL")
@CheckEndGreaterThanStart
@SequenceGenerator(name = "SQ_NFM_RESERVED_NUMBER_INTERVAL", sequenceName = "SQ_NFM_RESERVED_NUMBER_INTERVAL", allocationSize = 1)
public class NumberingFormatInterval extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NFM_RESERVED_NUMBER_INTERVAL")
    private Long id;


    @Column(name = "RESERVED_START", length = 12)
    @Min(value = 1L, message = "Start value can't be smaller than 1")
    private Long reservedStart;

    @Column(name = "RESERVED_END", length = 12)
    private Long reservedEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUMBERING_FORMAT_ID", nullable = false)
    @JsonIgnore
    private NumberingFormat numberingFormat;
}
