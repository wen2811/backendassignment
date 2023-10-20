package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.models.TreatmentType;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TreatmentServiceTest {

    @Mock
    TreatmentRepository treatmentRepository;
    @Mock
    CalendarRepository calendarRepository;
    @InjectMocks
    TreatmentService treatmentServiceImpl;

    @Captor
    ArgumentCaptor<Treatment> treatmentArgumentCaptor;
    @Captor
    ArgumentCaptor<Calendar> calendarCaptor;


    Treatment treatment1;
    Treatment treatment2;
    Treatment treatment3;
    List<Treatment> treatmentList = new ArrayList<>();
    List<BookingTreatmentDto> bookingTreatments;
    Calendar calendar;



    @BeforeEach
    void setUp() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        treatment1 = new Treatment(1L, "LED Light Therapy", TreatmentType.FACIAL_TREATMENT, "LED light therapy rejuvenates skin", 30.0, 45.0, calendar);
        treatment2 = new Treatment(2L, "Carboxy Treatment", TreatmentType.BODY_TREATMENT, "A carboxy body treatment", 45.0, 100.0, calendar);
        treatment3 = new Treatment(3L, "Massage Treatment", TreatmentType.BODY_TREATMENT, "Massage for relaxing", 60.0, 85.0, calendar);

        treatmentList = new ArrayList<>();

        TreatmentDto treatmentDto1 = new TreatmentDto();
        treatmentDto1.setId(1L);
        treatmentDto1.setName("LED Light Therapy");
        treatmentDto1.setType(TreatmentType.FACIAL_TREATMENT);
        treatmentDto1.setDescription("LED light therapy rejuvenates skin");
        treatmentDto1.setDuration(30);
        treatmentDto1.setPrice(45);
        treatmentList.add(treatment1);

        TreatmentDto treatmentDto2 = new TreatmentDto();
        treatmentDto2.setId(2L);
        treatmentDto2.setName("Carboxy Treatment");
        treatmentDto2.setType(TreatmentType.BODY_TREATMENT);
        treatmentDto2.setDescription("A oxygen body treatment");
        treatmentDto2.setDuration(45);
        treatmentDto2.setPrice(100);
        treatmentList.add(treatment2);

        TreatmentDto treatmentDto3 = new TreatmentDto();
        treatmentDto3.setId(3L);
        treatmentDto3.setName("Massage Treatment");
        treatmentDto3.setType(TreatmentType.BODY_TREATMENT);
        treatmentDto3.setDescription("Massage for relaxing");
        treatmentDto3.setDuration(60);
        treatmentDto3.setPrice(85);
        treatmentList.add(treatment3);

        bookingTreatments = new ArrayList<>();

        BookingTreatmentDto bookingTreatmentDto1 = new BookingTreatmentDto();
        bookingTreatmentDto1.setId(1L);
        bookingTreatmentDto1.setQuantity(2);
        bookingTreatmentDto1.setTreatment(treatment1);
        bookingTreatmentDto1.setCustomerName("Iris Maria");
        bookingTreatmentDto1.setCustomerEmail("Iris@example.com");

        BookingTreatmentDto bookingTreatmentDto2 = new BookingTreatmentDto();
        bookingTreatmentDto2.setId(2L);
        bookingTreatmentDto2.setQuantity(3);
        bookingTreatmentDto2.setTreatment(treatment2);
        bookingTreatmentDto2.setCustomerName("Nate Cole");
        bookingTreatmentDto2.setCustomerEmail("Nate@example.com");

        bookingTreatments.add(bookingTreatmentDto1);
        bookingTreatments.add(bookingTreatmentDto2);
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void transferTreatmentToDto() {
        //Arrange
        Treatment treatment= Treatment.builder().id(4L).name("name")
                .type(TreatmentType.FACIAL_TREATMENT).description("Natural exfoliating")
                .duration(45).price(90).build();
        //Act
        TreatmentDto treatmentDto = treatmentServiceImpl.transferTreatmentToDto(treatment);

        //Assert
        assertEquals(treatmentDto.name, treatment.getName());
        assertEquals(treatmentDto.type, treatment.getType());
        assertEquals(treatmentDto.description, treatment.getDescription());
        assertEquals(treatmentDto.duration, treatment.getDuration());
        assertEquals(treatmentDto.price, treatment.getPrice());

    }

    @Test
    void transferDtoToTreatment() {
        TreatmentDto treatmentDto = TreatmentDto.builder().id(4L).name("name")
                .type(TreatmentType.FACIAL_TREATMENT).description("Natural exfoliating")
                .duration(45).price(90).build();
        //Act
        Treatment treatment = treatmentServiceImpl.transferDtoToTreatment(treatmentDto);

        //Assert
        assertEquals("name", treatmentDto.name);
        assertEquals(TreatmentType.FACIAL_TREATMENT, treatmentDto.type);
        assertEquals("Natural exfoliating", treatmentDto.description);
        assertEquals(45, treatmentDto.duration);
        assertEquals(90.0, treatmentDto.price);
    }

    @Test
    void transferBookingTreatmentToDto() {
        //Arrange
        BookingTreatment bookingTreatment= BookingTreatment.builder().id(4L).quantity(3)
                .customerName("Selena Lopez").customerEmail("selena@testmail.com")
                .build();
        //Act
        BookingTreatmentDto bookingTreatmentDto = treatmentServiceImpl.transferBookingTreatmentToDto(bookingTreatment);

        //Assert
        assertEquals(3, bookingTreatment.getQuantity());
        assertEquals("Selena Lopez", bookingTreatment.getCustomerName());
        assertEquals("selena@testmail.com", bookingTreatment.getCustomerEmail());

    }

    @Test
    void transferDtoToBookingTreatment() {
        //Arrange
        BookingTreatmentDto bookingTreatmentDto= BookingTreatmentDto.builder().id(4L).quantity(3)
                .customerName("Selena Lopez").customerEmail("selena@testmail.com")
                .build();
        //Act
        BookingTreatment bookingTreatment = treatmentServiceImpl.transferDtoToBookingTreatment(bookingTreatmentDto);

        //Assert
        assertEquals(3, bookingTreatmentDto.getQuantity());
        assertEquals("Selena Lopez", bookingTreatmentDto.getCustomerName());
        assertEquals("selena@testmail.com", bookingTreatmentDto.getCustomerEmail());
    }

    @Test
    void getAllTreatments() {
        //arrange
       when(treatmentRepository.findAll()).thenReturn(treatmentList);
        //act
        List<TreatmentDto> treatmentDtos = treatmentServiceImpl.getAllTreatments();
        //assert
        assertEquals(treatmentList.size(), treatmentDtos.size());

        for (int i = 0; i < treatmentList.size(); i++) {
            Treatment treatment = treatmentList.get(i);
            TreatmentDto treatmentDto = treatmentDtos.get(i);

            assertEquals(treatment.getId(), treatmentDto.getId());
            assertEquals(treatment.getName(), treatmentDto.getName());
            assertEquals(treatment.getDescription(), treatmentDto.getDescription());
        }
    }

    @Test
    void getAllTreatments_EmptyRepository() {
        // Arrange
        when(treatmentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<TreatmentDto> treatmentDtos = treatmentServiceImpl.getAllTreatments();

        // Assert
        assertTrue(treatmentDtos.isEmpty());
    }

    @Test
    void getAllTreatments_ExceptionCase() {

        when(treatmentRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> treatmentServiceImpl.getAllTreatments());


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
     void getTreatmentsByTypeThrowsExeption(){
        TreatmentDto treatmentDto = new TreatmentDto();

        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.getTreatmentsByType(TreatmentType.BODY_TREATMENT));
     }


    @Test
    void addTreatment() {
       //Arrange
        TreatmentDto treatmentDto = TreatmentDto.builder().id(4L).name("name")
                .type(TreatmentType.FACIAL_TREATMENT).description("Natural exfoliating")
                .duration(45).price(90).build();

        Treatment treatment = treatmentServiceImpl.transferDtoToTreatment(treatmentDto);

        when(treatmentRepository.save(any(Treatment.class))).thenReturn(treatment);

        // Act
        TreatmentDto responseTreatmentDto = treatmentServiceImpl.addTreatment(treatmentServiceImpl.transferTreatmentToDto(treatment));

        verify(treatmentRepository, times(1)).save(treatmentArgumentCaptor.capture());

        Treatment savedTreatment = treatmentArgumentCaptor.getValue();

        //Assert
        assertEquals(responseTreatmentDto.name, savedTreatment.getName());
        assertEquals(responseTreatmentDto.type, savedTreatment.getType());
        assertEquals(responseTreatmentDto.description, savedTreatment.getDescription());
        assertEquals(responseTreatmentDto.duration, savedTreatment.getDuration());
        assertEquals(responseTreatmentDto.price, savedTreatment.getPrice());
    }


    @Test
    void updateTreatment() {
        //arrange
        long treatmentId = 1L;
        TreatmentDto treatmentDto = TreatmentDto.builder().description("korting")
                .price(50).duration(110).name("aanbieding").type(TreatmentType.FACIAL_TREATMENT).build();

        Treatment existingTreatment = new Treatment();
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(existingTreatment));

        //act
        treatmentServiceImpl.updateTreatment(treatmentId,treatmentDto);

        //assert
        ArgumentCaptor<Treatment> treatmentArgumentCaptor1 = ArgumentCaptor.forClass(Treatment.class);
        verify(treatmentRepository).save(treatmentArgumentCaptor1.capture());

        Treatment updatedTreatment = treatmentArgumentCaptor1.getValue();
        assertEquals("korting", updatedTreatment.getDescription());
        assertEquals(50, updatedTreatment.getPrice());
        assertEquals(110, updatedTreatment.getDuration());
        assertEquals("aanbieding", updatedTreatment.getName());
        assertEquals(TreatmentType.FACIAL_TREATMENT, updatedTreatment.getType());

    }

    @Test
    void deleteTreatment() {
        //arrange
        long treatmentId = 2L;
        Treatment treatment = new Treatment();

        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(treatment));

        // Act
        assertDoesNotThrow(() -> treatmentServiceImpl.deleteTreatment(treatmentId));

        // Assert
        verify(treatmentRepository).delete(treatment);
    }

    @Test
    void deleteTreatment_RecordNotFound() {
        //arrange
        long treatmentId = 1L;

        //act
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());

        //assert
        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.deleteTreatment(treatmentId));
    }

    @Test
    void updateTreatmentWithCalendar() {
        //arrange
        long treatmentId = 1L;
        TreatmentDto treatmentDto = TreatmentDto.builder().name("updated name")
                .description("korting").price(50).duration(75).type(TreatmentType.BODY_TREATMENT).build();

        CalendarDto calendarDto = CalendarDto.builder().id(4L).date(LocalDate.of(2023, 12, 4))
                .startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(10, 50)).build();


        Treatment existingTreatment = new Treatment();
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(existingTreatment));

        Calendar existingCalendar = new Calendar();
        existingTreatment.setCalendar(existingCalendar);

        //act
        treatmentServiceImpl.updateTreatmentWithCalendar(treatmentId,treatmentDto, calendarDto);

        //assert
        verify(treatmentRepository).save(treatmentArgumentCaptor.capture());
        verify(calendarRepository).save(calendarCaptor.capture());

        Treatment updatedTreatment = treatmentArgumentCaptor.getValue();
        Calendar updatedCalendar = calendarCaptor.getValue();

        assertEquals("updated name", updatedTreatment.getName());
        assertEquals("korting", updatedTreatment.getDescription());
        assertEquals(50.0, updatedTreatment.getPrice());
        assertEquals(75, updatedTreatment.getDuration());
        assertEquals(TreatmentType.BODY_TREATMENT, updatedTreatment.getType());

        assertEquals(calendarDto.getDate(), updatedCalendar.getDate());
        assertEquals(calendarDto.getStartTime(), updatedCalendar.getStartTime());
        assertEquals(calendarDto.getEndTime(), updatedCalendar.getEndTime());
    }

    @Test
    void updateTreatmentWithCalendar_RecordNotFound() {
        //arrange
        long treatmentId = 2L;
        TreatmentDto treatmentDto = TreatmentDto.builder().name("updated name")
                .description("korting").price(50).duration(75).type(TreatmentType.BODY_TREATMENT).build();

        CalendarDto calendarDto = CalendarDto.builder().id(4L).date(LocalDate.of(2023, 12, 4))
                .startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(10, 50)).build();

        //act
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());

        //assert
        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.updateTreatmentWithCalendar(treatmentId, treatmentDto, calendarDto));
    }


    @Test
    void getTreatmentWithCalendar() {
        //arrange
        long treatmentId = 3L;
        Treatment treatment = new Treatment();
        Calendar calendar = new Calendar();
        treatment.setCalendar(calendar);
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(treatment));

        //act
        TreatmentDto treatmentDto = treatmentServiceImpl.getTreatmentWithCalendar(treatmentId);

        //assert
        assertNotNull(treatmentDto);
        assertEquals(treatment.getName(), treatmentDto.getName());
        assertEquals(treatment.getDescription(), treatmentDto.getDescription());
        assertEquals(treatment.getPrice(), treatmentDto.getPrice());
        assertEquals(treatment.getDuration(), treatmentDto.getDuration());
    }

    @Test
    void getTreatmentWithCalendar_TreatmentNotFound() {
        //arrange
        long treatmentId = 1L;

        //act
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());

        //assert
        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.getTreatmentWithCalendar(treatmentId));

    }

    @Test
    void getTreatmentWithCalendar_NoCalendar() {
        //arrange
        long treatmentId = 1L;
        Treatment treatment = new Treatment();

        //act
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(treatment));

        //assert
        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.getTreatmentWithCalendar(treatmentId));
    }

   /* @Test
    void addBookingTreatmentToTreatment() {
        //arrange
        long treatmentId = 2L;
        Treatment existingTreatment = new Treatment();
        existingTreatment.setId(treatmentId);

        BookingTreatmentDto bookingTreatmentDto  = BookingTreatmentDto.builder().quantity(3)
                .treatment(treatment2).customerName("Nate Cole").customerEmail("Nate@example.com").build();

        BookingTreatment savedBookingTreatment = BookingTreatment.builder().quantity(3)
                .treatment(treatment2).customerName("Nate Cole").customerEmail("Nate@example.com").build();


        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(existingTreatment));
        when(bookingTreatmentRepository.save(any(BookingTreatment.class))).thenReturn(savedBookingTreatment);

        //act
        BookingTreatmentDto result = treatmentServiceImpl.addBookingTreatmentToTreatment(treatmentId, bookingTreatmentDto);

        //assert
        assertNotNull(result);
        assertEquals(bookingTreatmentDto.getQuantity(), result.getQuantity());

        verify(treatmentRepository).findById(treatmentId);
        verify(bookingTreatmentRepository).save(any(BookingTreatment.class));

    }*/
   /* @Test
    void addBookingTreatmentToTreatment_ThrowsRecordNotFoundException() {
        //arrange
        long treatmentId = 1L;
        BookingTreatmentDto bookingTreatmentDto = new BookingTreatmentDto();

        //act
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());
        //arrange
        assertThrows(RecordNotFoundException.class, () -> {
            treatmentServiceImpl.addBookingTreatmentToTreatment(treatmentId, bookingTreatmentDto);
        });
    }*/


    /*@Test
    void updateBookingTreatment() {
        long bookingTreatmentId = 1L;
        BookingTreatmentDto bookingTreatmentDto  = BookingTreatmentDto.builder().quantity(3)
                .treatment(treatment2).customerName("Nate Cole").customerEmail("Nate@example.com").build();



        BookingTreatment existingBookingTreatment = new BookingTreatment();
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(existingTreatment));

        //act
        treatmentServiceImpl.updateTreatment(treatmentId,treatmentDto);

        //assert
        ArgumentCaptor<Treatment> treatmentArgumentCaptor1 = ArgumentCaptor.forClass(Treatment.class);
        verify(treatmentRepository).save(treatmentArgumentCaptor1.capture());

        Treatment updatedTreatment = treatmentArgumentCaptor1.getValue();
        assertEquals("korting", updatedTreatment.getDescription());
        assertEquals(50, updatedTreatment.getPrice());

    }*/


}