package online.book.store.repository.order;

import java.util.Set;
import online.book.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findAllByUserId(Long userId);
}
