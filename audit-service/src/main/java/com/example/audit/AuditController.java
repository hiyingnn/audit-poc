package com.example.audit;

import com.example.domain.Auditable;
import com.example.domain.CareerHistory;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.javers.shadow.Shadow;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping
    public <T extends Auditable> void saveHistory(@RequestBody AuditRequest auditRequest) {
        auditService.commit(auditRequest.getCollection(), auditRequest.getCareerHistory());
    }

    @GetMapping("/shadows/{id}")
    public List<CareerHistory> getAllShadowsInstanceById(@PathVariable("id") String id ) {
        return auditService.getAllShadowsById(id);
    }

    @GetMapping("/changes/{id}")
    public Changes getChangesByIdGroupedByCommit(@PathVariable("id") String id ) {
        return auditService.getChangesByIdGroupedByCommit(id);
    }
}
