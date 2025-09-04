package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
