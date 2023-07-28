package com.swms.mdm.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MdmTestApplication.class})
public class BaseTest {

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}
