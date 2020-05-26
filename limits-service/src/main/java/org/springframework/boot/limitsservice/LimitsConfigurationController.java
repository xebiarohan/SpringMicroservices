package org.springframework.boot.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.limitsservice.beans.LimitConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;


    @GetMapping("/limits")
    public LimitConfiguration retreiveLimitsFromConfigurations(){
        return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
    }
}
