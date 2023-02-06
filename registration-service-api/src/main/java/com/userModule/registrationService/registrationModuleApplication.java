package com.userModule.registrationService;

import com.userModule.registrationService.Entity.Role;
import com.userModule.registrationService.Repository.roleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
@Slf4j
public class registrationModuleApplication implements CommandLineRunner {

    @Autowired
    private roleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(registrationModuleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role role_admin = Role.builder().roleId("admin").roleName("role_admin").userId("SuperAdmin").createdAt(new Date()).isActive(true).build();
            Role role_normal = Role.builder().roleId("user").roleName("role_user").userId("SuperAdmin").createdAt(new Date()).isActive(true).build();
            log.warn("Adding Role");
            List<Role> roles = new ArrayList<>(List.of(role_normal, role_admin));
            this.roleRepository.saveAll(roles);
        }
    }
}
