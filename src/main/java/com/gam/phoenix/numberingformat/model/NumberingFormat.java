package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NFM_NUMBERING_FORMAT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NUMBERING_USAGE", "NUMBERING_FORMAT"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NumberingFormat extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMBERING_USAGE")
    @Size(max = 50)
    @NotNull
    private String numberUsage;


    @Column(name = "NUMBERING_FORMAT")
    @Size(max = 400)
    @NotNull
    private String numberFormat;

    @Column(name = "START_AT", length = 12)
    @Min(1L)
    @NotNull
    private Long startAt;

    @Column(name = "LAST_ALLOCATED_SERIAL", length = 12)
    private Long lastAllocatedSerial;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "numberingFormat")
    private List<NumberingFormatInterval> numberingFormatIntervalList;

    @Override
    public String toString() {
        return "NumberingFormat{" +
                "id=" + id +
                ", numberUsage='" + numberUsage + '\'' +
                ", numberFormat='" + numberFormat + '\'' +
                ", startAt=" + startAt +
                ", lastAllocatedSerial=" + lastAllocatedSerial +
                '}';
    }
}
