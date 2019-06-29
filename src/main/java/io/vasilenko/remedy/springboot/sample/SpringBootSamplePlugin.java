package io.vasilenko.remedy.springboot.sample;

import com.bmc.arsys.api.Value;
import com.bmc.arsys.pluginsvr.plugins.ARFilterAPIPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;
import io.vasilenko.remedy.springboot.sample.service.PluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {JmxAutoConfiguration.class})
public class SpringBootSamplePlugin extends ARFilterAPIPlugin {

    private static final int INPUT_NAME_VALUE_INDEX = 0;

    private final Logger log = LoggerFactory.getLogger(SpringBootSamplePlugin.class);

    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private PluginService service;

    @Override
    public void initialize(ARPluginContext context) {
        applicationContext = SpringApplication.run(SpringBootSamplePlugin.class);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        log.info("initialized");
    }

    @Override
    public List<Value> filterAPICall(ARPluginContext context, List<Value> inputValues) {
        String name = String.valueOf(inputValues.get(INPUT_NAME_VALUE_INDEX));
        log.info("name: {}", name);
        String greeting = service.greeting(name);
        log.info("greeting: {}", greeting);
        List<Value> outputList = new ArrayList<>();
        outputList.add(new Value(greeting));
        return outputList;
    }

    @Override
    public void terminate(ARPluginContext context) {
        applicationContext.close();
    }
}
