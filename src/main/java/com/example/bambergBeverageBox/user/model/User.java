package com.example.bambergBeverageBox.user.model;

import com.example.bambergBeverageBox.role.model.Role;
import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@SQLDelete(sql = "Update user set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class User  extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name",nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @LastModifiedDate
    @Column(name = "birth_day")
    private LocalDate birthDay;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Column(name = "roles")
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();
}
