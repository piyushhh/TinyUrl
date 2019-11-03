package com.TinyUrl1.TinyUrl1;

import java.nio.charset.StandardCharsets;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

@RestController
@RequestMapping("/rest/url")
public class TinyUrlResource {
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@GetMapping("/{id}")
	public String getUrl(@PathVariable String id){
		String url = redisTemplate.opsForValue().get(id);
		System.out.println("URL retrived is: "+ url);
		return url ;
	}
	@PostMapping
	public String createUrl(@RequestBody String url){
	//We have added dependency in POM, commons-validator - to validate url (UrlValidator  is present in that dependency)	
		UrlValidator urlValidator =  new UrlValidator(
				new String[] {"https", "https"}) ;
	//we have added Guava Library, for hashing URL, (Hashing)	
		if(urlValidator.isValid(url)){
       String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
       System.out.println("URL Id generated: " + id);
       redisTemplate.opsForValue().set(id, url);
       return id;
       }
		else {
			throw new RuntimeException("wrong URL");
		}	
		// hiiii test git
	}

}
