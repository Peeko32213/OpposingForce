package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Slug;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class SlugAttackGoal extends MeleeAttackGoal {

    protected final Slug slug;

    public SlugAttackGoal(Slug slug) {
        super(slug, 1.2D, false);
        this.slug = slug;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.slug.getBbWidth() * 1.25F * this.slug.getBbWidth() * 1.25F + target.getBbWidth();
    }
}