package org.abhishek.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCreatedEvent {
    String orderId;
    List<OrderCreatedEventItemList> orderItemList;

}
