package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.MovieDetails;

public class MovieDataProvider {
    private static final Map<String, MovieDetails> MOVIE_DETAILS = new HashMap<String, MovieDetails>() {{
        put("The Dark Knight", new MovieDetails(
                "dark_knight",
                "The Dark Knight",
                R.drawable.dark_knight_banner,
                Arrays.asList("Action", "Crime", "Drama"),
                152,
                "July 18, 2008",
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                "Christopher Nolan",
                Arrays.asList("Christian Bale", "Heath Ledger", "Aaron Eckhart"),
                "PG-13",
                "English"
        ));

        put("Inception", new MovieDetails(
                "inception",
                "Inception",
                R.drawable.inception_banner,
                Arrays.asList("Action", "Adventure", "Sci-Fi"),
                148,
                "July 16, 2010",
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                "Christopher Nolan",
                Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Ellen Page"),
                "PG-13",
                "English"
        ));

        put("Interstellar", new MovieDetails(
                "interstellar",
                "Interstellar",
                R.drawable.interstellar_banner,
                Arrays.asList("Adventure", "Drama", "Sci-Fi"),
                169,
                "November 7, 2014",
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "Christopher Nolan",
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"),
                "PG-13",
                "English"
        ));

        put("The Matrix", new MovieDetails(
                "matrix",
                "The Matrix",
                R.drawable.matrix_banner,
                Arrays.asList("Action", "Sci-Fi"),
                136,
                "March 31, 1999",
                "A computer programmer discovers that reality as he knows it is a simulation created by machines, and joins a rebellion to break free.",
                "Lana and Lilly Wachowski",
                Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                "R",
                "English"
        ));

        put("Avengers: Endgame", new MovieDetails(
                "avengers_endgame",
                "Avengers: Endgame",
                R.drawable.avengers_endgame_banner,
                Arrays.asList("Action", "Adventure", "Drama"),
                181,
                "April 26, 2019",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                "Anthony and Joe Russo",
                Arrays.asList("Robert Downey Jr.", "Chris Evans", "Mark Ruffalo"),
                "PG-13",
                "English"
        ));

        put("Spider-Man: No Way Home", new MovieDetails(
                "spiderman_no_way_home",
                "Spider-Man: No Way Home",
                R.drawable.spiderman_banner,
                Arrays.asList("Action", "Adventure", "Fantasy"),
                148,
                "December 17, 2021",
                "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man.",
                "Jon Watts",
                Arrays.asList("Tom Holland", "Zendaya", "Benedict Cumberbatch"),
                "PG-13",
                "English"
        ));

        put("Black Panther", new MovieDetails(
                "black_panther",
                "Black Panther",
                R.drawable.black_panther_banner,
                Arrays.asList("Action", "Adventure", "Sci-Fi"),
                134,
                "February 16, 2018",
                "T'Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and must confront a challenger from his country's past.",
                "Ryan Coogler",
                Arrays.asList("Chadwick Boseman", "Michael B. Jordan", "Lupita Nyong'o"),
                "PG-13",
                "English"
        ));

        put("Joker", new MovieDetails(
                "joker",
                "Joker",
                R.drawable.joker_banner,
                Arrays.asList("Crime", "Drama", "Thriller"),
                122,
                "October 4, 2019",
                "A failed stand-up comedian is driven insane and becomes a psychopathic murderer, leading to a life of crime and chaos in Gotham City.",
                "Todd Phillips",
                Arrays.asList("Joaquin Phoenix", "Robert De Niro", "Zazie Beetz"),
                "R",
                "English"
        ));

        put("Parasite", new MovieDetails(
                "parasite",
                "Parasite",
                R.drawable.parasite_banner,
                Arrays.asList("Comedy", "Drama", "Thriller"),
                132,
                "October 11, 2019",
                "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                "Bong Joon-ho",
                Arrays.asList("Song Kang-ho", "Lee Sun-kyun", "Cho Yeo-jeong"),
                "R",
                "Korean"
        ));

        put("Dune", new MovieDetails(
                "dune",
                "Dune",
                R.drawable.dune_banner,
                Arrays.asList("Action", "Adventure", "Sci-Fi"),
                155,
                "October 22, 2021",
                "A noble family becomes embroiled in a war for control over the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
                "Denis Villeneuve",
                Arrays.asList("Timothée Chalamet", "Rebecca Ferguson", "Oscar Isaac"),
                "PG-13",
                "English"
        ));
    }};

    public static MovieDetails getMovieDetails(String movieTitle) {
        return MOVIE_DETAILS.get(movieTitle);
    }

    public static List<MovieDetails> getAllMovieDetails() {
        return new ArrayList<>(MOVIE_DETAILS.values());
    }
}
