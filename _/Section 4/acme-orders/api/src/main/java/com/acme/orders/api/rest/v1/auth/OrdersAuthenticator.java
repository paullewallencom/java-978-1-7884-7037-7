package com.acme.orders.api.rest.v1.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class OrdersAuthenticator implements Authenticator<String, User> {

    private Algorithm algorithm;

    public OrdersAuthenticator(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {

//        if ("secret".equals(basicCredentials.getPassword())) {
//            return Optional.of(new User(basicCredentials.getUsername()));
//        }

        try {

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);

            User.Access access = jwt.getClaim("realm_access").as(User.Access.class);

            return Optional.of(new User(token, jwt.getClaim("email").asString(), access));
        } catch (JWTVerificationException e) {

            return Optional.empty();
        }
    }
}
