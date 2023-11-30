package com.example.bambergBeverageBox.crate.model;

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
@Table(name = "crate")
@SQLDelete(sql = "Update crate set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Crate extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crate_sequence_generator")
    @SequenceGenerator(name = "crate_sequence_generator", sequenceName = "crate_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "beverage_id")
    private Long beverageId;

    @NotNull
    @NotEmpty
    @Column(name = "crate_name_en")
    private String crateNameEn;

    @NotNull
    @NotEmpty
    @Column(name = "crate_name_de")
    private String crateNameDe;

    @Column(name = "crate_pic")
    private String cratePic;

    @Column(name = "no_of_bottles")
    private Integer noOfBottles;

    @Positive(message = "Price must be greater than zero")
    @Column(name = "price")
    private Double price;

    @Column(name = "crates_in_stock")
    private Integer cratesInStock;

}
