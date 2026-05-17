
package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.Random;

public class AsteroidFactory {
    public static Asteroid createAsteroid(GameData gameData, int size) {
        var random = new Random();
        var asteroid = new Asteroid();
        asteroid.setSize(size);

        // radius scales with size
        asteroid.setRadius(size * 5);

        // polygon size also scales
        float s = size * 5;
        asteroid.setPolygonCoordinates(
            -s, -s,
             s, -s,
             s,  s,
            -s,  s
        );

        asteroid.setColor("gray");

        // spawn on a random edge of the screen
        int edge = random.nextInt(4);
        switch (edge) {
            case 0 -> { // top
                asteroid.setX(random.nextDouble() * gameData.getDisplayWidth());
                asteroid.setY(0);
            }
            case 1 -> { // bottom
                asteroid.setX(random.nextDouble() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight());
            }
            case 2 -> { // left
                asteroid.setX(0);
                asteroid.setY(random.nextDouble() * gameData.getDisplayHeight());
            }
            default -> { // right
                asteroid.setX(gameData.getDisplayWidth());
                asteroid.setY(random.nextDouble() * gameData.getDisplayHeight());
            }
        }

        // random direction
        asteroid.setRotation(random.nextDouble() * 360);
        return asteroid;
    }
}
