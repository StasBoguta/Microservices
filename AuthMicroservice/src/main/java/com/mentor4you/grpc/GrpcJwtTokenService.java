package com.mentor4you.grpc;

import com.mentor4you.service.JwtTokenService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcJwtTokenService extends GrpcJwtTokenServiceGrpc.GrpcJwtTokenServiceImplBase {

  private final JwtTokenService jwtTokenService;

  @Override
  public void checkToken(
      JwtTokenCheckRequest request, StreamObserver<JwtTokenCheckResponse> responseObserver) {
    final JwtTokenCheckResponse jwtTokenCheckResponse =
        JwtTokenCheckResponse.newBuilder()
            .setIsValid(jwtTokenService.isValidToken(request.getToken()))
            .build();
    responseObserver.onNext(jwtTokenCheckResponse);
    responseObserver.onCompleted();
  }
}
