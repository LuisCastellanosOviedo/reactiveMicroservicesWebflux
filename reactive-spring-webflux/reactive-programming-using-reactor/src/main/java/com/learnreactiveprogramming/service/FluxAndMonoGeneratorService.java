package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

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
        var namesFlux = Flux.fromIterable(List.of("James","Luis","Julieth"));
        namesFlux = namesFlux.map(x -> x.toUpperCase()); //INMUTABILITY
        return namesFlux;
    }

    public Flux<String> namesFluxMapAndFilter(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .map(x -> x.length()+"-"+x);

    }

    public Flux<String> namesFluxFlatMap(int length){
        return Flux.fromIterable(List.of("James","Luis","Julieth"))
                .map(String::toUpperCase)
                .filter(x -> x.length() > length)
                .map(x -> x.length()+"-"+x);

    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(n -> System.out.println("The Name from Flux is :"+n));
        fluxAndMonoGeneratorService.nameMono().subscribe(n -> System.out.println("The Name from Mono structure is :"+n));
    }
}
