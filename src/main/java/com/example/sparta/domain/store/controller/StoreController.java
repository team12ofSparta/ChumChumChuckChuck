package com.example.sparta.domain.store.controller;

import com.example.sparta.domain.store.dto.CreateStoreRequestDto;
import com.example.sparta.domain.store.dto.OpeningHoursDto;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.global.dto.ExceptionDto;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<?> createStore(@RequestBody CreateStoreRequestDto storeRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.createStore(storeRequestDto,userDetails.getUser()))
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("새로운 가게가 등록 오류 발생.")
                    .build());
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllStore(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                    .builder()
                    .statusCode(200)
                    .data(storeService.getAllStore())
                    //.message("모든 가게 를 조회합니다.")
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("모든 가게 를 조회 오류 발생.")
                    .build());
        }
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<?> editStoreDetails(@PathVariable(name = "storeId") Long id,@RequestBody StoreRequestDto storeRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.editStore(id,storeRequestDto,userDetails.getUser()))
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가게 의 정보가 수정 오류 발생.")
                    .build());
        }
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable(name = "storeId") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.deleteStore(id,userDetails.getUser()))
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가게 삭제 오류 발생.")
                    .build());
        }

    }
    @GetMapping("/{storeId}")

    public ResponseEntity<?> getStoreById(@PathVariable(name = "storeId") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                    .builder()
                    .statusCode(200)
                    .data(storeService.getStoreById(id))
                    //.message("가게 id : ."+id+" 를 가져옵니다")
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가게 id : ."+id+" 를 가져올수 없어요")
                    .build());
        }

    }
    //extra
    @GetMapping("/search")
    public ResponseEntity<?> getStoreByName(@RequestParam(name = "name") String name){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                    .builder()
                    .statusCode(200)
                    .data(storeService.getAllStoreByName(name))
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("검색 : "+name+" 을 조회 에러")
                    .build());
        }

    }
    // 가계 주인 기능
    @GetMapping("/{storeId}/open")
    public ResponseEntity<?> openStore(@PathVariable(name = "storeId") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.openStore(id,userDetails.getUser()))
                    //.message("가계 운영 시작")
                    .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가계 운영 시작 오류")
                    .build());
        }

    }
    @GetMapping("/{storeId}/close")
    public ResponseEntity<?> closeStore(@PathVariable(name = "storeId") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.closeStore(id,userDetails.getUser()))
                    //.message("가계 운영 시작")
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가계 운영 종료 오류")
                    .build());
        }
    }

    @PatchMapping("/openinghours/{storeId}")
    public ResponseEntity<?> updateStoreOpeningHours(@PathVariable(name = "storeId")Long id,@RequestBody OpeningHoursDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto
                    .builder()
                    .statusCode(201)
                    .data(storeService.updateOpeningHours(id,requestDto,userDetails.getUser()))
                    //.message("가계 의 영업 시간 이 등록 되었습니다.")
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가계 의 영업 시간 이 등록 에러.")
                    .build());
        }

    }

    //운영자 용 기능
    @GetMapping("/{storeId}/status/force/{code}")
    public ResponseEntity<?> forceChangeStoreStatus(@PathVariable(name = "storeId")Long id,@PathVariable(name = "code") int code,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto
                    .builder()
                    .statusCode(200)
                    .data(storeService.forceStatus(id,code,userDetails.getUser().getRole()))
                    //.message("가계 status 강제 변경")
                    .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDto
                    .builder()
                    .statusCode(500)
                    .message("가계 status 강제 변경 에러")
                    .build());
        }

    }
}
