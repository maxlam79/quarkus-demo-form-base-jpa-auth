package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import io.quarkus.vertx.VertxContextSupport;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;

@ApplicationScoped
public class AppInitializer {

    @Startup
    public Uni< Void > dataInitialization( ) throws Throwable {

        Log.info( "Initializing data..." );

        return VertxContextSupport.subscribeAndAwait( ( ) -> {
            return Panache.withTransaction( ( ) -> {
                Log.info( "Creating roles..." );
                RoleEntity roleAdminEntity = new RoleEntity( );
                roleAdminEntity.roleName = "admin";
                roleAdminEntity.roleDesc = "Administrator";

                RoleEntity roleUserEntity = new RoleEntity( );
                roleUserEntity.roleName = "user";
                roleUserEntity.roleDesc = "System User";

                return Uni.join( ).< RoleEntity >all(
                                roleAdminEntity.persist( ),
                                roleUserEntity.persist( ) )
                        .andFailFast( )
                        .flatMap( r -> {
                            Log.info( "Creating users..." );

                            UserEntity adminUserEntity = new UserEntity( );
                            adminUserEntity.username = "admin";
                            adminUserEntity.password = BcryptUtil.bcryptHash( "qaz123" );
                            adminUserEntity.roleList = Collections.singletonList( r.get( 0 ) );

                            UserEntity userEntity = new UserEntity( );
                            userEntity.username = "bob";
                            userEntity.password = BcryptUtil.bcryptHash( "wsx234" );
                            userEntity.roleList = Collections.singletonList( r.get( 1 ) );

                            return Uni.join( ).< UserEntity >all(
                                            adminUserEntity.persist( ),
                                            userEntity.persist( ) )
                                    .andFailFast( )
                                    .map( r2 -> null );
                        } );
            } );
        } );
    }

}
