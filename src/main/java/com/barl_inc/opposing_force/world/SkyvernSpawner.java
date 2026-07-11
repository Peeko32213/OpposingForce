package com.barl_inc.opposing_force.world;

import com.barl_inc.opposing_force.registry.OPEntities;
import com.barl_inc.opposing_force.registry.tags.OPEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import static com.barl_inc.opposing_force.OpposingForceConfig.*;

public class SkyvernSpawner {

    private static final Predicate<? super ServerPlayer> ABOVE_CLOUD_HEIGHT = (serverPlayer -> serverPlayer.isAlive() && serverPlayer.getY() > SKYVERN_SPAWN_HEIGHT.get());
    private static final Predicate<? super ServerPlayer> BELOW_CLOUD_HEIGHT = (serverPlayer -> serverPlayer.isAlive() && serverPlayer.getY() <= SKYVERN_SPAWN_HEIGHT.get());
    private final Random random = new Random();
    private final ServerLevel serverLevel;
    private int timer;
    private final int BASE_TIMER = SKYVERN_SPAWN_TIMER.get();

    public SkyvernSpawner(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
        this.timer = BASE_TIMER;
        if (serverLevel.isRaining() || serverLevel.isThundering()) {
            this.timer /= 2;
        }
    }

    public void tick() {
        if (this.timer-- <= 0) {
            this.timer = BASE_TIMER + random.nextInt(BASE_TIMER / 2);
            if (serverLevel.isRaining() || serverLevel.isThundering()) {
                this.timer /= 2;
            }
            this.attemptSpawn();
        }
    }

    private void attemptSpawn() {
        Player player = this.getRandomPlayer();
        decreaseSkyvernSpawnChances();
        boolean postDragon = serverLevel.getServer().getWorldData().endDragonFightData().dragonKilled() || serverLevel.getServer().getWorldData().endDragonFightData().previouslyKilled();
        boolean canSpawn = postDragon || !POST_END.get() || !OPEntities.SKYVERN.get().is(OPEntityTypeTags.POST_END);
        if (player != null && this.serverLevel.dimensionType().hasSkyLight() && canSpawn) {
            if(handleSkyvernSpawnChance(player)) {
                BlockPos playerPos = BlockPos.containing(player.position());
                double minDist = 24;
                double maxDist = 48;
                BlockPos spawnPos = this.generateFarAwayPos(playerPos, (int) minDist, (int) maxDist);
                if (spawnPos != null && this.hasLightLevel(spawnPos) && spawnPos.distSqr(playerPos) > minDist * minDist) {
                    spawnSkyvern(spawnPos, player);
                }
            }
        }
    }

    private void spawnSkyvern(BlockPos spawnPos, Player player) {
        EntityType<? extends Mob> type = OPEntities.SKYVERN.get();
        Mob mob = type.create(this.serverLevel);
        mob.moveTo(Vec3.atCenterOf(spawnPos));
        mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos), MobSpawnType.NATURAL, null, null);
        OPWorldData worldData = OPWorldData.get(serverLevel);
        if(worldData != null) {
            worldData.resetSkyvernSpawnChance(player.getUUID());
        }
        if (mob.checkSpawnObstruction(serverLevel)) {
            mob.setYRot(random.nextFloat() * 360);
            serverLevel.addFreshEntityWithPassengers(mob);
        }
    }

    private boolean handleSkyvernSpawnChance(Player player) {
        OPWorldData worldData = OPWorldData.get(serverLevel);
        UUID uuid = player.getUUID();
        if(worldData != null) {
            double spawnChance = worldData.getSkyvernSpawnChance(uuid);
            if(random.nextDouble() < spawnChance) {
                return true;
            }
            if(worldData.getSkyvernSpawnChance(uuid) < 1) {
                worldData.incrementSkyvernSpawnChance(uuid);
            }
        }
        return false;
    }

    private void decreaseSkyvernSpawnChances() {
        OPWorldData worldData = OPWorldData.get(serverLevel);
        List<ServerPlayer> list = serverLevel.getPlayers(BELOW_CLOUD_HEIGHT);
        if(worldData != null) {
            for(ServerPlayer player : list) {
                UUID uuid = player.getUUID();
                if(worldData.getSkyvernSpawnChance(uuid) > SKYVERN_SPAWN_CHANCE.get()) {
                    worldData.decreaseSkyvernSpawnChance(uuid);
                }
            }
        }
    }

    private Player getRandomPlayer() {
        List<ServerPlayer> list = serverLevel.getPlayers(ABOVE_CLOUD_HEIGHT);
        return list.isEmpty() ? null : list.get(this.random.nextInt(list.size()));
    }

    @Nullable
    private BlockPos generateFarAwayPos(BlockPos center, int minDist, int maxDist) {
        BlockPos pos = null;
        for (int i = 0; i < 10; ++i) {
            int posX = center.getX() + random.nextInt(maxDist * 2) - maxDist;
            int posZ = center.getZ() + random.nextInt(maxDist * 2) - maxDist;
            int cloudHeight = SKYVERN_SPAWN_HEIGHT.get() + random.nextInt(10);
            int height = this.serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, posX, posZ);
            BlockPos blockPos = new BlockPos(posX, Math.max(cloudHeight, height + 1), posZ);
            double x = (double) blockPos.getX() - center.getX();
            double z = (double) blockPos.getZ() - center.getZ();
            if (serverLevel.isEmptyBlock(blockPos) && (x * x + z * z) >= minDist * minDist) {
                pos = blockPos;
                break;
            }
        }
        return pos;
    }

    private boolean hasLightLevel(BlockPos pos) {
        int blockLight = serverLevel.getBrightness(LightLayer.BLOCK, pos);
        return blockLight < 2;
    }
}