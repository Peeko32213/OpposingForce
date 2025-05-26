package com.unusualmodding.opposing_force.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class EmeraldfishEntity extends Monster {
    @Nullable
    private EmeraldfishEntity.SilverfishWakeUpFriendsGoal friendsGoal;

    public EmeraldfishEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.friendsGoal = new EmeraldfishEntity.SilverfishWakeUpFriendsGoal(this);
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(3, this.friendsGoal);
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, false));
        this.goalSelector.addGoal(5, new EmeraldfishEntity.SilverfishMergeWithStoneGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    public double getMyRidingOffset() {
        return 0.1;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.13F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0F).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.ATTACK_DAMAGE, 1.0F);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            if ((pSource.getEntity() != null || pSource.is(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH)) && this.friendsGoal != null) {
                this.friendsGoal.notifyHurt();
            }
            return super.hurt(pSource, pAmount);
        }
    }

    public void tick() {
        this.yBodyRot = this.getYRot();
        super.tick();
    }

    public void setYBodyRot(float pOffset) {
        this.setYRot(pOffset);
        super.setYBodyRot(pOffset);
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return InfestedBlock.isCompatibleHostBlock(pLevel.getBlockState(pPos.below())) ? 10.0F : super.getWalkTargetValue(pPos, pLevel);
    }

    public static boolean checkSilverfishSpawnRules(EntityType<EmeraldfishEntity> pSilverfish, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (checkAnyLightMonsterSpawnRules(pSilverfish, pLevel, pSpawnType, pPos, pRandom)) {
            Player player = pLevel.getNearestPlayer((double) pPos.getX() + (double) 0.5F, (double) pPos.getY() + (double) 0.5F, (double) pPos.getZ() + (double) 0.5F, 5.0F, true);
            return player == null;
        } else {
            return false;
        }
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    static class SilverfishMergeWithStoneGoal extends RandomStrollGoal {

        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public SilverfishMergeWithStoneGoal(EmeraldfishEntity pSilverfish) {
            super(pSilverfish, 1.0F, 10);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            } else if (!this.mob.getNavigation().isDone()) {
                return false;
            } else {
                RandomSource randomsource = this.mob.getRandom();
                if (ForgeEventFactory.getMobGriefingEvent(this.mob.level(), this.mob) && randomsource.nextInt(reducedTickDelay(10)) == 0) {
                    this.selectedDirection = Direction.getRandom(randomsource);
                    BlockPos blockpos = BlockPos.containing(this.mob.getX(), this.mob.getY() + (double)0.5F, this.mob.getZ()).relative(this.selectedDirection);
                    BlockState blockstate = this.mob.level().getBlockState(blockpos);
                    if (InfestedBlock.isCompatibleHostBlock(blockstate)) {
                        this.doMerge = true;
                        return true;
                    }
                }

                this.doMerge = false;
                return super.canUse();
            }
        }

        public boolean canContinueToUse() {
            return this.doMerge ? false : super.canContinueToUse();
        }

        public void start() {
            if (!this.doMerge) {
                super.start();
            } else {
                LevelAccessor levelaccessor = this.mob.level();
                BlockPos blockpos = BlockPos.containing(this.mob.getX(), this.mob.getY() + (double) 0.5F, this.mob.getZ()).relative(this.selectedDirection);
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                if (InfestedBlock.isCompatibleHostBlock(blockstate)) {
                    levelaccessor.setBlock(blockpos, InfestedBlock.infestedStateByHost(blockstate), 3);
                    this.mob.spawnAnim();
                    this.mob.discard();
                }
            }

        }
    }

    static class SilverfishWakeUpFriendsGoal extends Goal {

        private final EmeraldfishEntity silverfish;
        private int lookForFriends;

        public SilverfishWakeUpFriendsGoal(EmeraldfishEntity pSilverfish) {
            this.silverfish = pSilverfish;
        }

        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = this.adjustedTickDelay(20);
            }

        }

        public boolean canUse() {
            return this.lookForFriends > 0;
        }

        public void tick() {
            --this.lookForFriends;
            if (this.lookForFriends <= 0) {
                Level level = this.silverfish.level();
                RandomSource randomsource = this.silverfish.getRandom();
                BlockPos blockpos = this.silverfish.blockPosition();

                for(int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
                    for(int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                        for(int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                            BlockPos blockpos1 = blockpos.offset(j, i, k);
                            BlockState blockstate = level.getBlockState(blockpos1);
                            Block block = blockstate.getBlock();
                            if (block instanceof InfestedBlock) {
                                if (ForgeEventFactory.getMobGriefingEvent(level, this.silverfish)) {
                                    level.destroyBlock(blockpos1, true, this.silverfish);
                                } else {
                                    level.setBlock(blockpos1, ((InfestedBlock)block).hostStateByInfested(level.getBlockState(blockpos1)), 3);
                                }
                                if (randomsource.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}