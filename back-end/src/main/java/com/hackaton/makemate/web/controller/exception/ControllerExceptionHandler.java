package com.hackaton.makemate.web.controller.exception;

import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.exception.ForbiddenException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
  // I shit code and i don't care
  private static final String DEFAULT_KEY = "message";

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(DEFAULT_KEY, e.getMessage()));
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Map<String, String>> handleForbiddenException(ForbiddenException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(DEFAULT_KEY, e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
        .body(Map.of(DEFAULT_KEY, e.getMessage()));
  }
}
