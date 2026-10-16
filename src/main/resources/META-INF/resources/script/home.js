const loginForm = document.querySelector( '#loginForm' );

loginForm.addEventListener( 'submit', ( e ) => {
    e.preventDefault();

    const username = document.querySelector( '#username' ).value.trim();
    const password = document.querySelector( '#password' ).value.trim();
    const usernameError = document.querySelector( '#usernameError' );
    const passwordError = document.querySelector( '#passwordError' );

    let isValid = true;

    if ( !username ) {
        usernameError.style.display = 'block';
        isValid = false;
    } else {
        usernameError.style.display = 'none';
    }

    if ( !password ) {
        passwordError.style.display = 'block';
        isValid = false;
    } else {
        passwordError.style.display = 'none';
    }

    if ( isValid ) {
        const formData = new URLSearchParams();
        formData.append( 'j_username', username );
        formData.append( 'j_password', password );

        fetch( '/j_security_check', {
            method: 'POST', headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }, body: formData.toString()
        } ).then( resp => {
            console.log( resp );

            if ( !resp.ok ) {
                var msg = `Authentication failed with status: ${ resp.status }`;

                throw new Error( msg );
            }

            // Authentication OK.
            window.location.href = '/protected.html';
        } ).catch( err => {
            console.error( err );

            alert( err.message );
        } );

    }
} );
