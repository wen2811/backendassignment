package com.wendy.backendassignment.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.models.TreatmentType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TreatmentDto {
    public Long id;
    //@NotBlank(message = "Treatment name is required")
    public String name;
    public TreatmentType type;
    public String description;
    public double duration;
    public double price;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Calendar calendar;

    public Booking booking;

}
