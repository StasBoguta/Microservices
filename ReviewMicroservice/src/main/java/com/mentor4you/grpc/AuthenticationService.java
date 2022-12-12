package com.mentor4you.grpc;

public interface AuthenticationService {

    boolean isValidToken(String token);
}
