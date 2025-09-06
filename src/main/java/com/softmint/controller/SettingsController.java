package com.softmint.controller;

import com.softmint.dto.Menu;
import com.softmint.entity.User;
import com.softmint.model.AuthUser;
import com.softmint.service.AuthUserService;
import com.softmint.service.JwtService;
import com.softmint.service.MenuService;
import com.softmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SettingsController {

    private final JwtService jwtService;
    private final AuthUserService authUserService;
    private final UserService userService;
    private final MenuService menuService;


    @GetMapping("i18n/{lang}")
    public ResponseEntity<String> getI18n(@PathVariable String lang) throws IOException {
        // Load the JSON file from the classpath (e.g., src/main/resources)
        ClassPathResource jsonFile = new ClassPathResource("i18n/" + lang);
        // Read the file contents
        String jsonContent = StreamUtils.copyToString(jsonFile.getInputStream(), StandardCharsets.UTF_8);
        // Return the JSON file content as the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonContent, headers, HttpStatus.OK);
    }

    @GetMapping("me")
    public AuthUser getMe(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String token = jwtService.extractBearerToken(authorizationHeader);
        User user = userService.getUserById(UUID.fromString(jwtService.extractUserId(token)));
        AuthUser authUser = authUserService.loadUserByUsername(user.getEmail());
        authUser.setRoleType(user.getRoleType());
        return authUser;
    }

    @GetMapping("me/menu")
    public ResponseEntity<?> getMenu(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String token = jwtService.extractBearerToken(authorizationHeader);
        String userId = jwtService.extractUserId(token);
        Set<String> permissions = userService.getUserPermissions(UUID.fromString(userId));
        Menu menu = menuService.createMenu(permissions);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @GetMapping("ping")
    public ResponseEntity<List<String>> ping() {
        List<String> messages = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(messages, headers, HttpStatus.OK);
    }

}
