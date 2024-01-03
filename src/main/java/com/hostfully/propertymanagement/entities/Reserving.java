package com.hostfully.propertymanagement.entities;

import com.hostfully.propertymanagement.misc.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(indexes = {@Index(columnList = "record_status"),
                    @Index(columnList = "start_date, end_date")})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Reserving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    RecordStatus recordStatus = RecordStatus.EXIST;
}
