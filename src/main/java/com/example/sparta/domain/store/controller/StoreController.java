package com.example.sparta.domain.store.controller;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.dto.StoreResponseDto;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<?> CreateStore(@RequestBody StoreRequestDto storeRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                        .builder()
                        .statusCode(201)
                        .data(storeService.createStore(storeRequestDto,userDetails.getUser()))
                        .message("새로운 가게가 등록되었습니다.")
                        .build());
    }
    @GetMapping
    public ResponseEntity<?> GetAllStore(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .statusCode(200)
                        .data(storeService.getAllStore())
                        .message("모든 가게를 조회합니다.")
                        .build());
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<?> EditStoreDetails(@PathVariable(name = "storeId") Long id,@RequestBody StoreRequestDto storeRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseDto
                        .builder()
                        .statusCode(202)
                        .data(storeService.editStore(id,storeRequestDto,userDetails.getUser()))
                        .message("가게가 정보가 수정 되었습니다.")
                        .build());
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> DeleteStore(@PathVariable(name = "storeId") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ResponseDto
                        .builder()
                        .statusCode(202)
                        .data(storeService.deleteStore(id,userDetails.getUser()))
                        .message("가게사 삭제 되었습니다.")
                        .build());
    }
    /// 검색 조회 시작.
    @GetMapping("/{storeId}")
    public ResponseEntity<?> GetStoreById(@PathVariable(name = "storeId") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .statusCode(200)
                        .data(storeService.getStoreById(id))
                        .message("가게 id : ."+id+" 를 가져옵니다")
                        .build());
    }
    //extra
    @GetMapping("/search")
    public ResponseEntity<?> GetStoreByName(@RequestParam(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .statusCode(200)
                        .data(storeService.getAllStoreByName(name))
                        .message("검색 : "+name+" 을 조회")
                        .build());
    }

}
