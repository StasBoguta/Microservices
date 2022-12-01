package com.mentor4you.service;

import com.mentor4you.grpc.JwtTokenCheckRequest;
import com.mentor4you.grpc.JwtTokenCheckResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.http.ResponseEntity;

@GrpcService
@RequiredArgsConstructor
public class JwtTokenService extends com.mentor4you.grpc.JwtTokenServiceGrpc.JwtTokenServiceImplBase {

    private final AuthenticationService authenticationService;

    @Override
    public void checkToken(JwtTokenCheckRequest request, StreamObserver<JwtTokenCheckResponse> responseObserver) {
        authenticationService.checkExpiration(request.getToken());
        JwtTokenCheckResponse jwtTokenCheckResponse = null;
        try{
            authenticationService.checkExpiration(request.getToken());
            jwtTokenCheckResponse = JwtTokenCheckResponse.newBuilder().setIsValid(true).build();
        }catch (Exception e){
            jwtTokenCheckResponse = JwtTokenCheckResponse.newBuilder().setIsValid(false).build();
        }
        responseObserver.onNext(jwtTokenCheckResponse);
        responseObserver.onCompleted();
    }
}
