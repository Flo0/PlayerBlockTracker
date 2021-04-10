package com.gestankbratwurst.playerblocktracker;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PlayerBlockTracker and was created at the 06.04.2021
 *
 * PlayerBlockTracker can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class TrackingManager {

  private final Map<UUID, TrackedWorld> trackedWorldMap;

  protected TrackingManager() {
    this.trackedWorldMap = new Object2ObjectOpenHashMap<>();
  }

  protected void initWorld(final World world) {
    final TrackedWorld trackedWorld = new TrackedWorld();
    for (final Chunk loadedChunk : world.getLoadedChunks()) {
      trackedWorld.initChunk(loadedChunk);
    }
    this.trackedWorldMap.put(world.getUID(), trackedWorld);
  }

  protected void terminateWorld(final World world) {
    final TrackedWorld trackedWorld = this.trackedWorldMap.remove(world.getUID());
    if (trackedWorld == null) {
      return;
    }
    for (final Chunk loadedChunk : world.getLoadedChunks()) {
      trackedWorld.terminateChunk(loadedChunk);
    }
  }

  protected void initChunk(final Chunk chunk) {
    final TrackedWorld trackedWorld = this.getTrackedWorldOf(chunk);
    if (trackedWorld == null) {
      return;
    }
    trackedWorld.initChunk(chunk);
  }

  protected void terminateChunk(final Chunk chunk) {
    final TrackedWorld trackedWorld = this.getTrackedWorldOf(chunk);
    if (trackedWorld == null) {
      return;
    }
    trackedWorld.terminateChunk(chunk);
  }

  protected void initCurrentlyLoadedWorlds() {
    Bukkit.getWorlds().forEach(this::initWorld);
  }

  protected void terminateCurrentlyLoadedWorlds() {
    Bukkit.getWorlds().forEach(this::terminateWorld);
  }

  protected boolean isTracked(final Block block) {
    final TrackedWorld trackedWorld = this.getTrackedWorldOf(block);
    if (trackedWorld == null) {
      return false;
    }
    return trackedWorld.isTracked(block);
  }

  protected void track(final Block block) {
    final TrackedWorld trackedWorld = this.getTrackedWorldOf(block);
    if (trackedWorld == null) {
      return;
    }
    trackedWorld.add(block);
  }

  protected void unTrack(final Block block) {
    final TrackedWorld trackedWorld = this.getTrackedWorldOf(block);
    if (trackedWorld == null) {
      return;
    }
    trackedWorld.remove(block);
  }

  protected void trackAll(final Collection<Block> trackedBlocks) {
    trackedBlocks.forEach(this::track);
  }

  protected void unTrackAll(final Collection<Block> trackedBlocks) {
    trackedBlocks.forEach(this::unTrack);
  }

  protected void shift(final BlockFace direction, final List<Block> blocks) {
    this.unTrackAll(blocks);
    this.trackAll(blocks.stream().map(block -> block.getRelative(direction)).collect(Collectors.toList()));
  }

  protected void move(final Block from, final Block to) {
    this.unTrack(from);
    this.track(to);
  }

  private TrackedWorld getTrackedWorldOf(final Block block) {
    return this.trackedWorldMap.get(block.getWorld().getUID());
  }

  private TrackedWorld getTrackedWorldOf(final Chunk chunk) {
    return this.trackedWorldMap.get(chunk.getWorld().getUID());
  }

}