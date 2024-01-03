package online.book.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@SQLDelete(sql = "UPDATE order_items SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id")
    private Order order;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void setOrder(Order order) {
        order.getOrderItems().add(this);
        this.order = order;
    }

    public BigDecimal getPrice() {
        this.price = book.getPrice().multiply(new BigDecimal(quantity));
        return this.price;
    }
}
