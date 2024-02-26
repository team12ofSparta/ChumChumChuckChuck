package com.example.sparta.domain.store.controller;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.dto.StoreResponseDto;
import com.example.sparta.domain.store.service.StoreService;
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
    public ResponseEntity<StoreResponseDto> CreateStore(@RequestBody StoreRequestDto storeRequestDto){//@AuthenticationPrincipal UserDetailsImpl userDetails
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService.createStore(storeRequestDto));
    }
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> GetAllStore(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.getAllStore());
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<Long> EditStoreDetails(@PathVariable(name = "storeId") Long id,@RequestBody StoreRequestDto storeRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService.editStore(id,storeRequestDto));
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Long> DeleteStore(@PathVariable(name = "storeId") Long id){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService.deleteStore(id));
    }
    /// 검색 조회 시작.
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> GetStoreById(@PathVariable(name = "storeId") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.getAllStoreById(id));
    }


    //extra
    @GetMapping("/search")
    public ResponseEntity<List<StoreResponseDto>> GetStoreByName(@RequestParam(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK)
                .body(storeService.getAllStoreByName(name));
    }

}
