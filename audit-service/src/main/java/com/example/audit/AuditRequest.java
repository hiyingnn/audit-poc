package com.example.audit;

import com.example.domain.Auditable;
import com.example.domain.CareerHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditRequest {
    CareerHistory careerHistory;
    String collection;
}
