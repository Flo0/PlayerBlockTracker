package com.gestankbratwurst.playerblocktracker;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PlayerBlockTracker and was created at the 06.04.2021
 *
 * PlayerBlockTracker can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
@RequiredArgsConstructor
public class TrackListener implements Listener {

  private final TrackingManager trackingManager;

  @EventHandler
  public void onLoad(final WorldLoadEvent event) {
    this.trackingManager.initWorld(event.getWorld());
  }

  @EventHandler
  public void onUnload(final WorldUnloadEvent event) {
    this.trackingManager.terminateWorld(event.getWorld());
  }

  @EventHandler
  public void onLoad(final ChunkLoadEvent event) {
    this.trackingManager.initChunk(event.getChunk());
  }

  @EventHandler
  public void onUnload(final ChunkUnloadEvent event) {
    this.trackingManager.terminateChunk(event.getChunk());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onPlace(final BlockPlaceEvent event) {
    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }
    this.trackingManager.track(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onBreak(final BlockBreakEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onExplode(final BlockExplodeEvent event) {
    this.trackingManager.unTrackAll(event.blockList());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onExplode(final EntityExplodeEvent event) {
    this.trackingManager.unTrackAll(event.blockList());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onBurn(final BlockBurnEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onFade(final BlockFadeEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onForm(final BlockFormEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onFlow(final BlockFromToEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onGrow(final BlockGrowEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onMultiPlace(final BlockMultiPlaceEvent event) {
    this.trackingManager.trackAll(event.getReplacedBlockStates().stream().map(BlockState::getBlock).collect(Collectors.toList()));
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onPistonExtend(final BlockPistonExtendEvent event) {
    this.trackingManager.shift(event.getDirection(), event.getBlocks());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onPistonRetract(final BlockPistonRetractEvent event) {
    if (!event.isSticky()) {
      return;
    }
    this.trackingManager.shift(event.getDirection(), event.getBlocks());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onSpread(final BlockSpreadEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityForm(final EntityBlockFormEvent event) {
    this.trackingManager.unTrack(event.getBlock());
  }

}