package org.abhishek.repository;

import org.abhishek.model.OrderItemList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItemList, String> {
}
