package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    //private String treatmentName;
    //private double treatmentDuration;






   /* public void updateCustomerInformation(String customerName, String customerEmail){
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }*/
}
