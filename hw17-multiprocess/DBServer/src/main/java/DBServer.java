import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"hw17"})
@SpringBootApplication
public class DBServer {
    public static void main(String[] args) {
        System.out.println("1");
        // SpringApplication.run(Server.class, args);
    }
}
