package com.acme.customers.api.rest.v1.filters;

import com.acme.customers.api.rest.v1.auth.AuthContext;
import com.acme.customers.api.rest.v1.auth.User;
import com.acme.customers.lib.v1.common.ApiError;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Provider
@ApplicationScoped
@Priority(value = Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private Algorithm algorithm;

    @Inject
    private AuthContext authContext;

    @PostConstruct
    private void init() {

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(System.getenv("AUTH_PUBLIC_KEY")));

            RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

            this.algorithm = Algorithm.RSA256(pubKey, null);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {

            ApiError error = new ApiError();
            error.setRef(UUID.randomUUID());
            error.setStatus(401);
            error.setCode("unauthorized");

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(error).build());
            return;
        }

        try {
            String bearer = authHeader.substring(7);

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(bearer);

            User.Access access = jwt.getClaim("realm_access").as(User.Access.class);

            User user = new User(bearer, jwt.getClaim("email").asString(), access);

            authContext.setUser(user);
        } catch (JWTVerificationException e) {

            ApiError error = new ApiError();
            error.setRef(UUID.randomUUID());
            error.setStatus(401);
            error.setCode("unauthorized");

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(error).build());
        }
    }
}
