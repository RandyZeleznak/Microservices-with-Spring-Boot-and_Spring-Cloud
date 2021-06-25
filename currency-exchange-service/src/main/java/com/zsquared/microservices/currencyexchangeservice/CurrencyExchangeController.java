/**
 * 
 */
package com.zsquared.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Randy
 *
 */
@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retreiveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue =  repository.findByFromAndTo(from, to);
		
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}

}
