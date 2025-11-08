package org.acme;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.FormAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.HashMap;

@Path( "/secured" )
@ApplicationScoped
public class SecureResource {

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @Authenticated
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Uni< Response > processPOSTReq( final POSTPayload payload ) {

        return Uni.createFrom( ).item( payload )
                .map( p -> {
                    return Response
                            .ok( new HashMap<>( ) {{
                                put( "user", securityIdentity.getPrincipal( ).getName( ) );
                                put( "msg", "Processed POST Req with data: " + p.data );
                                put( "timestamp", LocalDateTime.now( ) );
                            }} )
                            .build( );
                } );
    }

    @PUT
    @Authenticated
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Uni< Response > processPUTReq( final PUTPayload payload ) {

        return Uni.createFrom( ).item( payload )
                .map( p -> {
                    return Response
                            .ok( new HashMap<>( ) {{
                                put( "user", securityIdentity.getPrincipal( ).getName( ) );
                                put( "msg", "Processed PUT Req with data: " + p.data );
                                put( "timestamp", LocalDateTime.now( ) );
                            }} )
                            .build( );
                } );
    }

    @GET
    @Authenticated
    @Produces( MediaType.APPLICATION_JSON )
    public Uni< Response > processGETReq( ) {

        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> {
                    return Response
                            .ok( new HashMap<>( ) {{
                                put( "user", si.getPrincipal( ).getName( ) );
                                put( "msg", "Processed GET Req" );
                                put( "timestamp", LocalDateTime.now( ) );
                            }} )
                            .build( );
                } );
    }

    @DELETE
    @Authenticated
    @Produces( MediaType.APPLICATION_JSON )
    public Uni< Response > processDELETEReq( ) {

        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> {
                    return Response
                            .ok( new HashMap<>( ) {{
                                put( "user", si.getPrincipal( ).getName( ) );
                                put( "msg", "Processed DELETE Req" );
                                put( "timestamp", LocalDateTime.now( ) );
                            }} )
                            .build( );
                } );
    }

    @POST
    @Path( "/logout" )
    @Authenticated
    public Uni< Response > logout( ) {
        return Uni.createFrom( ).item( securityIdentity )
                .map( si -> {

                    if ( !si.isAnonymous( ) ) {
                        FormAuthenticationMechanism.logout( si );
                    }

                    return Response.noContent( )
                            .build( );
                } );
    }

    static class POSTPayload {
        public String data;
    }

    static class PUTPayload {
        public String data;
    }
}
