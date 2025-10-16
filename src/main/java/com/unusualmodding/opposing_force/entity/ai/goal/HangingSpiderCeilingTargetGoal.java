package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.data.OPEntityTagProvider;
import com.unusualmodding.opposing_force.entity.HangingSpider;
import com.unusualmodding.opposing_force.registry.tags.OPEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HangingSpiderCeilingTargetGoal extends Goal {

    private final HangingSpider hangingSpider;

    public HangingSpiderCeilingTargetGoal(HangingSpider hangingSpider) {
        this.hangingSpider = hangingSpider;
    }

    @Override
    public boolean canUse() {
        if (!hangingSpider.isUpsideDown() || hangingSpider.getTarget() != null) return false;
        BlockPos ceilingPos = hangingSpider.blockPosition();
        int distance = 0;
        BlockPos.MutableBlockPos checkPos = ceilingPos.mutable();
        while (distance < 40 && hangingSpider.level().getBlockState(checkPos).isAir()) {
            checkPos.move(0, -1, 0);
            distance++;
        }
        if (distance >= 40) return false;
        double floorY = ceilingPos.getY() - distance + 1;
        AABB box = new AABB(hangingSpider.getX() - 4.0D, floorY, hangingSpider.getZ() - 4.0D, hangingSpider.getX() + 4.0D, hangingSpider.getY(), hangingSpider.getZ() + 4.0D);
        List<LivingEntity> potentialTargets = hangingSpider.level().getEntitiesOfClass(LivingEntity.class, box, entity -> (entity instanceof Player || entity.getType().is(OPEntityTypeTags.HANGING_SPIDER_TARGETS)) && hangingSpider.canAttack(entity) && entity.isAlive() && !entity.isSpectator());
        if (!potentialTargets.isEmpty()) {
            hangingSpider.setTarget(potentialTargets.get(0));
            if (potentialTargets.get(0) instanceof Mob mob && (mob.getTarget() == null || !mob.getTarget().is(hangingSpider))) mob.setTarget(hangingSpider);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return hangingSpider.getTarget() != null && hangingSpider.getTarget().isAlive() && (hangingSpider.isUpsideDown() || hangingSpider.isGoingDown());
    }

    @Override
    public void stop() {
        hangingSpider.setTarget(null);
    }
}