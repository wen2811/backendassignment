package com.wendy.backendassignment.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatments")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Treatment name is required")
    private String name;
    @Enumerated(EnumType.STRING)
    private TreatmentType type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double duration;
    private double price;

    @OneToOne (optional = true)
    private Calendar calendar;

    @OneToMany
    private List<BookingTreatment> bookingTreatments;

    public Treatment(long id, String name, TreatmentType type, String description, double duration, double price, java.util.Calendar calendar) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }
    public Treatment(Long id, String name, TreatmentType type, String description, double duration, double price, Calendar calendar) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.calendar = calendar;
    }

}
