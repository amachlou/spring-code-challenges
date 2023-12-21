package com.amachlou.springchallenges;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("cateringJobs")
public class CateringJobController {
    private static final String IMAGE_API = "https://foodish-api.herokuapp.com";
    private final CateringJobRepository cateringJobRepository;
    WebClient client;

    public CateringJobController(CateringJobRepository cateringJobRepository, WebClient.Builder webClientBuilder) {
        this.cateringJobRepository = cateringJobRepository;
    }

    @GetMapping
    @ResponseBody
    public List<CateringJob> getCateringJobs() {
        return cateringJobRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CateringJob getCateringJobById(@PathVariable Long id) {
        if (cateringJobRepository.existsById(id)) {
            return cateringJobRepository.findById(id).get();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status")
    public List<CateringJob> getCateringJobsByStatus(@RequestParam Status status) {
        return Optional.of(cateringJobRepository.findByStatus(status))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseBody
    public CateringJob createCateringJob(@RequestBody CateringJob job) {
        return cateringJobRepository.save(job);
    }

    @PutMapping("/{id}")
    public CateringJob updateCateringJob(@RequestBody CateringJob cateringJob, @PathVariable Long id) {
        if (cateringJobRepository.existsById(id)) {
            cateringJob.setId(id);
            return cateringJobRepository.save(cateringJob);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public CateringJob patchCateringJob(@PathVariable Long id, @RequestBody JsonNode json) {
        Optional<CateringJob> optJob = cateringJobRepository.findById(id);
        if (optJob.isPresent()) {
            CateringJob job = optJob.get();
            JsonNode email = json.get("email");
            if(email != null) {
                job.setMenu(email.asText());
            }
            return cateringJobRepository.save(job);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public Mono<String> getSurpriseImage() {
        return null;
    }
}
