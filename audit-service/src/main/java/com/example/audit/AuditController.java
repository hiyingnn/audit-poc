package com.example.audit;

import com.example.domain.Auditable;
import com.example.domain.CareerHistory;
import lombok.RequiredArgsConstructor;
import org.javers.core.Changes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public Page<CareerHistory> getAllVersionsById(Pageable pageable, @PathVariable("id") String id) {
        return auditService.getAllVersionsById(pageable, id);
    }

    @GetMapping("/shadows/{id}/version/{version}")
    public Optional<CareerHistory> getVersionByIdAndVersion(@PathVariable String id, @PathVariable Long version) {
        return auditService.getVersionByIdAndVersion(id, version);
    }

    @GetMapping("/changes/{id}")
    public Changes getChangesByIdGroupedByCommit(@PathVariable String id, @RequestParam(value = "property", required = false) String property, @RequestParam(value = "isValueObject", required = false) boolean isValueObject) {
        return auditService.getChangesByIdGroupedByCommit(id, property, isValueObject);
    }
}
