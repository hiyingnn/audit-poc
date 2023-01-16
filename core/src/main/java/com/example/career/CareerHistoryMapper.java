package com.example.career;

import com.example.career.domain.Appointment;
import com.example.career.domain.CareerHistory;
import com.example.career.domain.Certification;
import com.example.career.dto.AppointmentDTO;
import com.example.career.dto.CareerHistoryDTO;
import com.example.career.dto.CertificationToFieldDTO;
import com.example.career.dto.CertificationToObjDTO;
import com.example.common.ReferenceResolver;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ReferenceResolver.class})
public interface CareerHistoryMapper {
    /**
     * From DTO to Domain object
     *
     * @param careerHistoryDTO
     * @return domain object stored in db
     */
    @Mapping(target = "references", ignore = true)
    CareerHistory toCareerHistory(CareerHistoryDTO careerHistoryDTO);

    /**
     * From Domain object to DTO
     *
     * @param careerHistory domain object from db
     * @return DTO object returned
     */
    @Mapping(target = "references", ignore = true)
    @InheritInverseConfiguration
    CareerHistoryDTO toCareerHistoryDTO(CareerHistory careerHistory);

    // Mapping inner classes
    Certification map(CertificationToFieldDTO value);

    Certification map(CertificationToObjDTO value);

    Appointment map(AppointmentDTO value);
}
