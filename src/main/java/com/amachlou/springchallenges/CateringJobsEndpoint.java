package com.amachlou.springchallenges;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Endpoint(id = "catering-jobs")
public class CateringJobsEndpoint {
    private final CateringJobRepository cateringJobRepository;

    public CateringJobsEndpoint(CateringJobRepository cateringJobRepository) {
        this.cateringJobRepository = cateringJobRepository;
    }

    @ReadOperation
    Map<Status, Long> cateringJobs(){
        return cateringJobRepository.findAll().stream().collect(Collectors.groupingBy(CateringJob::getStatus, Collectors.counting()));
    }
}
