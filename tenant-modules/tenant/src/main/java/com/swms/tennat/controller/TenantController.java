package com.swms.tennat.controller;

import com.swms.tenant.api.dto.TenantDTO;
import com.swms.tennat.model.entity.Tenant;
import com.swms.tennat.model.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/addsource/{tenant}")
    public ResponseEntity<Object> addTenant(@RequestBody TenantDTO tenantDTO) {

        Tenant tenant = new Tenant();
        tenant.setTenantId(Arrays.toString(DigestUtils.md5Digest(tenantDTO.getName().getBytes(StandardCharsets.UTF_8))));

        //TODO  2030-07-01
        // 暂时先使用root,root,后期通过策略来实现，租户的数据库账号密码
        tenant.setUsername("root");
        tenant.setPassword("root");
        tenant.setUrl("jdbc:mysql://localhost:3306/" + tenant.getName() + "?useSSL=false");
        tenant.setDriverClassName("com.mysql.cj.jdbc.Driver");
        tenantRepository.save(tenant);

        //TODO 使用liquibase来完成数据库的创建

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }
}
