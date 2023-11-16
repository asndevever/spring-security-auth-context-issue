package com.example.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class DummyController {

  @QueryMapping
  public String dummyQuery(@Argument String dummyQueryInput) {
    return "DummyGqlResponse";
  }
}


