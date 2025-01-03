package org.example.spring_security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Data
public class AppRole implements GrantedAuthority { // GrantedAuthority: mỗi đối tượng AppRole đại diện cho một quyền (authority) của người dùng.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
