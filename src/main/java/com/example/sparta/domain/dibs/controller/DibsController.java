package com.example.sparta.domain.dibs.controller;

import com.example.sparta.domain.dibs.service.DibsService;
import com.example.sparta.global.dto.ExceptionDto;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dibs")
public class DibsController {
  private final DibsService dibsServices;

  @PostMapping("/store/{storeId}")
  public ResponseEntity<?> createDibs(@PathVariable(name = "storeId")Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(ResponseDto
              .builder()
              .statusCode(201)
              .data(dibsServices.createDibs(id,userDetails.getUser()))
              .build());
    }
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(ExceptionDto
              .builder()
              .statusCode(500)
              .message("찜 들록 하는데 실패")
              .build());
    }
  }
//
@DeleteMapping("/store/{storeId}")
public ResponseEntity<?> deleteDibs(@PathVariable(name = "storeId")Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
  try {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseDto
            .builder()
            .statusCode(200)
            .data(dibsServices.deleteDibs(id,userDetails.getUser()))
            .build());
  }
  catch (Exception e){
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ExceptionDto
            .builder()
            .statusCode(500)
            .message("찜 들록 하는데 실패")
            .build());
  }
}

}
