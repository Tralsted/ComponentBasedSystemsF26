module Asteroid {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroid;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
            with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;

    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService
            with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
}
