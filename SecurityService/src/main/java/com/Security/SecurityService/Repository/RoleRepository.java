package com.Security.SecurityService.Repository;


import com.Security.SecurityService.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

