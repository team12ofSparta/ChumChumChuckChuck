package com.example.sparta.domain.orderdetail.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrderDetailResponseDto {

    private Long storeId;
    private String storeName;
    private List<Long> menuIdList;
    private List<String> menuNameList;
    private List<Long> menuPriceList;
    private List<Integer> quantityList;
}
