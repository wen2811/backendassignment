package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.TreatmentType;
import com.wendy.backendassignment.services.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    //Read
    @GetMapping(value="")
    public ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        return ResponseEntity.ok().body(treatmentService.getAllTreatments());
    }

    @GetMapping(path="/{type}")
    public ResponseEntity<List<TreatmentDto>> getTreatmentsByType(@PathVariable("type") String type) throws RecordNotFoundException {
        System.out.println(TreatmentType.valueOf(type));
        return ResponseEntity.ok().body(treatmentService.getTreatmentsByType(TreatmentType.valueOf(type)));
    }

    //Create
    @PostMapping(value="")
    public ResponseEntity<Object> addTreatment(@Valid @RequestBody TreatmentDto treatmentDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            TreatmentDto addedTreatment = treatmentService.addTreatment(treatmentDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedTreatment.id)));
            return ResponseEntity.created(uri).body(addedTreatment);
        }
    }

    //Update
    @PutMapping(path="/{id}")
    public ResponseEntity<Object> updateTreatment(@PathVariable("id") Long id, @RequestBody TreatmentDto treatmentDto) {
        treatmentService.updateTreatment(id, treatmentDto);
        return ResponseEntity.ok(treatmentDto);
    }

    //Delete
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Object> deleteTreatment(@PathVariable Long id) throws RecordNotFoundException  {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    //Methods for relation
    @PutMapping(path="/updatewithcalendar/{id}")
    public ResponseEntity<Object> updateTreatmentWithCalendar(@PathVariable Long id, @RequestBody TreatmentDto treatmentDto, @RequestBody CalendarDto calendarDto) throws RecordNotFoundException {
        treatmentService.updateTreatmentWithCalendar(id, treatmentDto, calendarDto);
        return ResponseEntity.ok("Behandeling met kalendergegevens is succesvol bijgewerkt.");
    }

    @GetMapping(path="/{id}/with-calendar")
    public ResponseEntity<TreatmentDto> getTreatmentWithCalendar(@PathVariable Long id) throws RecordNotFoundException {
        TreatmentDto treatmentDto = treatmentService.getTreatmentWithCalendar(id);
        return ResponseEntity.ok(treatmentDto);
    }



}
