package se.gritacademy.server.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

public class Auth {
    private static final String secretKey = "Um1WMmRESkRaeko1TWtWbWExQjRSM2xOZEUxcVl6QkhSbkJ6WWtsQlVUQT0=";

    public static String generateJWT(Long userId) throws JOSEException, ParseException {
        JWSSigner signer = new MACSigner(secretKey);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("issuer")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 60))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public static Long verifyJWT(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier jwsVerifier = new MACVerifier(secretKey);

        if (signedJWT.verify(jwsVerifier) && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime())) {
            return Long.parseLong(signedJWT.getJWTClaimsSet().getSubject());
        } else {
            throw new JOSEException("Invalid token");
        }
    }
}
