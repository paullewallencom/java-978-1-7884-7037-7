package com.acme.orders.api.rest.v1.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class OpenIdAuthenticator implements Authenticator<String, User> {

    private Algorithm algorithm;

    public OpenIdAuthenticator(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Optional<User> authenticate(String s) throws AuthenticationException {

        try {

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(s);

            User.Access access = jwt.getClaim("realm_access").as(User.Access.class);

            return Optional.of(new User(s, jwt.getClaim("email").asString(), access));
        } catch (JWTVerificationException e) {

            return Optional.empty();
        }
    }
}
