package cat.itacademy.s04.t02.n01.fruitapimysql.controllers;

import cat.itacademy.s04.t02.n01.fruitapimysql.status.HealthStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public HealthStatus healthControl () {
        return new HealthStatus("OK");
    }
}
