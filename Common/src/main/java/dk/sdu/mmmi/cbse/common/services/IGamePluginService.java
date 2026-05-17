package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * A plugin is responsible for creating and removing its own entities
 * when the game starts and stops.
 * Implementations are discovered at runtime via the ServiceLoader.
 */

public interface IGamePluginService {

  /**
   * <p>Pre-condition:</p>
   * <ul>
   *  <li>{@code gameData} is non-null and contains valid display dimensions</li>
   *  <li>{@code world} is non-null</li>
   *  <li>No netities belonging to this plugin currently exist in the world</li>
   * </ul>
   *
   * <p>Post-condition</p>
   * <ul>
   *  <li>All entities belonging to this plugin have been added to the world</li>
   *  <li>Each added entity has valid position, rotation, and polygon coordinates</li>
   * </ul>
   *
   * @param gameData the current game state including display dimensions and input, must not be null
   * @param world the shared entity container, must not be null
   */
  
    void start(GameData gameData, World world);

    /**
     * Called once when the plugin is unloaded from the game.
     * The implementation should remove all entities it previously
     * added to the world during {@link #start(GameData, World)}.
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *   <li>{@code gameData} is non-null</li>
     *   <li>{@code world} is non-null</li>
     *   <li>{@link #start(GameData, World)} has previously been called</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *   <li>All entities belonging to this plugin have been removed from the world</li>
     *   <li>No references to removed entities are retained by the plugin</li>
     * </ul>
     *
     * @param gameData the current game state, must not be null
     * @param world    the shared entity container, must not be null
     */
    void stop(GameData gameData, World world);
}
