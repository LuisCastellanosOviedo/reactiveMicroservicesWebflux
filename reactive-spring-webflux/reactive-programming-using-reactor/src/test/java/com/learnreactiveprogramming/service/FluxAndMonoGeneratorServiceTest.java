package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void testFlatMap() {
        var namesFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMap(4);
        StepVerifier.create(namesFlatMap)
                .expectNext("j","A","M","E","S","J","U","L","I","E","T","H")
                .verifyComplete();
    }

    @Test
    void testFlatMapWithDelay() {
        var namesFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMapAsync(4);
        StepVerifier.create(namesFlatMap)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void testFlatMapWithConcatMap() {
        var namesFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMapConcatmap(4);
        StepVerifier.create(namesFlatMap)
                .expectNext("J","A","M","E","S","J","U","L","I","E","T","H")
                .verifyComplete();
    }

    @Test
    void testFlatmapOnMono() {
        var names= fluxAndMonoGeneratorService.namesMono_flatMap(4);
        StepVerifier.create(names)
                .expectNext(List.of("J","A","M","E","S"))
                .verifyComplete();
    }

    @Test
    void testFlatmapMany() {
        var names= fluxAndMonoGeneratorService.namesMono_flatMapMany(4);
        StepVerifier.create(names)
                .expectNext("J","A","M","E","S")
                .verifyComplete();
    }
}