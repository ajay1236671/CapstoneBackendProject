//package com.Product.ProductService.Config;
//
//
//import com.Product.ProductService.JwtRequestFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class SecurityFilterConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();  // ✅ Define RestTemplate bean
//    }
//
//    @Bean
//    public JwtRequestFilter jwtRequestFilter(RestTemplate restTemplate) {
//        return new JwtRequestFilter(restTemplate, "yourSecretKey");
//    }
//}
//
