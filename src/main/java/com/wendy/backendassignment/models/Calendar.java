
package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("calendar")
    private Treatment treatment;

    public boolean isAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(boolean availableTime) {
        this.availableTime = availableTime;
    }

    public Calendar(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public LocalDate getDate() {
        return date;
    }
}

