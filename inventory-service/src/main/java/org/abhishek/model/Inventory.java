package org.abhishek.model;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "Inventory")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotNull
    private String productId;

    private Integer price;

    private Integer amount;
    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
