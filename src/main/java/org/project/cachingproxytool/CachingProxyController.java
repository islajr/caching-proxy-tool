package org.project.cachingproxytool;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RestController
public class CachingProxyController {

    private final CachingProxyConfig cachingProxyConfig;
    private final CachingService cachingService;
    private final WebClient webClient;

    public CachingProxyController(CachingService cachingService, WebClient.Builder builder, CachingProxyConfig cachingProxyConfig) {
        this.cachingService = cachingService;
        this.webClient = builder.build();
        this.cachingProxyConfig = cachingProxyConfig;
    }

    @RequestMapping("/**")
    public Mono<ResponseEntity<byte[]>> cachingProxy(HttpServletRequest request) {
        String key = cachingService.generateKey(request);
        ResponseEntity<byte[]> cached = cachingService.get(key);

        if (cached != null) {
            return Mono.just(cached);   // return cached response asynchronously
        }

        String uri = cachingProxyConfig.getOrigin() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");   // if there are no queries, append an empty string.

        return webClient.method(HttpMethod.valueOf(request.getMethod()))
                .uri(uri)
                .headers(headers -> {
                    for (String name : Collections.list(request.getHeaderNames())) {
                        headers.addAll(name, Collections.list(request.getHeaders(name)));
                    }
                })
                .retrieve()
                .toEntity(byte[].class)
                .doOnSuccess(response -> cachingService.put(key, response));

    }

}
