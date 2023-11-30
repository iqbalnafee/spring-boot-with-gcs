package com.example.bambergBeverageBox.bottle.model;

import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "bottle")
@SQLDelete(sql = "Update bottle set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Bottle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bottle_sequence_generator")
    @SequenceGenerator(name = "bottle_sequence_generator", sequenceName = "bottle_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "beverage_id")
    private Long beverageId;

    @NotNull
    @NotEmpty
    @Column(name = "bottle_name_en")
    private String bottleNameEn;

    @NotNull
    @NotEmpty
    @Column(name = "bottle_name_de")
    private String bottleNameDe;

    @Column(name = "bottle_pic")
    private String bottlePic;

    @Positive(message = "Volume must be greater than zero")
    @Column(name = "volume")
    private Double volume;

    @Column(name = "is_alcoholic")
    private boolean isAlcoholic = false;

    @Column(name = "volume_percent")
    private Double volumePercent;

    @Positive(message = "Price must be greater than zero")
    @Column(name = "price")
    private Double price;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "in_stock")
    private Integer inStock;


}
