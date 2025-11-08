function execSecRestEndPoint( restEndPointUrl, data, methodType, successCallback, errorCallback ) {
    // Validate methodType
    const validMethods = [ 'GET', 'POST', 'PUT', 'DELETE' ];

    if ( !validMethods.includes( methodType.toUpperCase() ) ) {
        if ( typeof errorCallback === 'function' ) {
            errorCallback( new Error( 'Invalid method type. Must be GET, POST, PUT, or DELETE.' ) );
        }

        return;
    }

    const config = {
        method: methodType.toUpperCase(),
        credentials: 'include'
    };

    // Only include body for POST and PUT requests
    if ( [ 'POST', 'PUT' ].includes( methodType.toUpperCase() ) && data ) {
        config.body = JSON.stringify( data );
        config.headers = {
            'Content-Type': 'application/json'
        };
    }

    return fetch( restEndPointUrl, config )
        .then( resp => {
            if ( !resp.ok ) {
                throw new Error( `HTTP error! Status: ${ resp.status }` );
            }

            // Parse JSON only if the response has a body (not for DELETE)
            return resp.text().then( text => text ? JSON.parse( text ) : {} );
        } )
        .then( result => {
            if ( typeof successCallback === 'function' ) {
                successCallback( result );
            }
        } )
        .catch( error => {
            if ( error.message.includes( '401' ) ) {
                alert( 'Security concerns. Routing to login page.' );
                window.location.href = '/index.html';
            } else {
                console.error( error );
                if ( typeof errorCallback === 'function' ) {
                    errorCallback( error );
                }
            }
        } );
}
