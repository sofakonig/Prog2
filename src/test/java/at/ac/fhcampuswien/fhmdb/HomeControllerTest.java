package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertFalse(releaseYearsFromMovies.contains(2020));
        Assertions.assertFalse(releaseYearsFromMovies.contains(2022));
        Assertions.assertTrue(releaseYearsFromMovies.contains(2010));
        Assertions.assertEquals(2, releaseYearsFromMovies.size());
    }

    @Test
    public void testGetRatingsFromMovies() {
        Set<Double> ratingsFromMovies = homeController.getRatingsFromMovies(movies.subList(3, 6));
        Assertions.assertEquals(3, ratingsFromMovies.size());
        Assertions.assertTrue(ratingsFromMovies.contains(8.6));
        Assertions.assertTrue(ratingsFromMovies.contains(9.2));
        Assertions.assertFalse(ratingsFromMovies.contains(4.2));

    }

    @Test
    public void testCountMoviesFrom() {
        long count = homeController.countMoviesFrom(movies, "Christopher Nolan");
        assertEquals(4, count);
    }

    // test für getMoviesBetweenYears
    @Test
    public void testGetMoviesBetweenYears() {
        List<Movie> moviesBetweenYears = homeController.getMoviesBetweenYears(movies, 1990, 2000);
        assertEquals(6, moviesBetweenYears.size());
        Assertions.assertTrue(moviesBetweenYears.stream().allMatch(movie -> movie.getReleaseYear() >= 1990 && movie.getReleaseYear() <= 2000));
        Assertions.assertTrue(movies.size() > moviesBetweenYears.size());
        Assertions.assertNotEquals(movies.size(), moviesBetweenYears.size());
    }

    @Test
    public void testGetMostPopularActor() {
        String popularActor = homeController.getMostPopularActor(movies);
        assertEquals("Christian Bale", popularActor);
    }

    @Test
    public void testGetLongestMovieTitle() {
        int longestMovieTitle = homeController.getLongestMovieTitle(movies);
        Assertions.assertEquals(45, longestMovieTitle);
    }


}