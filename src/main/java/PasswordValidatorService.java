import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;


@ImportResource("classpath:beans.xml")
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class PasswordValidatorService {

    public static void main(String[] args) {
        SpringApplication.run(PasswordValidatorService.class, args);

    }
}
