package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRestClientTest {

    private MockWebServer mockWebServer;
    private MovieRestClient movieRestClient;

    @BeforeEach
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString();
        movieRestClient = new MovieRestClient(baseUrl);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetAllMovies() {
        String jsonResponse = "[{\"id\":\"1\",\"title\":\"Movie1\",\"genre\":\"ACTION\",\"releaseYear\":\"2020\",\"rating\":8.0}]";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse));

        List<Movie> movies = movieRestClient.getAllMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("Movie1", movies.get(0).getTitle());
    }

    @Test
    public void testGetById() {
        String jsonResponse = "{\"id\":\"1\",\"title\":\"Movie1\",\"genre\":\"ACTION\",\"releaseYear\":\"2020\",\"rating\":8.0}";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse));

        Movie movie = movieRestClient.getById("1");
        assertNotNull(movie);
        assertEquals("Movie1", movie.getTitle());
    }

    @Test
    public void testGetByQuery() {
        String jsonResponse = "[{\"id\":\"2\",\"title\":\"Movie2\",\"genre\":\"DRAMA\",\"releaseYear\":\"2019\",\"rating\":7.5}]";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse));

        List<Movie> movies = movieRestClient.getByQuery("Movie", Genre.DRAMA, 2019, 7.0);
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("Movie2", movies.get(0).getTitle());
    }
}
