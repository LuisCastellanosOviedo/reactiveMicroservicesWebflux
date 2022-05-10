package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    private FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

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
                .expectNext("J","A","M","E","S","J","U","L","I","E","T","H")
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

    @Test
    void namesTransformTest() {
        var namesTransform = fluxAndMonoGeneratorService.namesFlux_transform(4);
        StepVerifier.create(namesTransform)
                .expectNext("J","A","M","E","S","J","U","L","I","E","T","H")
                .verifyComplete();
    }

    @Test
    void namesTransformTest_defaultIsEmpty() {
        var namesTransform = fluxAndMonoGeneratorService.namesFlux_transform(10);
        StepVerifier.create(namesTransform)
                //.expectNext("J","A","M","E","S","J","U","L","I","E","T","H")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesTransformTest_switchIfEmpty() {
        var namesTransform = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(10);
        StepVerifier.create(namesTransform)
                //.expectNext("J","A","M","E","S","J","U","L","I","E","T","H")
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void testConcat() {
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void testConcat_with() {
        var concatFlux = fluxAndMonoGeneratorService.explore_concat_with();
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void testConcatWith_mono() {
        var fluxMono = fluxAndMonoGeneratorService.expore_concatWithMono();

        StepVerifier.create(fluxMono)
                .expectNext("A","B")
                .verifyComplete();

    }

    @Test
    void test_merge() {
        var fluxValues = fluxAndMonoGeneratorService.explore_merge();

        StepVerifier.create(fluxValues)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void test_merge_sequential() {
        var fluxValues = fluxAndMonoGeneratorService.explore_merge_sequential();

        StepVerifier.create(fluxValues)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void test_zip() {
        var fluxValues = fluxAndMonoGeneratorService.explore_zip();

        StepVerifier.create(fluxValues)
                .expectNext("AD","BE","CF")
                .verifyComplete();

    }

    @Test
    void test_zip_tuple4() {
        var fluxValues = fluxAndMonoGeneratorService.explore_zip_tuple4();

        StepVerifier.create(fluxValues)
                .expectNext("AD14","BE25","CF36")
                .verifyComplete();

    }

    @Test
    void test_zip_with() {
        var fluxValues = fluxAndMonoGeneratorService.explore_zip_with();

        StepVerifier.create(fluxValues)
                .expectNext("AD","BE","CF")
                .verifyComplete();

    }

    @Test
    void zip_mono() {
        var monoValues = fluxAndMonoGeneratorService.explore_zip_with_mono();

        StepVerifier.create(monoValues)
                .expectNext("AB")
                .verifyComplete();
    }
}