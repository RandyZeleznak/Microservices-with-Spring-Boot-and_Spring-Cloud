/**
 * 
 */
package com.zsquared.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retreiveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange =  repository.findByFromAndTo(from, to);
		
		if(currencyExchange == null) {
			throw new RuntimeException("Did not find From "+from+" to "+to+" combination");
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		logger.info("{}", currencyExchange);
		return currencyExchange;
	}

}
