package io.vasilenko.remedy.springboot.sample.service.impl;

import io.vasilenko.remedy.springboot.sample.service.PluginService;
import org.springframework.stereotype.Service;

@Service
public class PluginServiceImpl implements PluginService {

    @Override
    public String greeting(String name) {
        return "Hello " + name;
    }
}
