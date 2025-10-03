package vn.iostar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.iostar.entity.Customer;

import java.util.List;

@RestController
public class CustomerController {

    private final List<Customer> customers = List.of(
            new Customer("001", "Hoàng Kim", "0123456789", "kimspkt@gmail.com"),
            new Customer("002", "Kim", "0987654321", "hoangkim@gmail.com")
    );

    // Sau khi login thành công mới vào đây
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is guest");
    }

    // Chỉ ADMIN mới xem được toàn bộ danh sách
    @GetMapping("/customer/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> getCustomerList() {
        return ResponseEntity.ok(customers);
    }

    // Chỉ USER mới xem được chi tiết theo id
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
