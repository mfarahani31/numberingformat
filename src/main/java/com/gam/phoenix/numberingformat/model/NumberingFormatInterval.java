package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_NFM_RESERVED_NUMBER_INTERVAL")
@CheckEndGreaterThanStart
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NumberingFormatInterval extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "RESERVED_START", length = 12)
    @Min(value = 1L, message = "min start value is 1")
    private Long reservedStart;

    @Column(name = "RESERVED_END", length = 12)
    private Long reservedEnd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NUMBERING_FORMAT_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private NumberingFormat numberingFormat;


    @Column(name = "CREATED_BY", updatable = false)
    @Size(max = 200)
    private String createdBy;


    @Column(name = "LAST_MODIFIED_BY")
    @Size(max = 200)
    private String lastModifiedBy;
}
