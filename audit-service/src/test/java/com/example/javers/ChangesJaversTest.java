package com.example.javers;

import com.example.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class ChangesJaversTest {

    private final String OBJECT_ID = "1";
    @Autowired
    Javers javers;
    private CareerHistory.CareerHistoryBuilder<?, ?> careerHistoryBaseBuilder;

    @BeforeEach
    void mockCareerHistories() {
        Appointment ap1 = Appointment.builder()
                .rank("ap1.rank")
                .position("ap1.position")
                .references(List.of())
                .build();

        Certification cert1 = Certification.builder()
                .issuedBy("cer1.issuedBy")
                .name("cer1.name")
                .references(List.of())
                .build();

        Certification cert2 = Certification.builder()
                .issuedBy("cer2.name")
                .name("cer2.issuedBy")
                .references(List.of())
                .build();

        careerHistoryBaseBuilder = CareerHistory
                .builder()
                .id(OBJECT_ID)
                .appointment(ap1)
                .lastDrawnSalary("l1")
                .certs(List.of(cert1, cert2))
                .skills(List.of("skill1", "skill2"));
    }

    @Test
    void allValueObjects_thenCollectionChangesByIndex() {
        /**
         * Precondition
         * careerHistory - entity (EntityId == id)
         * appointment, certs,lastDrawnSalary - value object (default setting)
         */

        Certification cert4 = Certification.builder()
                .issuedBy("cer4.name")
                .name("cer4.issuedBy")
                .references(List.of())
                .build();

        Certification cert1 = Certification.builder()
                .issuedBy("cer1.issuedBy")
                .name("cer1.name")
                .references(List.of())
                .build();

        CareerHistory careerHistory1 = careerHistoryBaseBuilder.build();

        CareerHistory careerHistoryCertsChange = careerHistoryBaseBuilder
                .lastDrawnSalary("l2")
                .certs(List.of(cert4, cert1))
                .skills(List.of("skill13", "skill4"))
                .build();


        var changes = javers.compare(careerHistory1, careerHistoryCertsChange);

        var certChanges = changes.getPropertyChanges("certs");

        log.error(changes.prettyPrint());
        log.error(certChanges.toString());

    }

    @Test
    void collectionElementsEntity_thenCollectionChangesById() {
        /**
         * Precondition
         * careerHistory, certs - entity (EntityId == id)
         * appointment,lastDrawnSalary - value object (default setting)
         */

        Appointment ap2 = Appointment.builder()
                .rank("ap2.rank")
                .position("ap2.position")
                .references(List.of())
                .build();

        Certification cert3 = Certification.builder()
                .issuedBy("cer3.name")
                .name("cer3.issuedBy")
                .references(List.of())
                .build();


        Source source1 = Source.builder()
                .dateObtained(LocalDateTime.now())
                .referenceType(ReferenceType.FACEBOOK)
                .comment("source1.comment")
                .build();

        Source source2 = Source.builder()
                .dateObtained(LocalDateTime.now())
                .referenceType(ReferenceType.INDEED)
                .comment("source2.comment")
                .build();

        var refBuilder1 = Reference
                .builder()
                .sources(List.of(source1));

        var refBuilder2 = Reference
                .builder()
                .sources(List.of(source1, source2));

        var refBuilder3 = Reference
                .builder()
                .sources(List.of(source2));


        CareerHistory careerHistory1 = careerHistoryBaseBuilder.build();

        CareerHistory careerHistoryReferenceAdd = careerHistoryBaseBuilder
                .references(List.of(refBuilder1
                        .field("lastDrawnSalary")
                        .content(careerHistory1.getLastDrawnSalary())
                        .build()))
                .build();


        var changes = javers.compare(careerHistory1, careerHistoryReferenceAdd);

        log.error(changes.prettyPrint());

        CareerHistory careerHistoryReferenceChange = careerHistoryBaseBuilder
                .references(List.of(refBuilder3
                        .field("lastDrawnSalary")
                        .content(careerHistory1.getLastDrawnSalary())
                        .build()))
                .build();


        var changes2 = javers.compare(careerHistoryReferenceAdd, careerHistoryReferenceChange);

        log.error(changes2.prettyPrint());


        CareerHistory careerHistoryReferenceAppend = careerHistoryBaseBuilder
                .lastDrawnSalary("5kk")
                .references(List.of(refBuilder2
                        .field("lastDrawnSalary")
                        .content("5kk")
                        .build()))
                .build();

        var changes3 = javers.compare(careerHistoryReferenceAdd, careerHistoryReferenceAppend);

        log.error(changes3.prettyPrint());

    }


}
