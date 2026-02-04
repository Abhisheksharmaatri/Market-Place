package org.abhishek.model;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "inventory_table")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productId;

    private Integer price;

    private Integer amount;
}
