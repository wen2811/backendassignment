package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDate invoicedate;

    @OneToOne
    @JsonIgnoreProperties("invoice")
    private Booking booking;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    public Invoice(LocalDate invoicedate, double Amount ){
        this.invoicedate = invoicedate;
        this.amount = Amount;
    }

    public Invoice(Long id) {
    }
}
