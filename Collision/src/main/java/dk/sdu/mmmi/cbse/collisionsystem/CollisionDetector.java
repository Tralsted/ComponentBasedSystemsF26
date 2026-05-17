package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidFactory;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getID().equals(e2.getID())) continue;

                // skip if either entity was already removed
                if (!world.getEntities().contains(e1)) continue;
                if (!world.getEntities().contains(e2)) continue;

                if (!isColliding(e1, e2)) continue;
                // bullet hits asteroid
                if (e1 instanceof Bullet && e2 instanceof Asteroid) {
                    handleBulletAsteroid((Asteroid) e2, e1, world, gameData);
                }

                // asteroid hits ship
                if (e1 instanceof Asteroid && isShip(e2)) {
                    world.removeEntity(e2);
                }
            }
        }
    }

    private void handleBulletAsteroid(Asteroid asteroid, Entity bullet, World world, GameData gameData) {
        world.removeEntity(bullet);
        if (asteroid.getSize() > 1) {
            var a1 = AsteroidFactory.createAsteroid(gameData, asteroid.getSize() - 1);
            var a2 = AsteroidFactory.createAsteroid(gameData, asteroid.getSize() - 1);
            a1.setX(asteroid.getX());
            a1.setY(asteroid.getY());
            a2.setX(asteroid.getX());
            a2.setY(asteroid.getY());
            world.addEntity(a1);
            world.addEntity(a2);
        } else {
          gameData.addScore(1);
        }
        world.removeEntity(asteroid);
    }

    private boolean isColliding(Entity e1, Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (e1.getRadius() + e2.getRadius());
    }

    private boolean isShip(Entity e) {
        return !(e instanceof Bullet) && !(e instanceof Asteroid);
    }
}
