package com.example.audit;

import com.example.career.domain.CareerHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuditApi {

    private final RestTemplate client;


    public AuditApi(@Value("${api.audit-service.url}") String auditUrl, RestTemplateBuilder restTemplateBuilder) {
        this.client = restTemplateBuilder
                .rootUri(auditUrl)
                .build();
    }

    public void saveHistory(String collection, CareerHistory c) {
        this.client.postForEntity("/api/v1/audit", new AuditRequest(c, collection), AuditRequest.class);
    }


}
