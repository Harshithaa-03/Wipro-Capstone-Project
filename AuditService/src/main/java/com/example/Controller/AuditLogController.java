package com.example.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.AuditLogDTO;
import com.example.Entity.AuditLog;
import com.example.Service.AuditLogService;

@RestController
@RequestMapping("/api/audit")

public class AuditLogController {
	
	@Autowired
    private AuditLogService auditlogService;

    @PostMapping("/save")
    public String saveAudit(@RequestBody AuditLogDTO auditLogDTO) {
        auditlogService.saveAudit(auditLogDTO);
        return "Audit log saved.";
    }

    @GetMapping("/logs")
    public Page<AuditLog> getLogs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "timestamp") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
            Sort.by(sortBy).ascending() :
            Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return auditlogService.getAuditLogs(pageable);
    }

}
