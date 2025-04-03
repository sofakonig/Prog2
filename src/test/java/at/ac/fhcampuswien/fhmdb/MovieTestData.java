package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieTestData {

    public static List<Movie> getTestMovies() {
        Movie movie1 = new Movie(
                "1",
                "Inception",
                2010,
                "A mind-bending thriller about dream invasion.",
                "http://example.com/inception.jpg",
                90,
                8.8,
                Arrays.asList(Genre.ACTION, Genre.HORROR),
                Arrays.asList("Christopher Nolan"),
                Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                Arrays.asList("Christopher Nolan")
        );

        Movie movie2 = new Movie(
                "2",
                "The Matrix",
                1999,
                "A computer hacker learns the true nature of his reality.",
                "http://example.com/matrix.jpg",
                100,
                8.7,
                Arrays.asList(Genre.ACTION, Genre.ANIMATION),
                Arrays.asList("The Wachowskis"),
                Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                Arrays.asList("The Wachowskis")
        );

        Movie movie3 = new Movie(
                "3",
                "The Shawshank Redemption",
                1994,
                "Two imprisoned men bond over several years.",
                "http://example.com/shawshank.jpg",
                100,
                9.3,
                Arrays.asList(Genre.DRAMA),
                Arrays.asList("Frank Darabont"),
                Arrays.asList("Tim Robbins", "Morgan Freeman"),
                Arrays.asList("Stephen King", "Frank Darabont")
        );

        Movie movie4 = new Movie(
                "4",
                "Pulp Fiction",
                1994,
                "The lives of two mob hitmen, a boxer, and others intertwine.",
                "http://example.com/pulpfiction.jpg",
                120,
                8.9,
                Arrays.asList(Genre.DRAMA, Genre.CRIME),
                Arrays.asList("Quentin Tarantino"),
                Arrays.asList("John Travolta", "Samuel L. Jackson", "Uma Thurman"),
                Arrays.asList("Quentin Tarantino", "Roger Avary")
        );

        Movie movie5 = new Movie(
                "5",
                "The Godfather",
                1972,
                "The aging patriarch of an organized crime dynasty transfers control to his reluctant son.",
                "http://example.com/godfather.jpg",
                135,
                9.2,
                Arrays.asList(Genre.DRAMA, Genre.CRIME),
                Arrays.asList("Francis Ford Coppola"),
                Arrays.asList("Marlon Brando", "Al Pacino", "James Caan"),
                Arrays.asList("Mario Puzo", "Francis Ford Coppola")
        );

        Movie movie6 = new Movie(
                "6",
                "Interstellar",
                2014,
                "A team of explorers travel through a wormhole in space to ensure humanity's survival.",
                "http://example.com/interstellar.jpg",
                140,
                8.6,
                Arrays.asList(Genre.ACTION, Genre.DRAMA, Genre.DOCUMENTARY),
                Arrays.asList("Christopher Nolan"),
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"),
                Arrays.asList("Jonathan Nolan", "Christopher Nolan")
        );

        Movie movie7 = new Movie(
                "7",
                "Fight Club",
                1999,
                "An insomniac office worker and a soap salesman form an underground fight club.",
                "http://example.com/fightclub.jpg",
                80,
                8.8,
                Arrays.asList(Genre.DRAMA),
                Arrays.asList("David Fincher"),
                Arrays.asList("Brad Pitt", "Edward Norton"),
                Arrays.asList("Chuck Palahniuk", "Jim Uhls")
        );

        Movie movie8 = new Movie(
                "8",
                "Forrest Gump",
                1994,
                "The presidencies of Kennedy and Johnson, the Vietnam War, and more, seen through the eyes of an Alabama man.",
                "http://example.com/forrestgump.jpg",
                90,
                8.8,
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE),
                Arrays.asList("Robert Zemeckis"),
                Arrays.asList("Tom Hanks", "Robin Wright"),
                Arrays.asList("Winston Groom", "Eric Roth")
        );

        Movie movie9 = new Movie(
                "9",
                "The Dark Knight",
                2008,
                "Batman raises the stakes in his war on crime with a new criminal mastermind.",
                "http://example.com/darkknight.jpg",
                70,
                9.0,
                Arrays.asList(Genre.ACTION, Genre.DRAMA),
                Arrays.asList("Christopher Nolan"),
                Arrays.asList("Christian Bale", "Heath Ledger", "Aaron Eckhart"),
                Arrays.asList("Jonathan Nolan", "Christopher Nolan")
        );

        Movie movie10 = new Movie(
                "10",
                "The Lord of the Rings: The Return of the King",
                2003,
                "Gandalf and Aragorn lead the World of Men against Sauron's army to save Middle-earth.",
                "http://example.com/lotr_return.jpg",
                90,
                8.9,
                Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.FANTASY),
                Arrays.asList("Peter Jackson"),
                Arrays.asList("Elijah Wood", "Viggo Mortensen", "Ian McKellen"),
                Arrays.asList("J.R.R. Tolkien", "Fran Walsh", "Philippa Boyens")
        );

        Movie movie11 = new Movie(
                "11",
                "Gladiator",
                2000,
                "A former Roman General sets out to exact vengeance against the corrupt emperor.",
                "http://example.com/gladiator.jpg",
                200,
                8.5,
                Arrays.asList(Genre.ACTION, Genre.DRAMA),
                Arrays.asList("Ridley Scott"),
                Arrays.asList("Russell Crowe", "Joaquin Phoenix"),
                Arrays.asList("David Franzoni", "John Logan", "William Nicholson")
        );

        Movie movie12 = new Movie(
                "12",
                "The Prestige",
                2006,
                "Two stage magicians engage in competitive one-upmanship in an attempt to create the ultimate stage illusion.",
                "http://example.com/prestige.jpg",
                190,
                8.5,
                Arrays.asList(Genre.DRAMA, Genre.MYSTERY),
                Arrays.asList("Christopher Nolan"),
                Arrays.asList("Christian Bale", "Hugh Jackman", "Scarlett Johansson"),
                Arrays.asList("Jonathan Nolan", "Christopher Nolan")
        );

        return Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10, movie11, movie12);
    }


}
