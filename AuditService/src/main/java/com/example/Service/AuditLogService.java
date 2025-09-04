package com.example.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.DTO.AuditLogDTO;
import com.example.Entity.AuditLog;
import com.example.Repository.AuditLogRepository;

@Service
public class AuditLogService {
	
	@Autowired
    private AuditLogRepository auditLogRepository;

    public void saveAudit(AuditLogDTO dto) {
        AuditLog log = new AuditLog();
        log.setSenderName(dto.getSenderName());
        log.setReceiverName(dto.getReceiverName());
        log.setAmount(dto.getAmount());
        log.setTimestamp(dto.getTimestamp());

        auditLogRepository.save(log);
    }

    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }

}
