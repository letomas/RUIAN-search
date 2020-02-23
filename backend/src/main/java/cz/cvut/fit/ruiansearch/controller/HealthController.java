package cz.cvut.fit.ruiansearch.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/health")
public class HealthController {
    @GetMapping({"", "/"})
    public RedirectView getHealthStatus() {
        return new RedirectView("/actuator/health");
    }
}
