
package com.wendy.backendassignment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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

}

/* @ManyToOne
    public void setId(Long id) {
        this.id = id;
    }*//*


   */
/* @OneToOne(mappedBy = "calendar")
    private Booking booking;
*//*


    public boolean isAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(boolean availableTime) {
        this.availableTime = availableTime;
    }
}
*/
