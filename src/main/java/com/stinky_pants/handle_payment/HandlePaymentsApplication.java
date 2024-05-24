package com.stinky_pants.handle_payment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = "classpath:handle-payment.bpmn")
public class HandlePaymentsApplication implements CommandLineRunner {

	@Autowired
	private ZeebeClient zeebeClient;

	private final static Logger LOG = LoggerFactory.getLogger(HandlePaymentsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HandlePaymentsApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		var processDefinitionKey = "handle-payment-final";
		var event = zeebeClient.newCreateInstanceCommand()
				.bpmnProcessId(processDefinitionKey)
				.latestVersion()
				.variables(Map.of("total", 100))
				.send()
				.join();
		LOG.info(String.format("started a process: %d", event.getProcessInstanceKey()));
	}

}
