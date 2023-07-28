package com.swms.tennat.controller;

import static com.swms.utils.http.Response.SUCCESS_CODE;

import com.swms.tenant.api.dto.TenantDTO;
import com.swms.tennat.model.entity.Tenant;
import com.swms.tennat.model.repository.TenantRepository;
import com.swms.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @PostMapping("/addTenant/")
    public Response<Object> addTenant(@RequestBody TenantDTO tenantDTO) {

        Tenant tenant = new Tenant();
        tenant.setName(tenantDTO.getName());
        tenant.setTenantId(Arrays.toString(DigestUtils.md5Digest(tenantDTO.getName().getBytes(StandardCharsets.UTF_8))));
        //TODO  2030-07-01
        // 暂时先使用root,root,后期通过策略来实现，租户的数据库账号密码
        tenant.setUsername("root");
        tenant.setPassword("root");
        tenant.setUrl("jdbc:mysql://localhost:3306/" + tenant.getName() + "?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true");
        tenant.setDriverClassName("com.mysql.cj.jdbc.Driver");
        tenantRepository.save(tenant);

        //TODO 使用liquibase来完成数据库的创建

        //TODO add default root user with sql not api

        //TODO send email to tenant with username and password and login url

        return Response.success();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public Response<Object> getAll() {
        return Response.builder().code(SUCCESS_CODE).data(tenantRepository.findAll()).build();
    }
}
