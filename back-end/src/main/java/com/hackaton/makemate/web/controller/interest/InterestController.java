package com.hackaton.makemate.web.controller.interest;

import com.hackaton.makemate.domain.interest.InterestService;
import com.hackaton.makemate.web.dto.interest.InterestDto;
import com.hackaton.makemate.web.dto.interest.InterestMapper;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/interests")
@RestController()
public class InterestController {
  private final InterestService interestService;
  private final InterestMapper interestMapper;

  public InterestController(InterestService interestService, InterestMapper interestMapper) {
    this.interestService = interestService;
    this.interestMapper = interestMapper;
  }

  @GetMapping()
  public List<InterestDto> getAllInterests() {
    return interestMapper.toDto(interestService.getAllInterests());
  }
}
