package org.prajwal.authservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BaseModel {

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private Date startTime;
    private Date endTime;
    private Long totalDistance;


}