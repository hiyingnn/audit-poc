package com.example.audit;

import com.example.career.domain.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuditRequest {
    Auditable auditable;
    String collection;
}
