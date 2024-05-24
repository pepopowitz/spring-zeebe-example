package com.stinky_pants.handle_payment;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChargeCreditCardWorker {
  private final static Logger LOG = LoggerFactory.getLogger(ChargeCreditCardWorker.class);

  public record ChargeRequest(
      Double total,
      Double totalWithTax) {
  }

  @JobWorker(type = "charge-credit-card")
  public Map<String, Double> chargeCreditCard(final ActivatedJob job) {

    final double totalWithTax = job.getVariablesAsType(ChargeRequest.class).totalWithTax;

    // Pretend we're actually charging the credit card here
    LOG.info("charging credit card: {}", totalWithTax);

    return Map.of("amountCharged", totalWithTax);
  }
}