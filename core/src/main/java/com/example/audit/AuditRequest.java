package com.example.audit;

import com.example.career.domain.Auditable;
import com.example.career.domain.CareerHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRequest {
    CareerHistory careerHistory;
    String collection;
}
