package at.ac.fhcampuswien.fhmdb;

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

    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at";
    private final OkHttpClient client;
    private final Gson gson;

    public MovieRestClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Movie> getAllMovies() {
        Request request = new Request.Builder()
                .url(BASE_URL + "/movies")
                .addHeader("User-Agent", "Mozilla")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Type listType = new TypeToken<List<Movie>>() {
                }.getType();
                return gson.fromJson(response.body().string(), listType);
            } else {
                throw new IOException("Unexpected response: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Movie getById(String id) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/movies/" + id)
                .addHeader("User-Agent", "Mozilla")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), Movie.class);
            } else {
                throw new IOException("Movie not found: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> getByQuery(String query, Genre genre, String releaseYear, double ratingFrom) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/movies")
                .newBuilder()
                .addQueryParameter("query", query)
                .addQueryParameter("genre", genre.name())
                .addQueryParameter("releaseYear", releaseYear)
                .addQueryParameter("ratingFrom", String.valueOf(ratingFrom))
                .build();

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla")
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Type listType = new TypeToken<List<Movie>>() {
                }.getType();
                return gson.fromJson(response.body().string(), listType);
            } else {
                throw new IOException("Query failed: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
