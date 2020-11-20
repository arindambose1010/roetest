package com.roe.service.application.controller;

import static java.time.LocalDate.now;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.roe.service.application.domain.ServiceResponse;
import com.roe.service.application.exception.CommonException;
import com.roe.service.application.exception.CommonExceptionAdvice;
import com.sun.media.jfxmedia.logging.Logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/roe")
public class RoeserviceController {

	@Autowired
	private RestTemplate restTemplate;
	
	@CrossOrigin
	@GetMapping(value="/exchangefactor/history/{limit}")
	@ResponseStatus(HttpStatus.OK)
	public ServiceResponse exchangefactor(@PathVariable Long limit) throws CommonException {
		
		log.info("Today :"+now());
		try {
		List retMapList = new ArrayList();	
		
		LongStream.rangeClosed(0, limit-1).forEach(i->{
			
				if(i==0) {
					HttpHeaders headers = new HttpHeaders();
		            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		            ResponseEntity<Map> response = restTemplate.exchange(
					        "https://api.ratesapi.io/api/latest",
					        HttpMethod.GET,
					        entity,
					        Map.class
					);
		            Map retMap1 = new TreeMap();
		            retMap1.put("Date", now());
		            retMap1.putAll(covertRatesMap(response.getBody()));
		            retMapList.add(retMap1);
				}else {
					
				    LocalDate dt = now().minusMonths(i);
					HttpHeaders headers = new HttpHeaders();
		            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		            ResponseEntity<Map> response = restTemplate.exchange(
					        "https://api.ratesapi.io/api/"+dt,
					        HttpMethod.GET,
					        entity,
					        Map.class
					);
		            Map retMap1 = new TreeMap();
		            retMap1.put("Date", dt);
		            retMap1.putAll(covertRatesMap(response.getBody()));
		            retMapList.add(retMap1);
				}
		
		log.info("retMapList :"+retMapList);
		});
		Collections.reverse(retMapList);
        return new ServiceResponse("success","201","Data Fetched",1,retMapList);
		}catch(Exception e) {
			throw new CommonException("Error "+e.getMessage());
		}
			
	}
	
	@CrossOrigin
	@GetMapping(value="/exchangefactor/latest")
	@ResponseStatus(HttpStatus.OK)
	public ServiceResponse exchangefactorDaily() {
		log.info("start exchangefactorDaily ");
			
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<Map> response = restTemplate.exchange(
			        "https://api.ratesapi.io/api/latest",
			        HttpMethod.GET,
			        entity,
			        Map.class
			);
            Map retMap1 = new TreeMap();
            retMap1.put("Date", now());
            retMap1.putAll(covertRatesMap(response.getBody()));
            
          
			return new ServiceResponse("success","201","Data Fetched",1,retMap1);
		}catch(Exception e) {
			throw new CommonException("Error "+e.getMessage());
		}
	}

	static List<String> currarr = Arrays.asList("GBP","USD","HKD");
	private Map covertRatesMap(Map fullMap) {
		Map retMap = new HashMap();
		fullMap.forEach((K, V) ->{
			
			if("rates".equalsIgnoreCase((String) K)) {
				Map map = (Map) V;
				log.info("map==>"+map);
				currarr.forEach(i->{
					log.info("i=="+i);
					retMap.put(i,map.get(i));
				});
				
			}
			
		});
		
		return retMap;
		
	}
}
