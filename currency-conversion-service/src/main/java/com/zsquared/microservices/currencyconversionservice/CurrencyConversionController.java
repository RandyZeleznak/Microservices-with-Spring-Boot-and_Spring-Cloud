package com.zsquared.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable String from,
			@PathVariable String to, 
			@PathVariable BigDecimal quantity){
		
			HashMap<String, String> uriVariables = new HashMap<>();
			uriVariables.put("from", from);
			uriVariables.put("to", to);	
			ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
					"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
					CurrencyConversion.class, 
					uriVariables);
			
			CurrencyConversion response =  responseEntity.getBody();
			
			logger.info("{}", response);
			
			System.out.println(" REST");
		
				return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(), 
						quantity, quantity.multiply(response.getConversionMultiple()),  response.getEnvironment()+" "+"REST");
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeign(@PathVariable String from,
			@PathVariable String to, 
			@PathVariable BigDecimal quantity){
		
			
			
			CurrencyConversion response =  proxy.retreiveExchangeValue(from, to);
			if(response == null) {
				throw new RuntimeException("Not Working");
			}
			
			logger.info("{}", response);
			
			System.out.println(" REST");
		
				return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(), 
						quantity, quantity.multiply(response.getConversionMultiple()),  response.getEnvironment()+" "+"FEIGN");
	}
	


}
