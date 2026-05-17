package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollisionDetectorTest {

    private CollisionDetector detector;
    private World world;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        detector = new CollisionDetector();
        world = new World();
        gameData = new GameData();
    }

    @Test
    void bulletHitsLargeAsteroid_shouldSplitIntoTwo() {
        // arrange
        var bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);

        var asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(15);
        asteroid.setSize(3);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        // act
        detector.process(gameData, world);

        // assert
        var asteroids = world.getEntities(Asteroid.class);
        assertEquals(2, asteroids.size(), "Large asteroid should split into two smaller ones");
        asteroids.forEach(a -> assertEquals(2, ((Asteroid) a).getSize(), "Split asteroids should be size 2"));
        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
    }

    @Test
    void bulletHitsSmallAsteroid_shouldDestroyAndAddScore() {
        // arrange
        var bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);

        var asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(5);
        asteroid.setSize(1);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        // act
        detector.process(gameData, world);

        // assert
        assertEquals(0, world.getEntities(Asteroid.class).size(), "Small asteroid should be destroyed");
        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
        assertEquals(1, gameData.getScore(), "Score should increase by 1");
    }

    @Test
    void asteroidHitsShip_shouldDestroyShip() {
        // arrange — use a plain Entity as a ship stand-in
        var ship = new Entity() {};
        ship.setX(100);
        ship.setY(100);
        ship.setRadius(8);

        var asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(15);
        asteroid.setSize(2);

        world.addEntity(ship);
        world.addEntity(asteroid);

        // act
        detector.process(gameData, world);

        // assert
        assertFalse(world.getEntities().contains(ship), "Ship should be destroyed on asteroid collision");
    }

    @Test
    void entitiesFarApart_shouldNotCollide() {
        // arrange
        var bullet = new Bullet();
        bullet.setX(0);
        bullet.setY(0);
        bullet.setRadius(1);

        var asteroid = new Asteroid();
        asteroid.setX(500);
        asteroid.setY(500);
        asteroid.setRadius(15);
        asteroid.setSize(2);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        // act
        detector.process(gameData, world);

        // assert
        assertTrue(world.getEntities().contains(bullet), "Bullet should survive");
        assertTrue(world.getEntities().contains(asteroid), "Asteroid should survive");
    }
}
