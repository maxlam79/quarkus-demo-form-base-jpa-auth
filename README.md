# Quarkus Demo - Form Based JPA Auth

This is an application which demonstrates authentication via Quarkus Security with JPA.

It handles the login, secured (credential sensitive access) REST End-Points and logout, with credential that are
embedded in cookies.

## Database Setup

Since we are running on Dev Services, here are the defaults for the database (these values are default by Quarkus):

- username: quarkus
- password: quarkus
- database: quarkus

_There is no need to alter the application.properties file._

## Cookie Encryption Key
The value in `quarkus.http.auth.session.encryption-key` is to be generated with:
```bash
openssl rand -base64 32
```

_Quarkus' requirement is at least 16 characters, so 32 characters is more than enough._