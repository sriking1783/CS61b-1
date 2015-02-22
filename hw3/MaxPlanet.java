import java.util.Comparator;
<<<<<<< HEAD
import java.util.*;
=======
>>>>>>> 70607f43be77060299bad5badbba7882c11871c0
/* Import anything else you need here. */

/**
 * MaxPlanet.java
 */

public class MaxPlanet {

    /** Returns the Planet with the maximum value according to Comparator c. */
    public static Planet maxPlanet(Planet[] planets, Comparator<Planet> c) {
        Arrays.sort(planets, c);
        return planets[planets.length-1];
    }
}