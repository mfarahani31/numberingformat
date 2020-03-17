package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NFM_RESERVED_NUMBER_INTERVAL")
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

    @Column(name = "RESERVED_START")
    @Size(max = 12)
    private Long startAt;

    @Column(name = "RESERVED_END")
    @Size(max = 12)
    private Long lastAllocatedSerial;

    @Column(name = "CREATED_BY", updatable = false)
    @Size(max = 200)
    private String createdBy;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "LAST_MODIFIED_BY")
    @Size(max = 200)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;
}
