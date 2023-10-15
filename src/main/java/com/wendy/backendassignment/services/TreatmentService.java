package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
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
}
