package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.AttackGoal;
import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class Frowzy extends Monster implements IAnimatedAttacker {

    private static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(Frowzy.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Frowzy.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> JUMP_COOLDOWN = SynchedEntityData.defineId(Frowzy.class, EntityDataSerializers.INT);

    private static final UUID BABY_SPEED_MODIFIER_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier BABY_SPEED_MODIFIER = new AttributeModifier(BABY_SPEED_MODIFIER_UUID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (difficulty) -> difficulty == Difficulty.HARD;
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;

    private int runTimer = 0;

    public Frowzy(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FrowzyAttackGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(4, new FrowzyAttackTurtleEggGoal(this, 1.0D, 3));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(Frowzy.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Bat.class, true));
    }

    protected void handleAttributes(float chance) {
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * (double) 0.05F, AttributeModifier.Operation.ADDITION));
        double random = this.random.nextDouble() * 1.5D * (double) chance;
        if (random > 1.0D) {
            this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random frowzy-spawn bonus", random, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (this.random.nextFloat() < chance * 0.05F) {
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader frowzy bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            this.setCanBreakDoors(this.supportsBreakDoorGoal());
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        return entity.is(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.9F;
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 0.6F : 1.0F;
    }

    protected boolean supportsBreakDoorGoal() {
        return true;
    }

    @Override
    public int getExperienceReward() {
        if (this.isBaby()) {
            this.xpReward = (int) ((double) this.xpReward * 2.5D);
        }
        return super.getExperienceReward();
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.broadcastBreakEvent(EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    this.setSecondsOnFire(8);
                }
            }
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getJumpCooldown() > 0) {
            this.setJumpCooldown(this.getJumpCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.isAggressive() && this.isAlive()) {
            this.runTimer++;
            if (this.runTimer == 1) {
                playSound(OPSoundEvents.FROWZY_RUN.get(), 1.0F, this.getVoicePitch());
            }
            if (this.runTimer > 320) {
                this.runTimer = 0;
            }
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.attackAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag) {
            float difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < difficulty * 0.3F) {
                entity.setSecondsOnFire(2 * (int) difficulty);
            }
        }
        return flag;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BABY, false);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(JUMP_COOLDOWN, 80 + random.nextInt(80));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsBaby", this.isBaby());
        compoundTag.putBoolean("CanBreakDoors", this.canBreakDoors());
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putInt("JumpCooldown", this.getJumpCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBaby(compoundTag.getBoolean("IsBaby"));
        this.setCanBreakDoors(compoundTag.getBoolean("CanBreakDoors"));
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setJumpCooldown(compoundTag.getInt("JumpCooldown"));
    }

    public int getJumpCooldown() {
        return this.entityData.get(JUMP_COOLDOWN);
    }

    public void setJumpCooldown(int cooldown) {
        this.entityData.set(JUMP_COOLDOWN, cooldown);
    }

    public void jumpCooldown() {
        this.entityData.set(JUMP_COOLDOWN, 80 + random.nextInt(80));
    }

    @Override
    public boolean isBaby() {
        return this.getEntityData().get(IS_BABY);
    }

    @Override
    public void setBaby(boolean baby) {
        this.getEntityData().set(IS_BABY, baby);
        if (this.level() != null && !this.level().isClientSide) {
            AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(BABY_SPEED_MODIFIER);
            if (baby) {
                attributeinstance.addTransientModifier(BABY_SPEED_MODIFIER);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (IS_BABY.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public static boolean getSpawnAsBabyOdds(RandomSource randomSource) {
        return randomSource.nextFloat() < ForgeConfig.SERVER.zombieBabyChance.get();
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean p_34337_) {
        if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != p_34337_) {
                this.canBreakDoors = p_34337_;
                ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(p_34337_);
                if (p_34337_) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.45D;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int amount, boolean drops) {
        super.dropCustomDeathLoot(damageSource, amount, drops);
        Entity entity = damageSource.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack itemstack = new ItemStack(OPItems.FROWZY_HEAD.get());
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemstack);
            }
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.FROWZY_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return OPSoundEvents.FROWZY_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.FROWZY_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_34316_, @NotNull BlockState p_34317_) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.2F);
    }

    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkFrowzySpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkFrowzySpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 32 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int lightLimit = dimension.monsterSpawnBlockLightLimit();
            if (lightLimit < 15 && level.getBrightness(LightLayer.BLOCK, pos) > lightLimit) {
                return false;
            } else {
                int lightTest = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return lightTest <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }

    public static class FrowzyGroupData implements SpawnGroupData {
        public final boolean isBaby;
        public final boolean canSpawnJockey;

        public FrowzyGroupData(boolean isBaby, boolean chickenJockeyyy) {
            this.isBaby = isBaby;
            this.canSpawnJockey = chickenJockeyyy;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag compoundTag) {
        RandomSource randomsource = level.getRandom();

        groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData, compoundTag);
        float f = difficulty.getSpecialMultiplier();

        if (groupData == null) {
            groupData = new FrowzyGroupData(getSpawnAsBabyOdds(randomsource), true);
        }

        if (groupData instanceof Zombie.ZombieGroupData frowzyGroupData) {
            if (frowzyGroupData.isBaby) {
                this.setBaby(true);
                if (frowzyGroupData.canSpawnJockey) {
                    if ((double) randomsource.nextFloat() < 0.05D) {
                        List<Chicken> list = level.getEntitiesOfClass(Chicken.class, this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
                        if (!list.isEmpty()) {
                            Chicken chicken = list.get(0);
                            chicken.setChickenJockey(true);
                            this.startRiding(chicken);
                        }
                    } else if ((double) randomsource.nextFloat() < 0.05D) {
                        Chicken chicken1 = EntityType.CHICKEN.create(this.level());
                        if (chicken1 != null) {
                            chicken1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                            chicken1.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null, null);
                            chicken1.setChickenJockey(true);
                            this.startRiding(chicken1);
                            level.addFreshEntity(chicken1);
                        }
                    }
                }
            }
            this.setCanBreakDoors(this.supportsBreakDoorGoal() && randomsource.nextFloat() < f * 0.1F);
        }
        this.handleAttributes(f);
        return groupData;
    }

    // goals
    private static class FrowzyAttackGoal extends AttackGoal {

        protected final Frowzy frowzy;

        public FrowzyAttackGoal(Frowzy frowzy) {
            super(frowzy);
            this.frowzy = frowzy;
        }

        @Override
        public void tick() {
            LivingEntity target = this.frowzy.getTarget();
            if (target != null) {
                this.frowzy.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());

                double distance = this.frowzy.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.frowzy.getAttackState();

                this.frowzy.getNavigation().moveTo(target, 1.2D);

                if (attackState == 1) {
                    tickAttack();
                } else {
                    checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance) {
            if (this.frowzy.isBaby() && distance <= 1.25D) {
                this.frowzy.setAttackState(1);
            } else if (distance <= 2) {
                this.frowzy.setAttackState(1);
            }
            else if (distance >= 5 && distance <= 12 && this.frowzy.onGround() && this.frowzy.getJumpCooldown() <= 0) {
                this.jump();
            }
        }

        protected void tickAttack() {
            timer++;
            LivingEntity target = this.frowzy.getTarget();

            if (timer == 1) {
                this.frowzy.playSound(OPSoundEvents.FROWZY_ATTACK.get(), 1.0F, this.frowzy.getVoicePitch());
            }

            if (timer == 5) {
                if (this.frowzy.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.frowzy.doHurtTarget(target);
                    this.frowzy.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer >= 20) {
                timer = 0;
                this.frowzy.setAttackState(0);
            }
        }

        public void jump() {
            this.frowzy.getNavigation().stop();
            Vec3 vec3 = this.frowzy.getDeltaMovement();
            Vec3 leapVec = new Vec3(Objects.requireNonNull(this.frowzy.getTarget()).getX() - this.frowzy.getX(), 0.0F, this.frowzy.getTarget().getZ() - this.frowzy.getZ());
            if (leapVec.lengthSqr() > 1.0E-7) {
                leapVec = leapVec.normalize().scale(1).add(vec3.scale(0.5));
            }
            this.frowzy.setDeltaMovement(leapVec.x, 0.57D, leapVec.z);
            this.frowzy.jumpCooldown();
        }
    }

    private class FrowzyAttackTurtleEggGoal extends RemoveBlockGoal {

        FrowzyAttackTurtleEggGoal(PathfinderMob frowzy, double speed, int chance) {
            super(Blocks.TURTLE_EGG, frowzy, speed, chance);
        }

        public void playDestroyProgressSound(LevelAccessor level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + Frowzy.this.random.nextFloat() * 0.2F);
        }

        public void playBreakSound(Level level, BlockPos blockPos) {
            level.playSound(null, blockPos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        public double acceptedDistance() {
            return 1.14D;
        }
    }
}
