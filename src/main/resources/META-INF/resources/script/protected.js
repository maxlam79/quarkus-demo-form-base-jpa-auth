function execGET() {
    execSecRestEndPoint(
        REST_SERVER_URL + '/secured',
        null,
        'GET',
        function ( result ) {
            document.querySelector( "#resultView" ).value = JSON.stringify( result );
        },
        function ( err ) {
            console.error( err );
            gotoLoginPage();
        }
    );
}

function execPOST() {
    execSecRestEndPoint(
        REST_SERVER_URL + '/secured',
        {
            data: 'Blah blah POST...'
        },
        'POST',
        function ( result ) {
            document.querySelector( "#resultView" ).value = JSON.stringify( result );
        },
        function ( err ) {
            console.error( err );
            gotoLoginPage();
        }
    );
}

function execPUT() {
    execSecRestEndPoint(
        REST_SERVER_URL + '/secured',
        {
            data: 'Blah blah PUT...'
        },
        'PUT',
        function ( result ) {
            document.querySelector( "#resultView" ).value = JSON.stringify( result );
        },
        function ( err ) {
            console.error( err );
            gotoLoginPage();
        }
    );
}

function execDELETE() {
    execSecRestEndPoint(
        REST_SERVER_URL + '/secured',
        null,
        'DELETE',
        function ( result ) {
            document.querySelector( "#resultView" ).value = JSON.stringify( result );
        },
        function ( err ) {
            console.error( err );
            gotoLoginPage();
        }
    );
}

function gotoLoginPage() {
    window.location.href = '/index.html';
}

function execLogout() {
    execSecRestEndPoint(
        REST_SERVER_URL + '/secured/logout',
        null,
        'POST',
        function ( result ) {
            window.location.href = '/';
        },
        function ( err ) {
            console.error( err );
        }
    );
}

document.querySelector( '#execGETBtn' ).addEventListener( 'click', execGET );
document.querySelector( '#execPOSTBtn' ).addEventListener( 'click', execPOST );
document.querySelector( '#execPUTBtn' ).addEventListener( 'click', execPUT );
document.querySelector( '#execDELETEBtn' ).addEventListener( 'click', execDELETE );
document.querySelector( '#logoutBtn' ).addEventListener( 'click', execLogout );
