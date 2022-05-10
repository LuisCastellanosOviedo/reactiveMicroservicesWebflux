package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth")).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("james").log();
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFlux_inmutability() {
        var namesFlux = Flux.fromIterable(List.of("James", "Luis", "Julieth")).log();
        namesFlux = namesFlux.map(x -> x.toUpperCase()); //INMUTABILITY
        return namesFlux;
    }

    public Flux<String> namesFluxMapAndFilter(int length) {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .map(x -> x.length() + "-" + x).log();

    }

    public Flux<String> namesFluxFlatMap(int length) {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .flatMap(s -> splitString(s))
                .log();

    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray).log();
    }

    public Flux<String> namesFluxFlatMapAsync(int length) {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .flatMap(s -> splitStringWithDelay(s))
                .log();

    }

    public Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(300);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFluxFlatMapConcatmap(int length) {
        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .concatMap(s -> splitStringWithDelayConcatMap(s))
                .log();

    }

    public Flux<String> splitStringWithDelayConcatMap(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(300));
    }


    public Mono<List<String>> namesMono_flatMap(int stringLength) {
        return Mono.just("james")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono).log();
    }

    public Flux<String> namesMono_flatMapMany(int stringLength) {
        return Mono.just("james")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString).log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Flux<String> namesFlux_transform(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name
                .map(String::toUpperCase)
                .filter(x -> x.length() > length);

        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .transform(filterMap)
                .flatMap(s -> splitString(s))
                .defaultIfEmpty("default")
                .log();

    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int length) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name
                .map(String::toUpperCase)
                .filter(x -> x.equals("DEFAULT"))
                .flatMap(s -> splitString(s));

        var defaultFLux = Flux.just("default")
                .transform(filterMap);


        return Flux.fromIterable(List.of("James", "Luis", "Julieth"))
                .transform(filterMap)
                .switchIfEmpty(defaultFLux)
                .log();
    }

    public Flux<String>  explore_concat (){
        var abcFLux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");

        return Flux.concat(abcFLux,defFlux).log();
    }

    public Flux<String>  explore_concat_with(){
        var abcFLux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");

        return abcFLux.concatWith(defFlux).log();
    }

    public Flux<String> expore_concatWithMono(){
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.concatWith(bMono).log();
    }

    public Flux<String>  explore_merge (){
        var abcFLux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(125));

        return Flux.merge(abcFLux,defFlux).log();
    }

    public Flux<String>  explore_merge_sequential (){
        var abcFLux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(abcFLux,defFlux).log();
    }

    public Flux<String>  explore_zip (){
        var abcFLux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");

        return Flux.zip(abcFLux,defFlux,(f, s)-> f+s).log();
    }

    public Flux<String>  explore_zip_tuple4 (){
        var abcFLux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        var _123FLux = Flux.just("1","2","3");
        var _456FLux = Flux.just("4","5","6");

        return Flux.zip(abcFLux,defFlux,_123FLux,_456FLux).map(
                t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()
        ).log();
    }

    public Flux<String>  explore_zip_with (){
        var abcFLux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");

        return abcFLux.zipWith(defFlux,(x,y)-> x+y).log();
    }

    public Mono<String> explore_zip_with_mono(){
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.zipWith(bMono).map(t2 -> t2.getT1()+t2.getT2()).log();
    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(n -> System.out.println("The Name from Flux is :" + n));
        fluxAndMonoGeneratorService.nameMono().subscribe(n -> System.out.println("The Name from Mono structure is :" + n));
    }
}
