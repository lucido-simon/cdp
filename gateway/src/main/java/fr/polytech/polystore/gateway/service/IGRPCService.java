package fr.polytech.polystore.gateway.service;

import com.google.common.util.concurrent.ListenableFuture;
import reactor.core.publisher.Mono;

public interface IGRPCService {
    default <T> Mono<T> createMonoFromFuture(ListenableFuture<T> future) {
        return Mono.create(monoSink -> {
            future.addListener(() -> {
                try {
                    T response = future.get();
                    monoSink.success(response);
                } catch (Exception e) {
                    monoSink.error(e);
                }
            }, Runnable::run);
        });
    }
}
