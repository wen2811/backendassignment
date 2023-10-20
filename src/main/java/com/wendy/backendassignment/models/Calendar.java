
package com.wendy.backendassignment.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean availableTime;

    @OneToOne(mappedBy = "calendar")
    private Treatment treatment;


    public boolean isAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(boolean availableTime) {
        this.availableTime = availableTime;
    }
}

