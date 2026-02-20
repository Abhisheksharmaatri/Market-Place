package org.abhishek.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String id;
    private String productId;

    private Integer price;

    private Integer amount;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
