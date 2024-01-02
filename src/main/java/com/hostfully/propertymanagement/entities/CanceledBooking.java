package com.hostfully.propertymanagement.entities;

import com.hostfully.propertymanagement.misc.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CanceledBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "canceled_reason_id")
    private CancelingReason reason;

    @Column(length = 300)
    private String message;

    @ManyToOne// the real relation is one to one, but I want to store canceled that rebooked for support team in production
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus = RecordStatus.EXIST;

}
