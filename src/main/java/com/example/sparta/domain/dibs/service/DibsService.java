package com.example.sparta.domain.dibs.service;

import com.example.sparta.domain.dibs.dto.DibsDto;
import com.example.sparta.domain.dibs.entity.Dibs;
import com.example.sparta.domain.dibs.repository.DibsRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.domain.user.entity.User;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibsService {
  private final StoreRepository storeRepository;
  private final DibsRepository dibsRepository;

  public DibsDto createDibs(Long id, User user) {
    Store store = storeRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 가계는 찾을수 없어요"));
    try {
      Dibs dibs= new Dibs(user,store);
      dibsRepository.save(dibs);
      return new DibsDto(dibs);
    }catch (Exception e){
      throw new IllegalArgumentException("dibs 등록 실패");
    }
  }

  public long deleteDibs(Long Storeid, User user) {
    Store store = storeRepository.findById(Storeid).orElseThrow(()->new NoSuchElementException("해당 가계는 찾을수 없어요"));

    try {
      Dibs dibs= dibsRepository.findDibsByStoreAndUser(store,user).orElseThrow(()->new NoSuchElementException("해당 찜을 찾을수 없어요"));;
      dibsRepository.delete(dibs);
      return Storeid;
    }catch (Exception e){
      throw new IllegalArgumentException("dibs 제거 실패");
    }
  }
}
