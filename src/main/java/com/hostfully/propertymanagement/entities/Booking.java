package com.hostfully.propertymanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import com.hostfully.propertymanagement.misc.BookingStatus;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Booking extends Reserving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    BookingStatus status;
}
