package com.example.bambergBeverageBox.order.model;


import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "orders")
@SQLDelete(sql = "Update orders set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Order extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence_generator")
    @SequenceGenerator(name = "order_sequence_generator", sequenceName = "order_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "total_price")
    private Double totalPrice;
}
