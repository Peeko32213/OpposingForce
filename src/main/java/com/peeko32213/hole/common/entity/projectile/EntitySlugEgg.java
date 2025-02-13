package com.peeko32213.hole.common.entity.projectile;
import com.peeko32213.hole.common.entity.EntityFrowzy;
import com.peeko32213.hole.common.entity.EntitySlug;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class EntitySlugEgg extends ThrowableItemProjectile {

    public EntitySlugEgg(EntityType p_i50154_1_, Level p_i50154_2_) {
        super(p_i50154_1_, p_i50154_2_);
    }

    public EntitySlugEgg(Level worldIn, LivingEntity throwerIn) {
        super(HoleEntities.SLUG_EGG.get(), throwerIn, worldIn);
    }

    public EntitySlugEgg(Level worldIn, double x, double y, double z) {
        super(HoleEntities.SLUG_EGG.get(), x, y, z, worldIn);
    }

    public EntitySlugEgg(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(HoleEntities.SLUG_EGG.get(), world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
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
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                EntitySlug lvt_4_1_ = HoleEntities.SLUG.get().create(this.level());
                lvt_4_1_.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(lvt_4_1_);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

    }

    protected Item getDefaultItem() {
        return HoleItems.SLUG_EGG.get();
    }
}
