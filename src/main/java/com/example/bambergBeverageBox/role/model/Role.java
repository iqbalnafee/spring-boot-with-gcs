package com.example.bambergBeverageBox.role.model;

import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.util.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
@Entity
@Table(name="roles")
@SQLDelete(sql = "Update roles set is_deleted = 1 where id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Role  extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_sequence_generator")
    @SequenceGenerator(name = "roles_sequence_generator", sequenceName = "roles_sequence", initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", nullable=false, unique=true)
    private String roleName;

    @ManyToMany(mappedBy="roles")
    @Column(name = "users")
    private List<User> users;
}
