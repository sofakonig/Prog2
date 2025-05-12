package at.ac.fhcampuswien.fhmdb.models;

import java.util.Arrays;
import java.util.List;

public enum Genre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DRAMA,
    DOCUMENTARY,
    FAMILY,
    FANTASY,
    HISTORY,
    HORROR,
    MUSICAL,
    MYSTERY,
    ROMANCE,
    SCIENCE_FICTION,
    SPORT,
    THRILLER,
    WAR,
    WESTERN;

    public static List<Genre> mapGenres(String genres) {
        return Arrays.stream(genres.split(",")).map(Genre::valueOf).toList();
    }
}
