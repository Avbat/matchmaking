package io.cell.matchmaking.controllers;

import io.cell.matchmaking.model.Participant;
import io.cell.matchmaking.services.RegistrationResult;
import io.cell.matchmaking.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/users")
    public RegistrationResult register(@RequestParam String name,
                                       @RequestParam Double skill,
                                       @RequestParam Double latency) {
        Participant registered = new Participant()
                .setName(name)
                .setSkill(skill)
                .setLatency(latency);
        return registrationService.register(registered);
    }
}
