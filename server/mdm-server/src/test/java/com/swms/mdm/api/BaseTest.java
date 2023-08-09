package com.swms.mdm.api;

import com.swms.common.utils.user.UserContext;
import com.swms.tenant.config.util.TenantContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MdmTestApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseTest {

    @BeforeEach
    public void initBean() {
        //set default tenant
        TenantContext.setCurrentTenant("test");

        //set default user
        UserContext.setUserName("linsan");
    }

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
