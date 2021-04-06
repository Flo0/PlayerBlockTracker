package com.gestankbratwurst.playerblocktracker;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PlayerBlockTracker and was created at the 06.04.2021
 *
 * PlayerBlockTracker can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class TrackedChunk {

  private final IntSet trackedBlockPositions;

  protected TrackedChunk(final PersistentDataContainer container) {
    final int[] data = container.get(PlayerBlockTracker.TRACKED_DATA_KEY, PersistentDataType.INTEGER_ARRAY);
    if (data == null) {
      this.trackedBlockPositions = new IntOpenHashSet();
    } else {
      this.trackedBlockPositions = new IntOpenHashSet(data);
    }
  }

  protected void add(final Block block) {
    this.trackedBlockPositions.add(UtilChunk.getRelativeChunkPosition(block));
  }

  protected void remove(final Block block) {
    this.trackedBlockPositions.remove(UtilChunk.getRelativeChunkPosition(block));
  }

  protected boolean isTracked(final Block block) {
    return this.trackedBlockPositions.contains(UtilChunk.getRelativeChunkPosition(block));
  }

  protected void saveTo(final PersistentDataContainer container) {
    final int[] data = this.trackedBlockPositions.toIntArray();
    container.set(PlayerBlockTracker.TRACKED_DATA_KEY, PersistentDataType.INTEGER_ARRAY, data);
  }

  protected boolean isEmpty() {
    return this.trackedBlockPositions.isEmpty();
  }

}
