package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

public class SlugEggs extends ThrowableItemProjectile {

    public SlugEggs(EntityType entity, Level level) {
        super(entity, level);
    }

    public SlugEggs(Level worldIn, LivingEntity throwerIn) {
        super(OPEntities.SLUG_EGGS.get(), throwerIn, worldIn);
    }

    public SlugEggs(Level worldIn, double x, double y, double z) {
        super(OPEntities.SLUG_EGGS.get(), x, y, z, worldIn);
    }

    public SlugEggs(PlayMessages.SpawnEntity entity, Level world) {
        this(OPEntities.SLUG_EGGS.get(), world);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        Entity thrower = getOwner();
        if (!this.level().isClientSide) {
            if (this.random.nextInt(4) == 0) {
                Slug slug = OPEntities.SLUG.get().create(this.level());
                slug.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                if (thrower instanceof Player) {
                    slug.tame((Player) thrower);
                }
                this.level().addFreshEntity(slug);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected Item getDefaultItem() {
        return OPItems.SLUG_EGGS.get();
    }
}
