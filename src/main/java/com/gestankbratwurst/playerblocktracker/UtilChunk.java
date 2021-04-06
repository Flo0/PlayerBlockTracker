package com.gestankbratwurst.playerblocktracker;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PlayerBlockTracker and was created at the 06.04.2021
 *
 * PlayerBlockTracker can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilChunk {

  public static long getChunkKey(final Chunk chunk) {
    return getChunkKey(chunk.getX(), chunk.getZ());
  }

  public static long getChunkKey(final int chunkX, final int chunkZ) {
    return (long) chunkX & 0xFFFFFFFFL | ((long) chunkZ & 0xFFFFFFFFL) << 32;
  }

  public static long getChunkKeyOfBlock(final Block block) {
    return getChunkKey(block.getX() >> 4, block.getZ() >> 4);
  }

  public static int getRelativeChunkPosition(final Block block) {
    final int relX = block.getX() % 16;
    final int relZ = block.getZ() % 16;
    final int relY = block.getY();
    return (relY & 0xFFFF) | ((relX & 0xFF) << 16) | ((relZ & 0xFF) << 24);
  }

}