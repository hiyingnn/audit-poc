package com.example.audit;

import com.example.domain.CareerHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
    private final Javers javers;

    public void commit(String collection, CareerHistory careerHistory) {
        javers.commit(collection, careerHistory);
    }

    /**
     * Get all CareerHistory historical objects by id
     * (1: List all version)
     *
     * <p>
     * Paginated: by skip and limit
     * Order by: By default, commitId (Unable to define by other properties) https://github.com/javers/javers/issues/534
     *
     * @param id id of object
     * @return Page of object
     */
    public Page<CareerHistory> getAllVersionsById(Pageable pageable, String id) {

        var shadows = javers.findShadows(QueryBuilder
                .byInstanceId(id, CareerHistory.class)
                .withScopeCommitDeep()
                .limit(pageable.getPageSize() + (int) pageable.getOffset())
                .skip((int) pageable.getOffset())
                .build());
        var careerHistories = shadows.stream().map(Shadow::get).map(a -> (CareerHistory) a).toList();

        //TODO find efficient way of obtaining total number
        return new PageImpl<>(careerHistories, pageable, 0);
    }

    /**
     * Get CareerHistory historical objects by id and version
     * (2: List specific version)
     *
     * @param id      id of object
     * @param version Note that version is 1-indexed, whereas Mongo version is 0-indexed
     * @return
     */
    public Optional<CareerHistory> getVersionByIdAndVersion(String id, Long version) {

        var shadows = javers.findShadows(QueryBuilder
                .byInstanceId(id, CareerHistory.class)
                .withVersion(version)
                .build());

        return shadows.stream().map(Shadow::get).map(a -> (CareerHistory) a).findFirst();


    }

    public Changes getChangesByIdGroupedByCommit(String id, String property, boolean isValueObject) {

        JqlQuery query = QueryBuilder.byInstanceId(id, CareerHistory.class)
                .withChildValueObjects()
                .withScopeCommitDeep()
                .build();

        if (property != null && isValueObject) {
            query = QueryBuilder.byValueObjectId(id, CareerHistory.class, property)
                    .withChildValueObjects(false)
                    .withScopeCommitDeep()
                    .build();
        } else if (property != null && !isValueObject) {
            query = QueryBuilder.byInstanceId(id, CareerHistory.class)
                    .withChangedProperty(property)
                    .withChildValueObjects()
                    .withScopeCommitDeep()
                    .build();
        }

        Changes changes = javers.findChanges(query);
        log.warn(changes.prettyPrint());
        return changes;
    }

}
