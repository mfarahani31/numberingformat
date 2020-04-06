package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NFM_RESERVED_NUMBER_INTERVAL")
@CheckEndGreaterThanStart
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NumberingFormatInterval implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NUMBERING_USAGE")
    @Size(max = 50)
    @NotNull
    private String numberUsage;


    @Column(name = "NUMBERING_FORMAT")
    @Size(max = 400)
    @NotNull
    private String numberFormat;

    @Column(name = "RESERVED_START", length = 12)
    @Min(1L)
    private Long reservedStart;

    @Column(name = "RESERVED_END", length = 12)
    private Long reservedEnd;

    @Column(name = "CREATED_BY", updatable = false)
    @Size(max = 200)
    private String createdBy;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "LAST_MODIFIED_BY")
    @Size(max = 200)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @UpdateTimestamp
    private Date lastModifiedDate;
}
