# PlayerBlockTracker
This Spigot plugin provides tracking of blocks placed by any player that is not in creative mode in a performant way.

![BlockTrackingIllustration](https://i.imgur.com/Hf3uCvU.png)


## API methods:
```java
  boolean >  PlayerBlockTracker.isTrackedBlock(org.bukkit.Block)
  void    >  PlayerBlockTracker.track(org.bukkit.Block)
  void    >  PlayerBlockTracker.unTrack(org.bukkit.Block)
  void    >  PlayerBlockTracker.moveTrack(org.bukkit.Block, org.bukkit.Block)
```


## Example implementation
```java
  @EventHandler
  public void onBreak(final BlockBreakEvent event) {
    final Block brokenBlock = event.getBlock();
    if (PlayerBlockTracker.isTracked(brokenBlock)) {
      event.setCancelled(true);
    }
  }
```

## Maven dependency

Repository
```xml
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
```

Dependency
```xml
  <dependency>
    <groupId>com.github.Flo0</groupId>
    <artifactId>PlayerBlockTracker</artifactId>
    <version>Tag</version>
  </dependency>
```
