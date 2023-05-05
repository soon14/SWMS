package com.swms.user.tenant.rest;

import com.swms.user.api.dto.TenantDTO;
import com.swms.user.tenant.model.Tenant;
import com.swms.user.tenant.repository.TenantRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/addsource/{tenant}")
    public ResponseEntity<Object> addDSource(@RequestBody TenantDTO tenantDTO) {

        Tenant tenant = new Tenant();
        tenant.setTenantId(DigestUtils.md5Hex(tenantDTO.getName()));

        //TODO  2030-07-01
        // 暂时先使用root,root,后期通过策略来实现，租户的数据库账号密码
        tenant.setUsername("root");
        tenant.setPassword("root");
        tenant.setUrl("jdbc:mysql://localhost:3306/" + tenant.getName() + "?useSSL=false");
        tenant.setDriverClassName("com.mysql.cj.jdbc.Driver");
        tenantRepository.insert(tenant);

        //TODO 使用liquibase来完成数据库的创建


        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public List<Tenant> getAll() {
        return tenantRepository.selectList(null);
    }
}
