package com.example.msa_exam.order;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.Random;

public class CustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final String serviceId;
    private final ServiceInstanceListSupplier supplier;

    public CustomLoadBalancer(String serviceId, ServiceInstanceListSupplier supplier) {
        this.serviceId = serviceId;
        this.supplier = supplier;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return supplier.get()
                .next()
                .map(serviceInstances -> {

                    Random random = new Random();
                    int randomValue = random.nextInt(100); // 0부터 99까지의 랜덤 숫자 생성

                    ServiceInstance instance;

                    if (randomValue < 70) { // 70% 확률
                        instance = serviceInstances.get(0);
                    } else { // 30% 확률
                        instance = serviceInstances.get(1);
                    }

                    return new DefaultResponse(instance);
                });
    }
}
