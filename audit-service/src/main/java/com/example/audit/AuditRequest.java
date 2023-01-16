package com.example.audit;

import com.example.domain.Auditable;
import lombok.Data;

@Data
public class AuditRequest {
    Auditable auditable;
    String collection;
}
