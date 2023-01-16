package com.example.audit;

import com.example.domain.Auditable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping
    public <T extends Auditable> void saveHistory(@RequestBody AuditRequest auditRequest) {
        auditService.commit(auditRequest.getCollection(), auditRequest.getAuditable());
    }
}
