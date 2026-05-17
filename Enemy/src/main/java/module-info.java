module Enemy {
  requires Common;
  requires CommonBullet;

  provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
    with dk.sdu.mmmi.cbse.ships.EnemyPlugin;

  provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService
    with dk.sdu.mmmi.cbse.ships.EnemyControlSystem;

  uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
}
