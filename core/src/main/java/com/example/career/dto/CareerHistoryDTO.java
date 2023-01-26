package com.example.career.dto;

import com.example.common.ReferencesDTO;
import com.example.config.ValidReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldNameConstants
@SuperBuilder
@NoArgsConstructor
@ValidReference
public class CareerHistoryDTO extends ReferencesDTO {
    String id;
    String company;

    @Valid AppointmentDTO appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;

    List<@Valid CertificationToFieldDTO> certs;
    Long version;

    @Override
    public Set<String> getMandatoryReferences() {
        return Set.of(Fields.company, Fields.skills);
    }

    @Override
    public Set<String> getOptionalReferences() {
        return Set.of(Fields.duration);
    }
}
