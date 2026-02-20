package org.abhishek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponse {
    private String id;
    private List<OrderItem> orderItemList;
    private Integer total;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime deliveryDate;
    private Boolean delivered;
}
