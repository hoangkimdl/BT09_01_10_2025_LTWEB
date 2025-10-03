package vn.iostar.restcontroller;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World - no login required!";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "Welcome USER!";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Welcome ADMIN!";
    }
}
