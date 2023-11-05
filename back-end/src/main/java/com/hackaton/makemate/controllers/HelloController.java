package com.hackaton.makemate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/suu")
  public String Suu() {
    return "Greetings from Spring Boot!";
  }
}
