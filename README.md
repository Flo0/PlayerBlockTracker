# PlayerBlockTracker
This Spigot plugin provides tracking of blocks placed by any player that is not in creative mode in a performant way.

![BlockTrackingIllustration](https://i.imgur.com/Hf3uCvU.png)

API methods:
```java
  boolean >  PlayerBlockTracker#isTrackedBlock(org.bukkit.Block)
  void    >  PlayerBlockTracker#track(org.bukkit.Block)
  void    >  PlayerBlockTracker#unTrack(org.bukkit.Block)
  void    >  PlayerBlockTracker#moveTrack(org.bukkit.Block, org.bukkit.Block)
```
