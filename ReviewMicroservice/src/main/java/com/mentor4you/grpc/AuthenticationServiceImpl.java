package com.mentor4you.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final GrpcProperties grpcProperties;
    private final GrpcJwtTokenServiceGrpc.GrpcJwtTokenServiceBlockingStub jwtTokenServiceBlockingStub;

    public AuthenticationServiceImpl(GrpcProperties grpcProperties) {
        this.grpcProperties = grpcProperties;
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcProperties.getServerUrl())
                .usePlaintext()
                .build();
        this.jwtTokenServiceBlockingStub = GrpcJwtTokenServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public boolean isValidToken(String token) {
        final JwtTokenCheckResponse jwtTokenCheckResponse =
                jwtTokenServiceBlockingStub.checkToken(JwtTokenCheckRequest.newBuilder().setToken(token).build());
        return jwtTokenCheckResponse.getIsValid();
    }
}
