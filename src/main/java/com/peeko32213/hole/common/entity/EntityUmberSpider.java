package com.peeko32213.hole.common.entity;

import com.peeko32213.hole.common.entity.util.FearTheLightGoal;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.core.registry.HoleSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class EntityUmberSpider extends Spider implements GeoAnimatable, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> SWINGING = SynchedEntityData.defineId(EntityUmberSpider.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_SWUNG = SynchedEntityData.defineId(EntityUmberSpider.class, EntityDataSerializers.BOOLEAN);
    private static final RawAnimation SCURRY = RawAnimation.begin().thenLoop("animation.umber_spider.scurry");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.umber_spider.idle");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("animation.umber_spider.attack");
    public int attackCooldown = 0;

    public EntityUmberSpider(EntityType<? extends Spider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 26.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.3F)
                .add(Attributes.ATTACK_DAMAGE, (double)4.0F);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<EntityUmberSpider> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.6F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
       this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(0, new FearTheLightGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.8D, false));
    }

    protected SoundEvent getAmbientSound() {
        return HoleSounds.UMBER_SPIDER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return HoleSounds.UMBER_SPIDER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return HoleSounds.UMBER_SPIDER_DEATH.get();
    }

    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = this.isLightSensitive() && this.isLightBurnTick();
        if (super.doHurtTarget(pEntity)) {
            if (pEntity instanceof LivingEntity) {
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity)pEntity).addEffect(new MobEffectInstance(MobEffects.WITHER, i * 20, 0), this);
                }
            }
            this.performAttack();
            return true;
        }
        if (flag){
            this.performAttack();
            return false;
        }
        else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SWINGING, false);
        this.entityData.define(HAS_SWUNG, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsSwinging", this.isSwinging());
        compound.putBoolean("HasSwung", this.hasSwung());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSwinging(compound.getBoolean("IsSwinging"));
        this.setHasSwung(compound.getBoolean("HasSwung"));
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) {
            attackCooldown--;

        }

        if (attackCooldown <= 0) {
            //setHasSwung(false);
            setSwinging(false);

        }
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL);
    }

    public boolean isSwinging() {
        return this.entityData.get(SWINGING).booleanValue();
    }

    public void setSwinging(boolean swinging) {
        this.entityData.set(SWINGING, Boolean.valueOf(swinging));
    }

    public boolean hasSwung() {
        return this.entityData.get(HAS_SWUNG).booleanValue();
    }

    public void setHasSwung(boolean swung) {
        this.entityData.set(HAS_SWUNG, Boolean.valueOf(swinging));
    }

    public void performAttack() {
        if (this.level().isClientSide) {
            return;
        }
        this.setSwinging(true);
    }

    protected boolean isLightSensitive() {
        return true;
    }

    protected boolean isLightBurnTick() {
        float f = this.getLightLevelDependentMagicValue();
        return !(f <= 0.5F);
    }


    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isLightSensitive() && this.isLightBurnTick();
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
                    this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2), this);
                    this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 2), this);
                }
            }
        }

        super.aiStep();
    }

    protected <E extends EntityUmberSpider> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isInWater()) {
            event.setAndContinue(SCURRY);
            event.getController().setAnimationSpeed(1.8D);
            return PlayState.CONTINUE;
        }
        else {
            event.setAndContinue(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

        if (pSpawnData == null) {
            pSpawnData = new EntityUmberSpider.UmberSpiderEffectsGroupData();
            if (pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {
                ((EntityUmberSpider.UmberSpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
            }
        }

        if (pSpawnData instanceof EntityUmberSpider.UmberSpiderEffectsGroupData spider$spidereffectsgroupdata) {
            MobEffect mobeffect = spider$spidereffectsgroupdata.effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return pSpawnData;
    }

    public static class UmberSpiderEffectsGroupData implements SpawnGroupData {
        @Nullable
        public MobEffect effect;

        public void setRandomEffect(RandomSource pRandom) {
            int i = pRandom.nextInt(6);
            if (i <= 1) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i <= 2) {
                this.effect = MobEffects.REGENERATION;
            } else if (i <= 3) {
                this.effect = MobEffects.DAMAGE_RESISTANCE;
            } else if (i <= 4) {
                this.effect = MobEffects.ABSORPTION;
            } else if (i <= 5) {
                this.effect = MobEffects.JUMP;
            }

        }
    }

    protected <E extends EntityUmberSpider> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(ATTACK);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
        controllers.add(new AnimationController<>(this, "Attack", 5, this::attackController));
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
