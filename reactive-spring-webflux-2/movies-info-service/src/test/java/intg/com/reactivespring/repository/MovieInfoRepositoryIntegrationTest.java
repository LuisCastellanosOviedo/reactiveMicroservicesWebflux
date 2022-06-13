package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    private List<MovieInfo> movieinfos;

    @BeforeEach
    void setUp() {
        movieinfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
        movieInfoRepository.saveAll(movieinfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
       var moviesInfoFlux =  movieInfoRepository.findAll().log();
       var fluxData = Flux.fromIterable(movieinfos);

        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();


    }

    @Test
    void findById() {
       var moviesInfoMono =  movieInfoRepository.findById("abc").log();

        StepVerifier.create(moviesInfoMono)
                .assertNext( movieinfo -> {
                    assertEquals("Dark Knight Rises",movieinfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {

        var batman = new MovieInfo(null, "Batman Begins",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
        var moviesInfoMono =  movieInfoRepository.save(batman).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext( movieinfo1 -> {
                    assertNotNull(movieinfo1.getMovieInfoId());
                    assertEquals("Batman Begins",movieinfo1.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {

        var batman =  movieInfoRepository.findById("abc").block();
        batman.setYear(2022);

        var moviesInfoMono =  movieInfoRepository.save(batman).log();

        StepVerifier.create(moviesInfoMono)
                .assertNext( movieinfo1 -> {
                    assertNotNull(movieinfo1.getMovieInfoId());
                    assertEquals(2022,movieinfo1.getYear());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {
        movieInfoRepository.deleteById("abc").block();
        var moviesInfoFlux = movieInfoRepository.findAll().log();


                StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();


    }
}