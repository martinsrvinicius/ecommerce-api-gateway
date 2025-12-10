package com.vinim.api_gateway;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Disabled("Context test disabled while infrastructure is not configured")
class ApiGatewayApplicationTests {

	@Test
	void contextLoads() {
	}

}
