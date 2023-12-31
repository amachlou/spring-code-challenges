package com.amachlou.springchallenges;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CateringJobRepository extends CrudRepository<CateringJob, Long> {
    List<CateringJob> findAll();
    List<CateringJob> findByStatus(Status status);
}
