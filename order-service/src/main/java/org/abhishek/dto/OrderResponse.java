package org.abhishek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponse {
    private Long id;
    private List<OrderItem> orderItemList;
    private Integer total;
    private String userId;
}
