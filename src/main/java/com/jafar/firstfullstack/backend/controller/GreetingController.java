package com.jafar.firstfullstack.controller;

import com.jafar.firstfullstack.model.QuoteApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})
@RequestMapping("api")
@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.ninjas.quote}")
    private String apikey;

    @GetMapping("/greeting")
    public Map<String, String > getGreeting(@RequestParam(defaultValue = "Guest") String name){
        return Map.of("message","Hello " +name+ " .From server side...");
    }

    @GetMapping("/quotes")
    public String getRandomQuote() {
        String url = "https://api.api-ninjas.com/v1/quotes";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apikey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<QuoteApi[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, QuoteApi[].class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody()[0].getQuote();
        }

        return "No quote available.";
    }

}
