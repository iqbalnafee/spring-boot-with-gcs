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
@Table(name = "order_item")
@SQLDelete(sql = "Update order_item set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class OrderItem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence_generator")
    @SequenceGenerator(name = "order_item_sequence_generator", sequenceName = "order_item_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "added_item_id")
    private Long addedItemId;

    @NotNull
    @Column(name = "added_item_name")
    private String addedItemName;

    @NotNull
    @Column(name = "added_item_pic")
    private String addedItemPic;

    @NotNull
    @Column(name = "added_item_price")
    private Double addedItemPrice;

    @Column(name = "added_item_quantity")
    private Integer addedItemQuantity;

    @NotNull
    @Column(name = "total_price_per_added_item")
    private Double totalPricePerAddedItem;
}
