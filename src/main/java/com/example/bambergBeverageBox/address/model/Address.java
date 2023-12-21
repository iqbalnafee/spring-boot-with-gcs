package com.example.bambergBeverageBox.address.model;

import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "address")
@SQLDelete(sql = "Update address set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Address extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence_generator")
    @SequenceGenerator(name = "address_sequence_generator", sequenceName = "address_sequence", initialValue = 8)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "street_number")
    private String streetNumber;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "state")
    private String state;

    @NotNull
    @Column(name = "postal_code")
    private String postalCode;

}
