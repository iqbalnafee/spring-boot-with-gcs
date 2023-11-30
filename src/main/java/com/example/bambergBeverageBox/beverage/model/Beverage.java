package com.example.bambergBeverageBox.beverage.model;

import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "beverage")
@SQLDelete(sql = "Update beverage set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Beverage extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beverage_sequence_generator")
    @SequenceGenerator(name = "beverage_sequence_generator", sequenceName = "beverage_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "beverage_name_en")
    private String beverageNameEn;

    @NotNull
    @Column(name = "beverage_name_de")
    private String beverageNameDe;

}
