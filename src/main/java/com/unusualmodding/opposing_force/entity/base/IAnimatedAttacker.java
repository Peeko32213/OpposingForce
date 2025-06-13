package com.unusualmodding.opposing_force.entity.base;

public interface IAnimatedAttacker {

    boolean isAttacking();

    void setAttacking(boolean attacking);

    int attackAnimationTimeout();

    void setAttackAnimationTimeout(int attackAnimationTimeout);
}
