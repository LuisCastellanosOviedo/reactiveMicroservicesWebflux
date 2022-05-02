package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void validateAllNamesPresentOnFlux() {

        var names = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(names)
                .expectNext("James", "Luis", "Julieth")
                .verifyComplete();

    }

    @Test
    void validateCountElementsFromFlux() {

        var nameFLux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(nameFLux)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    void validateFirstElementAndCountTail() {

        var fluxNames = fluxAndMonoGeneratorService.namesFlux();
        StepVerifier.create(fluxNames)
                .expectNext("James")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void validateUpperCaseUsingMap() {
        var namesUpper = fluxAndMonoGeneratorService.namesFlux_map();

        StepVerifier.create(namesUpper)
                .expectNext("JAMES","LUIS","JULIETH")
                .verifyComplete();

    }

    @Test
    void test_namesFlux_inmutability() {
        var upper = fluxAndMonoGeneratorService.namesFlux_inmutability();

        StepVerifier.create(upper)
                .expectNext("JAMES","LUIS","JULIETH")
                .verifyComplete();

    }

    @Test
    void test_mapAndFilterOverFLux() {
        var names = fluxAndMonoGeneratorService.namesFluxMapAndFilter(4);
        StepVerifier.create(names)
                .expectNext("5-JAMES","7-JULIETH")
                .verifyComplete();
    }

    @Test
    void testCount_mapAndFilterOverFLux() {
        var names = fluxAndMonoGeneratorService.namesFluxMapAndFilter(4);
        StepVerifier.create(names)
                .expectNextCount(2)
                .verifyComplete();
    }
}