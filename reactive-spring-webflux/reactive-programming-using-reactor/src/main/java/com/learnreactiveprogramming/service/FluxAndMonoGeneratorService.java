package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("James","Luis","Julieth")).log();
    }

    public Mono<String> nameMono(){
        return Mono.just("james").log();
    }

    public Flux<String> namesFlux_map(){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFlux_inmutability(){
        var namesFlux = Flux.fromIterable(List.of("James","Luis","Julieth")).log();
        namesFlux = namesFlux.map(x -> x.toUpperCase()); //INMUTABILITY
        return namesFlux;
    }

    public Flux<String> namesFluxMapAndFilter(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .map(x -> x.length()+"-"+x).log();

    }

    public Flux<String> namesFluxFlatMap(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .flatMap(s -> splitString(s))
                .log();

    }

    public Flux<String> splitString(String name ){
        var charArray = name.split("");
        return Flux.fromArray(charArray).log();
    }

    public Flux<String> namesFluxFlatMapAsync(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .flatMap(s -> splitStringWithDelay(s))
                .log();

    }

    public Flux<String> splitStringWithDelay(String name ){
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFluxFlatMapConcatmap(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .concatMap(s -> splitStringWithDelayConcatMap(s))
                .log();

    }

    public Flux<String> splitStringWithDelayConcatMap(String name ){
        var charArray = name.split("");
 ;
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(1000));
    }


    public Mono<List<String>> namesMono_flatMap(int stringLength){
        return Mono.just("james")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono).log();
    }

    public Flux<String> namesMono_flatMapMany(int stringLength){
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


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(n -> System.out.println("The Name from Flux is :"+n));
        fluxAndMonoGeneratorService.nameMono().subscribe(n -> System.out.println("The Name from Mono structure is :"+n));
    }
}
