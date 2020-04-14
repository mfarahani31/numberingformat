package com.gam.phoenix.numberingformat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull(message = "Created date cannot be null")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull(message = "Last modified date cannot be null")
    private Date lastModifiedDate;

    @Column(name = "CREATED_BY", updatable = false)
    //@NotNull(message = "Created by cannot be null")
    @Size(max = 200)
    @CreatedBy
    private String createdBy;

    @Column(name = "LAST_MODIFIED_BY")
    //@NotNull(message = "Last modified by cannot be null")
    @Size(max = 200)
    @LastModifiedBy
    private String lastModifiedBy;

}
