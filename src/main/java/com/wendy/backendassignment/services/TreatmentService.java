package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.models.TreatmentType;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import com.wendy.backendassignment.repositories.CalendarRepository;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final CalendarRepository calendarRepository;

    public TreatmentService(TreatmentRepository treatmentRepository, BookingTreatmentRepository bookingTreatmentRepository, CalendarRepository calendarRepository) {
        this.treatmentRepository = treatmentRepository;
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.calendarRepository = calendarRepository;
    }

    //Read
    public List<TreatmentDto> getAllTreatments() {
        Iterable<Treatment> treatments = treatmentRepository.findAll();
        List<TreatmentDto> treatmentDtos = new ArrayList<>();

        for (Treatment treatment: treatments) {
            treatmentDtos.add(transferTreatmentToDto(treatment));
        }

        return treatmentDtos;
    }

    public TreatmentDto getTreatmentsByType(TreatmentType type) throws RecordNotFoundException {
        Optional<Treatment> treatmentOptional = treatmentRepository.findTreatmentsByType(type);

        if(treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment to find with this type: " + type);
        }
        Treatment treatment = treatmentOptional.get();

        return transferTreatmentToDto(treatment);
    }

    //Create
    public TreatmentDto addTreatment(TreatmentDto treatmentDto) {
        Treatment treatment = transferDtoToTreatment(treatmentDto);
        treatmentRepository.save(treatment);

        return transferTreatmentToDto(treatment);
    }

    //Update
    public void updateTreatment(Long id, TreatmentDto treatmentDto) {
        Treatment existingTreatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("There's no treatment found with this id " + id));
        if (treatmentDto.getDescription() != null) {
            existingTreatment.setDescription(treatmentDto.getDescription());
        }
        if (treatmentDto.getDuration() != 0.0) {
            existingTreatment.setDuration(treatmentDto.getDuration());
        }
        if (treatmentDto.getName() != null) {
            existingTreatment.setName(treatmentDto.getName());
        }
        if (treatmentDto.getType() != null) {
            existingTreatment.setType(treatmentDto.getType());
        }
        if (treatmentDto.getPrice() != 0.0) {
            existingTreatment.setPrice(treatmentDto.getPrice());
        }
        treatmentRepository.save(existingTreatment);
    }

    //Delete
    public void deleteTreatment(Long id) throws RecordNotFoundException {
        Optional<Treatment> optionalTreatment = treatmentRepository.findById(id);
        if(optionalTreatment.isEmpty()) {
            throw new RecordNotFoundException("There is no booking found with id: " + id);
        }
        Treatment treatment = optionalTreatment.get();
        treatmentRepository.delete(treatment);
    }

    //methods for relations
    public TreatmentDto updateTreatmentWithCalendar(Long id, TreatmentDto treatmentDto, CalendarDto calendarDto) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);

        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + id);
        }
        Treatment treatment = treatmentOptional.get();
        if (treatmentDto != null) {
            if (treatmentDto.getDescription() != null) {
                treatment.setDescription(treatmentDto.getDescription());
            }
            if (treatmentDto.getDuration() != 0.0) {
                treatment.setDuration(treatmentDto.getDuration());
            }
            if (treatmentDto.getName() != null) {
                treatment.setName(treatmentDto.getName());
            }
            if (treatmentDto.getType() != null) {
                treatment.setType(treatmentDto.getType());
            }
            if (treatmentDto.getPrice() != 0.0) {
                treatment.setPrice(treatmentDto.getPrice());
            }
        }
        Calendar calendar = treatment.getCalendar();
        if (calendarDto != null) {
            if (calendarDto.getDate() != null) {
                calendar.setDate(calendarDto.getDate());
            }
            if (calendarDto.getStartTime() != null) {
                calendar.setStartTime(calendarDto.getStartTime());
            }
            if (calendarDto.getEndTime() != null) {
                calendar.setEndTime(calendarDto.getEndTime());
            }
        }
        treatmentRepository.save(treatment);
        calendarRepository.save(calendar);

        return transferTreatmentToDto(treatment);
    }

    public TreatmentDto getTreatmentWithCalendar(Long id) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);

        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + id);
        }

        Treatment treatment = treatmentOptional.get();
        Calendar calendar = treatment.getCalendar();

        if (calendar == null) {
            throw new RecordNotFoundException("There's no calendar associated with this treatment.");
        }

        return transferTreatmentToDto(treatment);
    }


    public BookingTreatmentDto addBookingTreatmentToTreatment(Long treatmentId, BookingTreatmentDto bookingTreatmentDto) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentId);

        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + treatmentId);
        }

        Treatment treatment = treatmentOptional.get();
        BookingTreatment bookingTreatment = transferDtoToBookingTreatment(bookingTreatmentDto);
        bookingTreatment.setTreatment(treatment);
        bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
    }

    public BookingTreatmentDto updateBookingTreatment(Long id, BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);

        if (bookingTreatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("BookingTreatment not found with id: " + id);
        }
        BookingTreatment bookingTreatment = bookingTreatmentOptional.get();

        bookingTreatment.setQuantity(bookingTreatmentDto.getQuantity());
        bookingTreatment.setTreatment(bookingTreatmentDto.getTreatment());
        bookingTreatment.setBooking(bookingTreatmentDto.getBooking());

        bookingTreatment = bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
    }

    public BookingTreatmentDto getBookingTreatmentById(Long id) throws RecordNotFoundException {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);

        if (bookingTreatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("BookingTreatment not found with ID: " + id);
        }

        BookingTreatment bookingTreatment = bookingTreatmentOptional.get();
        return transferBookingTreatmentToDto(bookingTreatment);
    }



    public TreatmentDto transferTreatmentToDto (Treatment treatment){
        TreatmentDto treatmentDto = new TreatmentDto();

        treatmentDto.id = treatment.getId();
        treatmentDto.description = treatment.getDescription();
        treatmentDto.duration = treatment.getDuration();
        treatmentDto.name = treatment.getName();
        treatmentDto.type = treatment.getType();
        treatmentDto.price = treatment.getPrice();
        return treatmentDto;
    }

    public Treatment transferDtoToTreatment (TreatmentDto treatmentDto){
        Treatment treatment = new Treatment();

        treatmentDto.setId(treatmentDto.id);
        treatmentDto.setDescription(treatmentDto.description);
        treatmentDto.setDuration(treatmentDto.duration);
        treatmentDto.setName(treatmentDto.name);
        treatmentDto.setType(treatmentDto.type);
        treatmentDto.setPrice(treatmentDto.price);
        return treatment;
    }

    public BookingTreatmentDto transferBookingTreatmentToDto(BookingTreatment bookingTreatment) {
        BookingTreatmentDto bookingTreatmentDto = new BookingTreatmentDto();

        bookingTreatmentDto.setId(bookingTreatment.getId());
        bookingTreatmentDto.setQuantity(bookingTreatment.getQuantity());
        bookingTreatmentDto.setTreatment(bookingTreatment.getTreatment());
        bookingTreatmentDto.setBooking(bookingTreatment.getBooking());

        return bookingTreatmentDto;
    }

    public BookingTreatment transferDtoToBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = new BookingTreatment();

        bookingTreatment.setQuantity(bookingTreatmentDto.getQuantity());
        bookingTreatment.setTreatment(bookingTreatmentDto.getTreatment());
        bookingTreatment.setBooking(bookingTreatmentDto.getBooking());

        return bookingTreatment;
    }

}
