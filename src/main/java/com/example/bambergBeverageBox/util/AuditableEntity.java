package com.example.bambergBeverageBox.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @ToString.Exclude
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ToString.Exclude
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @ToString.Exclude
    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @ToString.Exclude
    @LastModifiedBy
    @Column(name = "modified_by")
    private Long modifiedBy;

    @ToString.Exclude
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
