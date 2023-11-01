package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.models.TreatmentType;
import com.wendy.backendassignment.repositories.CalendarRepository;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final CalendarRepository calendarRepository;

    public TreatmentService(TreatmentRepository treatmentRepository, CalendarRepository calendarRepository) {
        this.treatmentRepository = treatmentRepository;
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

    public List<TreatmentDto> getTreatmentsByType(TreatmentType type) throws RecordNotFoundException {
        List<Treatment> treatments = treatmentRepository.findTreatmentsByType(type);

        if(treatments.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment to find with this type: " + type);
        }
        List<TreatmentDto> treatmentDtos = new ArrayList<>();

        treatments.forEach(treatment -> {
            treatmentDtos.add(transferTreatmentToDto(treatment));
        });

        return treatmentDtos;
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

        if (calendarDto != null) {
            Calendar calendar = treatment.getCalendar();
            if (calendar == null) {
                calendar = new Calendar();
                treatment.setCalendar(calendar);
            }
            if (calendarDto.getDate() != null) {
                calendar.setDate(calendarDto.getDate());
            }
            if (calendarDto.getStartTime() != null) {
                calendar.setStartTime(calendarDto.getStartTime());
            }
            if (calendarDto.getEndTime() != null) {
                calendar.setEndTime(calendarDto.getEndTime());
            }
            calendarRepository.save(calendar);
        }
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
        treatmentRepository.save(treatment);

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

    public TreatmentDto transferTreatmentToDto (Treatment treatment){
        TreatmentDto treatmentDto = new TreatmentDto();

        treatmentDto.id = treatment.getId();
        treatmentDto.description = treatment.getDescription();
        treatmentDto.duration = treatment.getDuration();
        treatmentDto.name = treatment.getName();
        treatmentDto.type = treatment.getType();
        treatmentDto.price = treatment.getPrice();
        treatmentDto.calendar = treatment.getCalendar();
        return treatmentDto;
    }

    public Treatment transferDtoToTreatment (TreatmentDto treatmentDto){
        Treatment treatment = new Treatment();

        treatment.setId(treatmentDto.id);
        treatment.setDescription(treatmentDto.description);
        treatment.setDuration(treatmentDto.duration);
        treatment.setName(treatmentDto.name);
        treatment.setType(treatmentDto.type);
        treatment.setPrice(treatmentDto.price);
        treatment.setCalendar(treatmentDto.calendar);
        return treatment;
    }

}
