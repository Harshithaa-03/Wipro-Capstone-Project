package com.example.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.DTO.AuditDTO;

@FeignClient(name = "audit-service", url = "http://localhost:8075")
public interface AuditClient {
	
	
	@PostMapping("/api/audit/save")
    void saveAudit(@RequestBody AuditDTO auditLDTO);

}
