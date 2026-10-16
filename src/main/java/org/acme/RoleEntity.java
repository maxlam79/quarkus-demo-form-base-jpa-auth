package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.*;

@Entity
@Table( name = "roles" )
public class RoleEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    public Long id;

    @RolesValue
    @Column( name = "role_name" )
    public String roleName;

    @Column( name = "role_desc" )
    public String roleDesc;

}
