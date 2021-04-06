package com.gestankbratwurst.playerblocktracker;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PlayerBlockTracker and was created at the 06.04.2021
 *
 * PlayerBlockTracker can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class TrackedWorld {

  private final Long2ObjectMap<TrackedChunk> chunkMap;

  protected TrackedWorld() {
    this.chunkMap = new Long2ObjectOpenHashMap<>();
  }

  protected boolean isTracked(final Block block) {
    return this.getChunkOf(block).isTracked(block);
  }

  protected void add(final Block block) {
    this.getChunkOf(block).add(block);
  }

  protected void remove(final Block block) {
    this.getChunkOf(block).remove(block);
  }

  protected void initChunk(final Chunk chunk) {
    final PersistentDataContainer container = chunk.getPersistentDataContainer();
    final TrackedChunk trackedChunk = new TrackedChunk(container);
    this.chunkMap.put(UtilChunk.getChunkKey(chunk), trackedChunk);
  }

  protected void terminateChunk(final Chunk chunk) {
    final TrackedChunk trackedChunk = this.chunkMap.remove(UtilChunk.getChunkKey(chunk));
    if (trackedChunk == null) {
      return;
    }
    final PersistentDataContainer container = chunk.getPersistentDataContainer();
    if (trackedChunk.isEmpty()) {
      container.remove(PlayerBlockTracker.TRACKED_DATA_KEY);
    } else {
      trackedChunk.saveTo(container);
    }
  }

  private TrackedChunk getChunkOf(final Block block) {
    return this.chunkMap.get(UtilChunk.getChunkKeyOfBlock(block));
  }

}