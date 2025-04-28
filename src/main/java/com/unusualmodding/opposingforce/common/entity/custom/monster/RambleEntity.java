package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.custom.base.AbstractMonster;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.SmartNearestTargetGoal;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.state.StateHelper;
import com.unusualmodding.opposingforce.common.entity.state.WeightedState;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.ToolActions;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;

public class RambleEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    private static final RawAnimation RAMBLE_IDLE = RawAnimation.begin().thenLoop("animation.ramble.idle");
    private static final RawAnimation RAMBLE_FLAIL = RawAnimation.begin().thenLoop("animation.ramble.idle_flail");
    private static final RawAnimation RAMBLE_WALK = RawAnimation.begin().thenLoop("animation.ramble.walk");
    private static final RawAnimation RAMBLE_AGGRO = RawAnimation.begin().thenLoop("animation.ramble.aggro");

    public RambleEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13F)
                .add(Attributes.ATTACK_DAMAGE, 9.0F)
                .add(Attributes.ARMOR,8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,2.0D);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<RambleEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
    }

    public static boolean checkUndergroundMonsterSpawnRules(EntityType<? extends Monster> monster, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource p_219018_) {
        return level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, p_219018_) && checkMobSpawnRules(monster, level, reason, pos, p_219018_);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int i = dimension.monsterSpawnBlockLightLimit();
            if (i < 15 && level.getBrightness(LightLayer.BLOCK, pos) > i) {
                return false;
            } else {
                int j = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return j <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return OPSounds.RAMBLE_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.RAMBLE_DEATH.get();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RambleAttackGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.2D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    protected void blockedByShield(LivingEntity defender) {
        this.playSound(SoundEvents.RAVAGER_STUNNED, 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 39);
        defender.push(this);
        defender.hurtMarked = true;
    }

    public void tick() {
        super.tick();
        if (this.isAlive() && this.getTarget() != null && (level().getDifficulty() != Difficulty.PEACEFUL || !(this.getTarget() instanceof Player))) {
            final float f1 = this.getYRot() * Mth.DEG_TO_RAD;
            this.setDeltaMovement(this.getDeltaMovement().add(-Mth.sin(f1) * 0.02F, 0.0D, Mth.cos(f1) * 0.02F));
            if (this.distanceTo(this.getTarget()) < 3.5F && this.hasLineOfSight(this.getTarget())) {
                boolean flag = this.getTarget().isBlocking();
                if (flag) {
                    if (this.getTarget() instanceof final Player player) {
                        this.damageShieldFor(player, (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
                    }
                }
            }
        }
    }

    protected void damageShieldFor(Player holder, float damage) {
        if (holder.getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
            if (!this.level().isClientSide) {
                holder.awardStat(Stats.ITEM_USED.get(holder.getUseItem().getItem()));
            }

            if (damage >= 3.0F) {
                int i = 1 + Mth.floor(damage);
                InteractionHand hand = holder.getUsedItemHand();
                holder.getUseItem().hurtAndBreak(i, holder, (p_213833_1_) -> {
                    p_213833_1_.broadcastBreakEvent(hand);
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(holder, holder.getUseItem(), hand);
                });
                if (holder.getUseItem().isEmpty()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        holder.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        holder.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    holder.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
                }
            }
        }
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<RambleEntity> event) {
        RambleEntity ramble = event.getAnimatable();
        if (ramble.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("ramble_flail")) {
                ramble.level().playLocalSound(ramble.getX(), ramble.getY(), ramble.getZ(), OPSounds.RAMBLE_ATTACK.get(), ramble.getSoundSource(), 0.5F, ramble.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<RambleEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<RambleEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends RambleEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isInWater()) {
                event.setAndContinue(RAMBLE_WALK);
        } else {
            event.setAndContinue(RAMBLE_IDLE);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends RambleEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (attackState == 21) {
            event.setAndContinue(RAMBLE_AGGRO);
            return PlayState.CONTINUE;
        }
        else if (attackState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    // Goals
    public static class RambleAttackGoal extends Goal {

        protected final RambleEntity ramble;
        private int attackTime = 0;

        public RambleAttackGoal(RambleEntity ramble) {
            this.ramble = ramble;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return ramble.getTarget() != null && ramble.getTarget().isAlive();
        }

        public void start() {
            ramble.setRunning(true);
            ramble.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            ramble.setRunning(false);
            ramble.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = ramble.getTarget();
            if (target != null) {

                ramble.lookAt(ramble.getTarget(), 30F, 30F);
                ramble.getLookControl().setLookAt(ramble.getTarget(), 30F, 30F);

                double distance = ramble.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = ramble.getAttackState();

                if (attackState == 21) {
                    tickFlailAttack();
                    this.ramble.getNavigation().moveTo(target, 1D);
                } else {
                    this.ramble.getNavigation().moveTo(target, 1.25D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            int r = (ramble.getRandom().nextInt(100) + 1);
            if (distance <= 5) {
                if (r <= 40) {
                    ramble.setAttackState(21);
                }
                else if (r <= 80) {
                    ramble.setAttackState(21);
                }
                else {
                    ramble.setAttackState(23);
                }
            }
            else if (distance <= 7) {
                if (r <= 20) {
                    ramble.setAttackState(21);
                }
                else if (r <= 40) {
                    ramble.setAttackState(21);
                }
                else {
                    ramble.setAttackState(23);
                }
            }
        }

        protected void tickFlailAttack () {
            attackTime++;
            ramble.setDeltaMovement(0, ramble.getDeltaMovement().y, 0);

            if(attackTime >= 3) {
                if (ramble.distanceTo(this.ramble.getTarget()) < 2.5f) {
                    ramble.doHurtTarget(ramble.getTarget());
                }
            }
            if(attackTime >= 80) {
                attackTime = 0;
                this.ramble.setAttackState(0);
            }
        }
    }
}
