package org.prajwal.authservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
/*
Without @MappedSuperclass
BaseModel is just an ordinary Java parent class. JPA doesn't automatically say:
"Take these fields and map them into every child entity's table."

So @MappedSuperclass tells JPA:
"This class is a template for persistent fields that its entity subclasses inherit."
 */
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    protected Date updatedAt;
}