package org.formation.rest;



import org.formation.model.Position;
import org.formation.service.ElasticPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class PositionController {
    private final ElasticPositionService elasticPositionService;
    
    
    Logger logger = LoggerFactory.getLogger(PositionController.class);

    public PositionController(ElasticPositionService elasticPositionService) {
        this.elasticPositionService = elasticPositionService;
    }

    @PostMapping("/position")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> position(@RequestBody Position position) {
        position.setTimestamp(System.currentTimeMillis());

        logger.info("Receiving position " + position);
        elasticPositionService.index(position);
        
        return Mono.empty();
    }
}
