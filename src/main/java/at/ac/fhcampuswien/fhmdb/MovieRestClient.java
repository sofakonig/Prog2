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

// Klasse MovieRestClient stellt die Kommunikation mit einer API für Filme sicher.
public class MovieRestClient {

    // Basis-URL der API (kann später geändert werden).
    private String baseUrl = "https://prog2.fh-campuswien.ac.at";

    // OkHttpClient wird für HTTP-Anfragen verwendet.
    private final OkHttpClient client;

    // Gson wird zum Konvertieren von JSON in Java-Objekte und umgekehrt verwendet.
    private final Gson gson;

    // Standardkonstruktor, der den HTTP-Client und Gson initialisiert.
    public MovieRestClient() {
        this.client = new OkHttpClient(); // Erzeugt einen neuen OkHttpClient für HTTP-Anfragen.
        this.gson = new Gson();           // Erzeugt eine neue Gson-Instanz für die JSON-Verarbeitung.
    }

    // Konstruktor, der eine benutzerdefinierte Basis-URL akzeptiert.
    public MovieRestClient(String url) {
        this(); // Ruft den Standardkonstruktor auf, um client und gson zu initialisieren.
        this.baseUrl = url; // Setzt die benutzerdefinierte Basis-URL.
    }

    // Diese Methode führt eine HTTP-Anfrage aus und gibt das Ergebnis des angegebenen Typs zurück.
    private <T> T executeRequest(Request request, Type type) {
        try (Response response = client.newCall(request).execute()) { // Führt die Anfrage aus und schließt die Antwort am Ende.
            if (response.isSuccessful() && response.body() != null) { // Überprüft, ob die Antwort erfolgreich ist und der Body nicht null ist.
                return gson.fromJson(response.body().string(), type); // Konvertiert den JSON-Body in ein Java-Objekt des angegebenen Typs.
            } else {
                // Wenn die Anfrage fehlschlägt, wird eine IOException geworfen.
                throw new IOException("Request failed: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // Falls ein Fehler auftritt, wird eine RuntimeException geworfen.
        }
    }

    // Diese Methode ruft eine Liste aller Filme von der API ab.
    public List<Movie> getAllMovies() {
        // Erstellt eine HTTP-Anfrage, um alle Filme abzurufen.
        Request request = new Request.Builder()
                .url(baseUrl + "/movies") // Setzt die URL für den Abruf aller Filme.
                .addHeader("User-Agent", "Mozilla") // Setzt den User-Agent-Header, um die Anfrage wie von einem Browser aussehen zu lassen.
                .build();

        // Definiert den Typ der Rückgabe als eine Liste von Movie-Objekten.
        Type listType = new TypeToken<List<Movie>>() {}.getType();

        // Führt die Anfrage aus und gibt die Liste von Filmen zurück.
        return executeRequest(request, listType);
    }

    // Diese Methode ruft einen Film basierend auf seiner ID ab.
    public Movie getById(String id) {
        // Erstellt eine HTTP-Anfrage, um einen Film mit einer bestimmten ID abzurufen.
        Request request = new Request.Builder()
                .url(baseUrl + "/movies/" + id) // Setzt die URL für den Abruf eines Films anhand seiner ID.
                .addHeader("User-Agent", "Mozilla") // Setzt den User-Agent-Header.
                .build();

        // Führt die Anfrage aus und gibt das Movie-Objekt zurück.
        return executeRequest(request, Movie.class);
    }

    // Diese Methode ruft Filme basierend auf einer Suchabfrage und verschiedenen Filterkriterien ab.
    public List<Movie> getByQuery(String query, Genre genre, int releaseYear, double ratingFrom) {
        // Baut die URL für die API-Anfrage zusammen und fügt die möglichen Filter hinzu.
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/movies").newBuilder();

        // Fügt das Suchquery als Parameter hinzu, wenn es nicht null oder leer ist.
        if (query != null && !query.trim().isEmpty()) {
            urlBuilder.addQueryParameter("query", query);
        }

        // Fügt das Genre als Parameter hinzu, wenn es nicht null ist.
        if (genre != null) {
            urlBuilder.addQueryParameter("genre", genre.name());
        }

        // Fügt das Veröffentlichungsjahr als Parameter hinzu, wenn es ungleich null ist.
        if (releaseYear != 0) {
            urlBuilder.addQueryParameter("releaseYear", String.valueOf(releaseYear));
        }

        // Fügt die Mindestbewertung als Parameter hinzu, wenn sie größer als 0 ist.
        if (ratingFrom > 0) {
            urlBuilder.addQueryParameter("ratingFrom", String.valueOf(ratingFrom));
        }

        // Baut die vollständige URL aus den gegebenen Parametern.
        HttpUrl url = urlBuilder.build();

        // Erstellt eine HTTP-Anfrage mit der zusammengebauten URL.
        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla") // Setzt den User-Agent-Header.
                .url(url) // Setzt die URL für die Anfrage.
                .build();

        // Definiert den Typ der Rückgabe als eine Liste von Movie-Objekten.
        Type listType = new TypeToken<List<Movie>>() {}.getType();

        // Führt die Anfrage aus und gibt die Liste von Filmen basierend auf der Suchabfrage zurück.
        return executeRequest(request, listType);
    }
}
