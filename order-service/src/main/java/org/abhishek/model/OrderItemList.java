package org.abhishek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.abhishek.dto.OrderItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(value = "orderItemList")
public class OrderItemList {
    @Id
    private String id;
    private List<OrderItem> orderItemList;
}
