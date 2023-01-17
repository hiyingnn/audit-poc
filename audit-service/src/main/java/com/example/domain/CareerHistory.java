package com.example.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TypeAlias("careerHistory")
@TypeName("careerHistory")
@SuperBuilder(toBuilder = true)
@Document(collection = "career")
@NoArgsConstructor
@Entity
public class CareerHistory extends References {
    @MongoId
    @Id
    String id;
    String company;
    Appointment appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;
    List<Certification> certs;
}
