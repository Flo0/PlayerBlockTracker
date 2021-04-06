package com.gestankbratwurst.playerblocktracker;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerBlockTracker extends JavaPlugin {

  public static final NamespacedKey TRACKED_DATA_KEY = NamespacedKey.minecraft("tracked_chunk_data");

  private static PlayerBlockTracker instance;
  private final TrackingManager trackingManager;

  public PlayerBlockTracker() {
    instance = this;
    this.trackingManager = new TrackingManager();
  }

  @Override
  public void onEnable() {
    this.trackingManager.initCurrentlyLoadedWorlds();
    Bukkit.getPluginManager().registerEvents(new TrackListener(this.trackingManager), this);
  }

  @Override
  public void onDisable() {
    this.trackingManager.terminateCurrentlyLoadedWorlds();
  }

  /**
   * Returns true if the block is marked as tracked.
   *
   * @param block
   * @return true if it is tracked false otherwise.
   */
  public static boolean isTracked(final Block block) {
    return instance.trackingManager.isTracked(block);
  }

  /**
   * Manually tag a block as tracked. Use this if you want to track the creation of blocks from your own plugin.
   *
   * @param block
   */
  public static void track(final Block block) {
    instance.trackingManager.track(block);
  }

  /**
   * Manually un-tag a block as tracked. Use this if you want to track the removal of blocks from your own plugin.
   *
   * @param block
   */
  public static void unTrack(final Block block) {
    instance.trackingManager.unTrack(block);
  }

  /**
   * Manually move a tracked position to another one. Should not be used in bulk operations.
   *
   * @param from
   * @param to
   */
  public static void moveTrack(final Block from, final Block to) {
    instance.trackingManager.move(from, to);
  }

}
