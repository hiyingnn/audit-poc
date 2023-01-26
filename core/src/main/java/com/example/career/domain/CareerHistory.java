package com.example.career.domain;

import com.example.common.References;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TypeAlias("careerHistory")
@SuperBuilder(toBuilder = true)
@Document(collection = "career")
@NoArgsConstructor
public class CareerHistory extends References implements Auditable {
    @MongoId
    String id;
    String company;
    Appointment appointment;
    String duration;
    String lastDrawnSalary;
    List<String> skills;
    List<Certification> certs;

    @Version
    Long version;

    public static String getCollectionName() {
        return null;
    }
}
