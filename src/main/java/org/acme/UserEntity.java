package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

import java.util.List;

@Entity
@UserDefinition
@Table( name = "t_user" )
public class UserEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    public Long id;

    @Column( name = "username" )
    @Username
    public String username;

    @Column( name = "password" )
    @Password
    public String password;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
            name = "t_user_role_map",
            joinColumns = @JoinColumn( name = "user_id" ),
            inverseJoinColumns = @JoinColumn( name = "role_id" )
    )
    @Roles
    public List< RoleEntity > roleList;

}
