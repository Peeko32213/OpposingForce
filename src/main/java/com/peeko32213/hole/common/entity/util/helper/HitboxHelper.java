package com.peeko32213.hole.common.entity.util.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class HitboxHelper {

    private static final double d = 0.6f;
    private static final double angleVar = Math.PI/12;

    public static void LargeAttack(DamageSource source, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup){

        Vec2 knockVec = MathHelpers.OrizontalAimVector(
                MathHelpers.AimVector(new Vec3(-entityIn.position().x, -entityIn.position().y, -entityIn.position().z),
                        new Vec3(-entityIn.getTarget().position().x, -entityIn.getTarget().position().y, -entityIn.getTarget().position().z)
                ));

        Vec2 aim = MathHelpers.OrizontalAimVector(entityIn.getLookAngle());
        Level worldIn = entityIn.level();

        for(int i = 0; i<=radius/d; ++i) {

            //double angleVar = Math.asin(1-(1/(2*(i^2)+0.0001)));

            for(int j = 0; j<=(angleLast-angleFirst)/angleVar; ++j) {

                double angle = angleFirst + angleVar*j;

                double x = pos0.x + i*d*(aim.x*Math.cos(angle) - aim.y * Math.sin(angle));
                double z = pos0.z + i*d*(aim.y*Math.cos(angle) + aim.x * Math.sin(angle));

                for(int k = 0; k<=(hSup-hInf)/d; ++k) {

                    double y = pos0.y + hInf + k*d;
                    AABB scanAbove = new AABB(x-d, y - 4*d, z- d, x+ d, y + 2*d, z+ d);
                    //if(!entityIn.level.isClientSide) hitboxOutline(scanAbove, (ServerLevel) entityIn.level);
                    List<LivingEntity> entities = new ArrayList<>(worldIn.getEntitiesOfClass(LivingEntity.class, scanAbove));

                    //if (!entityIn.level.isClientSide()) {
                    //    hitboxOutline(scanAbove, (ServerLevel)entityIn.getLevel());
                    //}

                    if(!entities.isEmpty()) {
                        for(int n = 0; n < entities.size(); n++) {

                            LivingEntity target = entities.get(n);

                            if(target != entityIn) {
                                //entityIn.doHurtTarget(target);
                                target.hurt(source, damage);
                                target.setLastHurtByMob(entityIn);

                                target.knockback(knockback, knockVec.x, knockVec.y);

                            }
                        }
                    }
					/*
				   if(worldIn instanceof ServerLevel) {


						((ServerLevel) worldIn).sendParticles( ParticleTypes.CRIT, x, y, z, 1,  0, 0, 0, 0.4);


					}
					*/

                }
            }
        }

    }


    public static void LargeAttackWithTargetCheck(DamageSource source, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double angleFirst, double angleLast, double hInf, double hSup){

        Vec2 knockVec = MathHelpers.OrizontalAimVector(
                MathHelpers.AimVector(new Vec3(-entityIn.position().x, -entityIn.position().y, -entityIn.position().z),
                        new Vec3(-entityIn.getTarget().position().x, -entityIn.getTarget().position().y, -entityIn.getTarget().position().z)
                ));

        Vec2 aim = MathHelpers.OrizontalAimVector(entityIn.getLookAngle());
        Level worldIn = entityIn.level();


        for(int i = 0; i<=radius/d; ++i) {


            //double angleVar = Math.asin(1-(1/(2*(i^2)+0.0001)));




            for(int j = 0; j<=(angleLast-angleFirst)/angleVar; ++j) {

                double angle = angleFirst + angleVar*j;

                double x = pos0.x + i*d*(aim.x*Math.cos(angle) - aim.y * Math.sin(angle));
                double z = pos0.z + i*d*(aim.y*Math.cos(angle) + aim.x * Math.sin(angle));

                for(int k = 0; k<=(hSup-hInf)/d; ++k) {

                    double y = pos0.y + hInf + k*d;
                    AABB scanAbove = new AABB(x-d, y - 4d, z- d, x+ d, y + 2d, z+ d);
                    List<LivingEntity> entities = new ArrayList<>(worldIn.getEntitiesOfClass(LivingEntity.class, scanAbove));
                    //if(!entityIn.level.isClientSide) hitboxOutline(scanAbove, (ServerLevel) entityIn.level);

                    if(!entities.isEmpty()) {
                        for(int n = 0; n < entities.size(); n++) {

                            LivingEntity target = entities.get(n);

                            if(target == entityIn.getTarget()) {
                                //entityIn.doHurtTarget(target);
                                target.hurt(source, damage);
                                target.setLastHurtByMob(entityIn);

                                target.knockback(knockback, knockVec.x, knockVec.y);

                            }
                        }
                    }
					/*
				   if(worldIn instanceof ServerLevel) {


						((ServerLevel) worldIn).sendParticles( ParticleTypes.CRIT, x, y, z, 1,  0, 0, 0, 0.4);


					}
					*/

                }
            }
        }

    }

    public static void PivotedPolyHitCheck(LivingEntity source, Vec3 boxOffset, double attackWidth, double attackHeight, double attackLength, ServerLevel world, float damage, DamageSource damageSource, float knockback, boolean disableShield) {
        //attackRadius is in blocks

        Vec3 sourcePos = source.position();
        double entityAngle = (source.getYRot());
        Vec3 truePos = sourcePos.add(boxOffset);
        double[] trueXZ = {truePos.x, truePos.z};

        AffineTransform.getRotateInstance(Math.toRadians(entityAngle), sourcePos.x, sourcePos.z).transform(trueXZ, 0, trueXZ, 0, 1);
        double[] transformedTrueXY = trueXZ;
        Vec3 rotatedPos = new Vec3(transformedTrueXY[0], truePos.y, transformedTrueXY[1]);
        BlockPos finalPos = BlockPos.containing(rotatedPos.x, rotatedPos.y, rotatedPos.z);
        AABB Hitbox = new AABB(finalPos).inflate(attackWidth, attackHeight, attackLength);
        //hitboxOutline(Hitbox, world);
        //world.sendParticles(ParticleTypes.EXPLOSION, rotatedPos.x, rotatedPos.y, rotatedPos.z, 1, 0, 0, 0, 0);
        List<LivingEntity> victims = new ArrayList<>(world.getEntitiesOfClass(LivingEntity.class, Hitbox));

        for (int i = 0; i < victims.size(); i++) {
            LivingEntity victim = victims.get(i);

            if (victim != source) {
                //entityIn.doHurtTarget(target);
                if (victim instanceof Player && disableShield == true) {
                    disableShield((Player)victim, victim.getMainHandItem(), victim.getOffhandItem(), source);
                }

                Vec2 knockVec = MathHelpers.OrizontalAimVector(
                        MathHelpers.AimVector(new Vec3(-source.position().x, -source.position().y, -source.position().z),
                                new Vec3(-victim.position().x, -victim.position().y, -victim.position().z)
                        ));


                victim.hurt(damageSource, damage);
                victim.setLastHurtByMob(source);

                victim.knockback(knockback, knockVec.x, knockVec.y);

            }
        }
    }

    public static void disableShield(Player pPlayer, ItemStack mainHand, ItemStack offHand, Entity source) {
        //System.out.println("Shatter " + (!mainHand.isEmpty() && mainHand.is(Items.SHIELD)));

        if (!mainHand.isEmpty() && mainHand.is(Items.SHIELD) && pPlayer.isBlocking()) {
            //float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(source) * 0.05F;
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte)30);

        } else if (!offHand.isEmpty() && offHand.is(Items.SHIELD) && pPlayer.isBlocking()) {
            //float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(source) * 0.05F;
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte)30);
        }

    }

    public static void hitboxOutline (AABB box, ServerLevel world) {
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);

        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void LongAttackWithTargetCheck(DamageSource source, float damage, float knockback, PathfinderMob entityIn, Vec3 pos0, double radius, double edgeS, double edgeR, double hInf, double hSup){

        Vec2 knockVec = MathHelpers.OrizontalAimVector(
                MathHelpers.AimVector(new Vec3(-entityIn.position().x, -entityIn.position().y, -entityIn.position().z),
                        new Vec3(-entityIn.getTarget().position().x, -entityIn.getTarget().position().y, -entityIn.getTarget().position().z)
                ));

        Vec2 aim = MathHelpers.OrizontalAimVector(entityIn.getLookAngle());
        Level worldIn = entityIn.level();


        for(int i = 0; i<=radius/d; ++i) {



            for(int j = Math.round(Math.round(edgeS/d)); j<=edgeR/d; ++j) {

                double angle = edgeR*Math.PI*(2^(-2))/4 + angleVar*j;

                //double x = pos0.x + aim.x*(d*i) + d*(aim.x*Math.cos(angle) - aim.y*Math.sin(angle));
                //double z = pos0.z + aim.y*(d*i) + d*(aim.y*Math.cos(angle) + aim.x * Math.sin(angle));

                double x = pos0.x + aim.x*(d*i + d*j);
                double z = pos0.z + aim.y*(d*i + d*j);

                for(int k = 0; k<=(hSup-hInf)/d; ++k) {

                    double y = pos0.y + hInf + k*d;
                    AABB scanAbove = new AABB(x-d, y - 4d, z- d, x+ d, y + 2d, z+ d);
                    List<LivingEntity> entities = new ArrayList<>(worldIn.getEntitiesOfClass(LivingEntity.class, scanAbove));

                    if(!entities.isEmpty()) {
                        for(int n = 0; n < entities.size(); n++) {

                            LivingEntity target = entities.get(n);

                            if(target == entityIn.getTarget()) {
                                //entityIn.doHurtTarget(target);
                                target.hurt(source, damage);
                                target.setLastHurtByMob(entityIn);

                                target.knockback(knockback, knockVec.x, knockVec.y);

                            }
                        }
                    }
				/*
			   if(worldIn instanceof ServerLevel) {


					((ServerLevel) worldIn).sendParticles( ParticleTypes.CRIT, x, y, z, 1,  0, 0, 0, 0.4);


				}
				*/


                }
            }
        }

    }


}