package com.#{packageName}.web.controller;

import com.#{packageName}.api.api.ExampleApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController implements ExampleApi {

  @Override
  public String path(String path) {
    return "例子：" + path;
  }
}
