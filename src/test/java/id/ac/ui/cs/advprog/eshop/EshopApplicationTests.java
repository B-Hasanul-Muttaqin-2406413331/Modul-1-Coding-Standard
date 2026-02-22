package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainCallsSpringApplicationRun() {
        String[] args = {"--spring.main.web-application-type=none"};
        ConfigurableApplicationContext context = Mockito.mock(ConfigurableApplicationContext.class);

        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(EshopApplication.class, args)).thenReturn(context);

            EshopApplication.main(args);

            mockedSpringApplication.verify(() -> SpringApplication.run(EshopApplication.class, args));
        }
    }

}
