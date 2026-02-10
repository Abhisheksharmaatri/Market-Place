package org.abhishek.model;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
