package online.book.store.repository.order;

import java.util.List;
import online.book.store.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    public List<OrderItem> findAllByOrderId(Long orderId);
}
