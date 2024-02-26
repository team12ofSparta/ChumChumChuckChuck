package com.example.sparta.domain.store.service;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.dto.StoreResponseDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public StoreResponseDto createStore(StoreRequestDto requestDto, User user) {
        Store store = new Store(requestDto,user);
        Store saveStore = storeRepository.save(store);
        return new StoreResponseDto(saveStore);
    }


    public List<StoreResponseDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponseDto> responseDtoList = new ArrayList<>();
        for (Store store : stores) {
            responseDtoList.add(new StoreResponseDto(store));
        }
        return responseDtoList;
    }

    public Long editStore(Long id, StoreRequestDto storeRequestDto) {
        Store store = storeRepository.findById(id).orElseThrow( ()->new NoSuchElementException("해당 가게를 찾을수 없어요."));
        store.update(storeRequestDto);
        return id;
    }

    public Long deleteStore(Long id){
        Store store = storeRepository.findById(id).orElseThrow( ()->new NoSuchElementException("해당 가게를 찾을수 없어요."));
        try {
            storeRepository.delete(store);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("삭제 하는데 오류 발생");
        }
        return id;
    }

    public List<StoreResponseDto> getAllStoreByName(String name) {
        List<Store> stores = storeRepository.findAllByNameContains(name);
        List<StoreResponseDto> responseDtoList = new ArrayList<>();
        for (Store store : stores) {
            responseDtoList.add(new StoreResponseDto(store));
        }
        return responseDtoList;
    }

    public StoreResponseDto getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 가게를 찾을수 없어요."));
        return new StoreResponseDto(store);
    }
}
