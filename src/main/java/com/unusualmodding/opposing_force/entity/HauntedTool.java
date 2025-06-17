package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.blocks.InfestedAmethyst;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class HauntedTool extends Monster {

    private static final EntityDataAccessor<ItemStack> ITEMSTACK = SynchedEntityData.defineId(HauntedTool.class, EntityDataSerializers.ITEM_STACK);

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel) {
            public boolean isStableDestination(BlockPos pos) {
                return !level().getBlockState(pos.below(2)).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public HauntedTool(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.FLYING_SPEED, 0.9F).add(Attributes.MOVEMENT_SPEED, 0.45F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new HauntedToolWanderGoal(this));
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    public void tick() {
        super.tick();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEMSTACK, new ItemStack(Items.DIAMOND_SWORD));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (!this.getItemStack().isEmpty()) {
            CompoundTag stackTag = new CompoundTag();
            this.getItemStack().save(stackTag);
            compoundTag.put("HauntedItem", stackTag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("HauntedItem")) {
            this.setItemStack(ItemStack.of(compoundTag.getCompound("HauntedItem")));
        }
    }

    public ItemStack getItemStack() {
        return this.entityData.get(ITEMSTACK);
    }

    public void setItemStack(ItemStack item) {
        this.entityData.set(ITEMSTACK, item);
    }

    public double getDamageForItem(ItemStack itemStack) {
        Multimap<Attribute, AttributeModifier> map = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        if (!map.isEmpty()) {
            double damage = 0;
            for (AttributeModifier modifier : map.get(Attributes.ATTACK_DAMAGE)) {
                damage += modifier.getAmount();
            }
            return damage;
        }
        return 0;
    }

    // goals
    private class HauntedToolWanderGoal extends Goal {

        private final HauntedTool tool;

        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public HauntedToolWanderGoal(HauntedTool tool) {
            this.tool = tool;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.tool.getTarget() != null) {
                return false;
            } else if (!this.tool.getNavigation().isDone()) {
                return false;
            } else {
                RandomSource randomsource = this.tool.getRandom();
                if (ForgeEventFactory.getMobGriefingEvent(this.tool.level(), this.tool) && randomsource.nextInt(reducedTickDelay(10)) == 0) {
                    this.selectedDirection = Direction.getRandom(randomsource);
                    BlockPos blockpos = BlockPos.containing(this.tool.getX(), this.tool.getY() + (double) 0.5F, this.tool.getZ()).relative(this.selectedDirection);
                    BlockState blockstate = this.tool.level().getBlockState(blockpos);
                    if (InfestedAmethyst.isCompatibleHostBlock(blockstate)) {
                        this.doMerge = true;
                        return true;
                    }
                }
                this.doMerge = false;
                return this.tool.getNavigation().isDone() && this.tool.getRandom().nextInt(10) == 0;
            }
        }

        public boolean canContinueToUse() {
            return !this.doMerge && this.tool.getNavigation().isInProgress();
        }

        public void start() {
            if (!this.doMerge) {
                Vec3 vec3 = this.findPos();
                if (vec3 != null) {
                    this.tool.getNavigation().moveTo(this.tool.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0);
                }
            } else {
                LevelAccessor levelaccessor = this.tool.level();
                BlockPos blockpos = BlockPos.containing(this.tool.getX(), this.tool.getY() + (double) 0.5F, this.tool.getZ()).relative(this.selectedDirection);
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                if (InfestedAmethyst.isCompatibleHostBlock(blockstate)) {
                    levelaccessor.setBlock(blockpos, InfestedAmethyst.infestedStateByHost(blockstate), 3);
                    this.tool.spawnAnim();
                    this.tool.discard();
                }
            }
        }

        public Vec3 findPos() {
            Vec3 vec32;
            vec32 = this.tool.getViewVector(0.0F);

            Vec3 vec33 = HoverRandomPos.getPos(this.tool, 8, 7, vec32.x, vec32.z, 1.5707964F, 3, 1);
            return vec33 != null ? vec33 : AirAndWaterRandomPos.getPos(this.tool, 8, 4, -2, vec32.x, vec32.z, 1.5707963705062866);
        }
    }
}
