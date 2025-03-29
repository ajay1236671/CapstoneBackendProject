package com.Product.ProductService.Client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final RestTemplateBuilder templateBuilder;

    public AuthInterceptor(RestTemplateBuilder restTemplateBuilder) {
        this.templateBuilder = restTemplateBuilder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7);
        RestTemplate restTemplate = templateBuilder.build();
        if (token == null || token.isEmpty()) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token");
            return false;
        }
        // Call Security Service to validate token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("TokenAuthorization", authHeader);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(Map.of("token", token), headers);
        String clientRequestUrl = System.getenv("AUTH_URL") + "/auth/validate" + checkForRoles(request.getRequestURI(), request.getMethod());
        ResponseEntity<Map> validationResponse = restTemplate.postForEntity(clientRequestUrl, requestEntity, Map.class);
        String errorMsg = "Invalid token";
        if (validationResponse.getStatusCode() == HttpStatus.OK && Boolean.TRUE.equals(validationResponse.getBody().get("valid"))) {
            request.setAttribute("userId", validationResponse.getBody().get("userId"));
            request.setAttribute("roles", validationResponse.getBody().get("roles"));
            return true;
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), errorMsg);
            return false;
        }
    }


    public String checkForRoles(String requestURI, String method) {
        if ((requestURI.startsWith("/products") && method.equals("POST")) ||
                (requestURI.startsWith("/products") && method.equals("PUT")) ||
                (requestURI.startsWith("/products") && method.equals("DELETE"))) {
            return "/admin";
        }

        return "";
    }
}

