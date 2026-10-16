package org.acme;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.FormAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path( "/secured" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class SecureResource {

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @Authenticated
    public Uni< Map< String, Object > > processPOSTReq( POSTPayload payload ) {
        return Uni.createFrom( ).item( payload )
                .map( p -> new HashMap<>( ) {{
                    put( "user", securityIdentity.getPrincipal( ).getName( ) );
                    put( "msg", "Processed POST Req with data: " + p.data );
                    put( "timestamp", LocalDateTime.now( ) );
                }} );
    }

    @PUT
    @Authenticated
    public Uni< Map< String, Object > > processPUTReq( PUTPayload payload ) {
        return Uni.createFrom( ).item( payload )
                .map( p -> new HashMap<>( ) {{
                    put( "user", securityIdentity.getPrincipal( ).getName( ) );
                    put( "msg", "Processed PUT Req with data: " + p.data );
                    put( "timestamp", LocalDateTime.now( ) );
                }} );
    }

    @GET
    @Authenticated
    public Uni< Map< String, Object > > processGETReq( ) {
        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> new HashMap<>( ) {{
                    put( "user", si.getPrincipal( ).getName( ) );
                    put( "msg", "Processed GET Req" );
                    put( "timestamp", LocalDateTime.now( ) );
                }} );
    }

    @DELETE
    @Authenticated
    public Uni< Map< String, Object > > processDELETEReq( ) {

        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> new HashMap<>( ) {{
                    put( "user", si.getPrincipal( ).getName( ) );
                    put( "msg", "Processed DELETE Req" );
                    put( "timestamp", LocalDateTime.now( ) );
                }} );
    }

    @POST
    @Path( "/logout" )
    @Authenticated
    public Uni< Void > logout( ) {
        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> {

                    if ( !si.isAnonymous( ) ) {
                        FormAuthenticationMechanism.logout( si );
                    }

                    return ( Void ) null;
                } );
    }

    record POSTPayload( String data ) {
    }

    record PUTPayload( String data ) {
    }
}
