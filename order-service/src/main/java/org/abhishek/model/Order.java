package org.abhishek.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = "Order")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    private String orderItemList;
    private Integer total;
    private String userId;


    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deliveryDate;

    @Builder.Default
    private Boolean delivered=false;
}
