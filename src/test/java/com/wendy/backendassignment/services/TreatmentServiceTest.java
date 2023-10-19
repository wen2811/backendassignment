package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.models.TreatmentType;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import com.wendy.backendassignment.repositories.CalendarRepository;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TreatmentServiceTest {

    @Mock
    TreatmentRepository treatmentRepository;
    @Mock
    BookingTreatmentRepository bookingTreatmentRepository;
    @Mock
    CalendarRepository calendarRepository;
    @InjectMocks
    TreatmentService treatmentServiceImpl;

    @Captor
    ArgumentCaptor<Treatment> treatmentArgumentCaptor;

    Treatment treatment1;
    Treatment treatment2;
    Treatment treatment3;
    List<Treatment> treatmentList;
    Calendar calendar;



    @BeforeEach
    void setUp() {
        treatment1 = new Treatment(1L, "LED Light Therapy", TreatmentType.FACIAL_TREATMENT, "LED light therapy rejuvenates skin", 30.0, 45.0, calendar);
        treatment2 = new Treatment(2L, "Carboxy Treatment", TreatmentType.BODY_TREATMENT, "A carboxy body treatment", 45.0, 100.0, calendar);
        treatment3 = new Treatment(3L, "Massage Treatment", TreatmentType.BODY_TREATMENT, "Massage for relaxing", 60.0, 85.0, calendar);

        treatmentList = new ArrayList<>();
        treatmentList.add(treatment1);
        treatmentList.add(treatment2);
        treatmentList.add(treatment3);

        calendar = Calendar.getInstance();

        BookingTreatment bookingTreatment1 = new BookingTreatment();
        bookingTreatment1.setId(1L);
        bookingTreatment1.setQuantity(2);
        bookingTreatment1.setTreatment(treatment1);
        bookingTreatment1.setCustomerName("Iris Maria");
        bookingTreatment1.setCustomerEmail("Iris@example.com");

        BookingTreatment bookingTreatment2 = new BookingTreatment();
        bookingTreatment2.setId(2L);
        bookingTreatment2.setQuantity(3);
        bookingTreatment2.setTreatment(treatment1);

        List<BookingTreatment> bookingTreatments = new ArrayList<>();
        bookingTreatments.add(bookingTreatment1);
        bookingTreatments.add(bookingTreatment2);

        treatment1.setBookingTreatments(bookingTreatments);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllTreatments() {
        //arrange
       when(treatmentRepository.findAll()).thenReturn(treatmentList);
        //act
        List<TreatmentDto> treatmentDtos = treatmentServiceImpl.getAllTreatments();
        //assert
        assertEquals(treatmentList.size(), treatmentDtos.size());

    }

    @Test
    void getTreatmentsByType() {
        //arrange
        when(treatmentRepository.findTreatmentsByType(TreatmentType.BODY_TREATMENT)).thenReturn(Optional.of(treatmentList.get(0)));
        //act
        TreatmentDto treatmentDto = treatmentServiceImpl.getTreatmentsByType(TreatmentType.BODY_TREATMENT);
        //assert
        assertEquals(treatmentList.get(0).getType(), treatmentDto.type);

    }

    @Test
    void addTreatment() {
    }

    @Test
    void updateTreatment() {
    }

    @Test
    void deleteTreatment() {
    }

    @Test
    void updateTreatmentWithCalendar() {
    }

    @Test
    void getTreatmentWithCalendar() {
    }

    @Test
    void addBookingTreatmentToTreatment() {
    }

    @Test
    void updateBookingTreatment() {
    }

    @Test
    void getBookingTreatmentById() {
    }

    @Test
    void transferTreatmentToDto() {
    }

    @Test
    void transferDtoToTreatment() {
    }

    @Test
    void transferBookingTreatmentToDto() {
    }

    @Test
    void transferDtoToBookingTreatment() {
    }
}