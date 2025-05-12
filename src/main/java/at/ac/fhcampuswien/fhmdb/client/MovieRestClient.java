package at.ac.fhcampuswien.fhmdb.client;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovieRestClient {

    private final OkHttpClient client;
    private final Gson gson;
    private String baseUrl = "https://prog2.fh-campuswien.ac.at";

    public MovieRestClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public MovieRestClient(String url) {
        this();
        this.baseUrl = url;
    }

    private <T> T executeRequest(Request request, Type type) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), type);
            } else {
                throw new MovieRestClientException("Request failed: " + response);
            }
        } catch (IOException e) {
            throw new MovieRestClientException("Error", e);
        }
    }

    public List<Movie> getAllMovies() {
        Request request = new Request.Builder()
                .url(baseUrl + "/movies")
                .addHeader("User-Agent", "Mozilla")
                .build();

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();

        return executeRequest(request, listType);
    }

    public Movie getById(String id) {
        Request request = new Request.Builder()
                .url(baseUrl + "/movies/" + id)
                .addHeader("User-Agent", "Mozilla")
                .build();

        return executeRequest(request, Movie.class);
    }

    public List<Movie> getByQuery(String query, Genre genre, int releaseYear, double ratingFrom) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/movies").newBuilder();

        if (query != null && !query.trim().isEmpty()) {
            urlBuilder.addQueryParameter("query", query);
        }
        if (genre != null) {
            urlBuilder.addQueryParameter("genre", genre.name());
        }
        if (releaseYear != 0) {
            urlBuilder.addQueryParameter("releaseYear", String.valueOf(releaseYear));
        }
        if (ratingFrom > 0) {
            urlBuilder.addQueryParameter("ratingFrom", String.valueOf(ratingFrom));
        }

        HttpUrl url = urlBuilder.build();

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla")
                .url(url)
                .build();

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();

        return executeRequest(request, listType);
    }
}
