package com.example.audit;

import com.example.domain.CareerHistory;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.commit.CommitId;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final Javers javers;

    public void commit(String collection, CareerHistory careerHistory) {
        javers.commit(collection, careerHistory);
    }

    public List<Shadow<CareerHistory>> getAllShadowsById(String id) {
        JqlQuery query = QueryBuilder.byClass(CareerHistory.class).build();
        return javers.findShadows(query);
    }

    public Changes getChangesByIdGroupedByCommit(String id){
        JqlQuery query = QueryBuilder.byInstanceId(id, CareerHistory.class).build();
        return javers.findChanges( query );
    }

}
