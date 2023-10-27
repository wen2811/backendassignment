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
    private String customerName;
    private String customerEmail;

    @ManyToOne
    @JsonIgnore
    private Booking booking;

    @ManyToOne
    @JsonIgnore
    private Treatment treatment;

   /* public void updateCustomerInformation(String customerName, String customerEmail){
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }*/
}
