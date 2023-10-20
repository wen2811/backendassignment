package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.*;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import com.wendy.backendassignment.repositories.CalendarRepository;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
    @Captor
    ArgumentCaptor<Calendar> calendarCaptor;
    @Captor
    ArgumentCaptor<BookingTreatment> bookingTreatmentArgumentCaptor;


    Treatment treatment1;
    Treatment treatment2;
    Treatment treatment3;
    List<Treatment> treatmentList = new ArrayList<>();
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
        //treatmentList.add(treatment1);

        TreatmentDto treatmentDto2 = new TreatmentDto();
        treatmentDto2.setId(2L);
        treatmentDto2.setName("Carboxy Treatment");
        treatmentDto2.setType(TreatmentType.BODY_TREATMENT);
        treatmentDto2.setDescription("A oxygen body treatment");
        treatmentDto2.setDuration(45);
        treatmentDto2.setPrice(100);
        //treatmentList.add(treatment2);

        TreatmentDto treatmentDto3 = new TreatmentDto();
        treatmentDto3.setId(3L);
        treatmentDto3.setName("Massage Treatment");
        treatmentDto3.setType(TreatmentType.BODY_TREATMENT);
        treatmentDto2.setDescription("Massage for relaxing");
        treatmentDto3.setDuration(60);
        treatmentDto3.setPrice(85);
        //treatmentList.add(treatment3);

        treatmentList = new ArrayList<>();
        treatmentList.add(treatment1);
        treatmentList.add(treatment2);
        treatmentList.add(treatment3);

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

        List<BookingTreatmentDto> bookingTreatments = new ArrayList<>();
        bookingTreatments.add(bookingTreatmentDto2);
        bookingTreatments.add(bookingTreatmentDto2);

        //treatment1.setBookingTreatments(bookingTreatmentArgumentCaptor);
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
     void getTreatmentsByTypeThrowsExeption(){
        TreatmentDto treatmentDto = new TreatmentDto();

        assertThrows(RecordNotFoundException.class, () -> treatmentServiceImpl.getTreatmentsByType(TreatmentType.BODY_TREATMENT));
     }


  /*  @Test
    void addTreatment() {
       //Arrange
        TreatmentDto treatmentDto4 = new TreatmentDto();
        treatmentDto4.id = 4L;
        treatmentDto4.name = "Green sea peeling";
        treatmentDto4.type = TreatmentType.FACIAL_TREATMENT;
        treatmentDto4.description = "Natural exfoliating";
        treatmentDto4.duration = 45;
        treatmentDto4.price = 90;

        Treatment treatment = new Treatment();
        treatment.setId(treatmentDto4.id);
        treatment.setName(treatmentDto4.name);
        treatment.setType(treatmentDto4.type);
        treatment.setDescription(treatmentDto4.description);
        treatment.setDuration(treatmentDto4.duration);
        treatment.setPrice(treatmentDto4.price);

        when(treatmentRepository.save(any(Treatment.class))).thenReturn(treatment);

        // Act
        treatmentServiceImpl.addTreatment(treatmentServiceImpl.transferTreatmentToDto(treatment));
        verify(treatmentRepository, times(1)).save(treatmentArgumentCaptor.capture());
        Treatment savedTreatment = treatmentArgumentCaptor.getValue();

        //Assert
        assertEquals(treatmentDto4.name, savedTreatment.getName());
        assertEquals(treatmentDto4.type, savedTreatment.getType());
        assertEquals(treatmentDto4.description, savedTreatment.getDescription());
        assertEquals(treatmentDto4.duration, savedTreatment.getDuration());
        assertEquals(treatmentDto4.price, savedTreatment.getPrice());

    }*/

   /* @Test
    void addTreatment() {
        // Arrange
        TreatmentDto treatmentDto = new TreatmentDto();
        treatmentDto.id = 4L;
        treatmentDto.name = "Green sea peeling";
        treatmentDto.type = TreatmentType.FACIAL_TREATMENT;
        treatmentDto.description = "Natural exfoliating";
        treatmentDto.duration = 45;
        treatmentDto.price = 90;

        Treatment savedTreatment = new Treatment();
        savedTreatment.setId(treatmentDto.id);
        savedTreatment.setName(treatmentDto.name);
        savedTreatment.setType(treatmentDto.type);
        savedTreatment.setDescription(treatmentDto.description);
        savedTreatment.setDuration(treatmentDto.duration);
        savedTreatment.setPrice(treatmentDto.price);

        when(treatmentRepository.save(any(Treatment.class))).thenReturn(savedTreatment);

        // Act
        TreatmentDto result = treatmentServiceImpl.addTreatment(treatmentDto);

        // Assert
        verify(treatmentRepository, times(1)).save(treatmentArgumentCaptor.capture());
        Treatment capturedTreatment = treatmentArgumentCaptor.getValue();

        assertEquals(treatmentDto.name, capturedTreatment.getName());
        assertEquals(treatmentDto.type, capturedTreatment.getType());
        assertEquals(treatmentDto.description, capturedTreatment.getDescription());
        assertEquals(treatmentDto.duration, capturedTreatment.getDuration());
        assertEquals(treatmentDto.price, capturedTreatment.getPrice());

        assertEquals(treatmentDto.id, result.id);
        assertEquals(treatmentDto.name, result.name);
        assertEquals(treatmentDto.type, result.type);
        assertEquals(treatmentDto.description, result.description);
        assertEquals(treatmentDto.duration, result.duration);
        assertEquals(treatmentDto.price, result.price);
    }*/


    @Test
    void updateTreatment() {
        //arrange
        long treatmentId = 1L;
        TreatmentDto treatmentDto = new TreatmentDto();
        treatmentDto.setDescription("korting");
        treatmentDto.setPrice(50);

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
    void updateTreatmentWithCalendar() {
        //arrange
        long treatmentId = 1L;
        TreatmentDto treatmentDto = new TreatmentDto();
        treatmentDto.setName("updated name");
        treatmentDto.setDescription("korting");
        treatmentDto.setPrice(50);

        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setDate(LocalDate.of(2023, 12, 4));
        calendarDto.setStartTime(LocalTime.of(10, 0));
        calendarDto.setEndTime(LocalTime.of(10, 50));

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

        assertEquals(calendarDto.getDate(), updatedCalendar.getDate());
        assertEquals(calendarDto.getStartTime(), updatedCalendar.getStartTime());
        assertEquals(calendarDto.getEndTime(), updatedCalendar.getEndTime());
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

    @Test
    void addBookingTreatmentToTreatment() {
        //arrange
        long treatmentId = 2L;
        Treatment existingTreatment = new Treatment();
        existingTreatment.setId(treatmentId);

        BookingTreatmentDto bookingTreatmentDto = new BookingTreatmentDto();
        //bookingTreatment.setId(2L);
        bookingTreatmentDto.setQuantity(3);
        bookingTreatmentDto.setTreatment(treatment2);
        bookingTreatmentDto.setCustomerName("Nate Cole");
        bookingTreatmentDto.setCustomerEmail("Nate@example.com");

        BookingTreatment savedBookingTreatment = new BookingTreatment();
        savedBookingTreatment.setQuantity(1);
        savedBookingTreatment.setTreatment(treatment1);
        savedBookingTreatment.setCustomerName("maria cruz");
        savedBookingTreatment.setCustomerEmail("maria@example.com");

        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(existingTreatment));
        when(bookingTreatmentRepository.save(any(BookingTreatment.class))).thenReturn(savedBookingTreatment);

        //act
        BookingTreatmentDto result = treatmentServiceImpl.addBookingTreatmentToTreatment(treatmentId, bookingTreatmentDto);

        //assert
        assertNotNull(result);
        assertEquals(bookingTreatmentDto.getQuantity(), result.getQuantity());

        verify(treatmentRepository).findById(treatmentId);
        verify(bookingTreatmentRepository).save(any(BookingTreatment.class));

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