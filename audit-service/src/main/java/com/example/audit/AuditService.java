package com.example.audit;

import com.example.domain.Auditable;
import lombok.RequiredArgsConstructor;
import org.javers.core.Javers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final Javers javers;

    public void commit(String collection, Auditable auditable) {
        javers.commit(collection, auditable);
    }

}
