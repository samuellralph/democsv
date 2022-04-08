package br.com.democsv.democsv;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@SpringBootTest
@Ignore
class DemocsvApplicationTests {
	
    @BeforeTestClass
    public static void setupHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }

	@Test
	void contextLoads() {
	}

}
