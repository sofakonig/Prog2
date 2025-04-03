package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    private static HomeController homeController;
    private static final List<Movie> movies = MovieTestData.getTestMovies();

    @BeforeAll
    public static void init() {
        homeController = new HomeController();
    }


    @Test
    public void testGetReleaseYearsFromMovies() {
        Set<Integer> releaseYearsFromMovies = homeController.getReleaseYearsFromMovies(movies.subList(0, 2));
    }

    @Test
    public void testGetRatingsFromMovies() {
        Set<Double> ratingsFromMovies = homeController.getRatingsFromMovies(movies.subList(3, 6));

    }

    @Test
    public void testGetMostPopularActor() {
        String popularActor = homeController.getMostPopularActor(movies);
        assertEquals("Christian Bale", popularActor);
    }

    @Test
    public void testGetLongestMovieTitle() {

    }

    @Test
    public void testCountMoviesFrom() {
        long countMoviesFrom = homeController.countMoviesFrom(movies, "Inception");

    }

    @Test
    public void testGetMoviesBetweenYears() {
        List<Movie> result = homeController.getMoviesBetweenYears(movies, 1900, 2000);
        assertEquals(7, result.size());
    }

}