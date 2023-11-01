/*
package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookingtreatments")
public class BookingTreatment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JsonIgnore
    private Booking booking;

    @ManyToOne
    @JsonIgnore
    private Treatment treatment;

    public void add(List<BookingTreatment> bookingTreatments) {
    }
}
*/
