package com.barl_inc.opposing_force.integration.jade;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.entity.Skyvern;
import com.barl_inc.opposing_force.entity.SkyvernSegment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.impl.ui.HealthElement;

public class SkyvernSegmentProvider implements IEntityComponentProvider {

    @Override
    public int getDefaultPriority() {
        return 9999;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        Entity entity = accessor.getEntity();

        if (entity instanceof SkyvernSegment segment) {
            Entity headEntity = segment.getHeadEntity();
            if (headEntity instanceof Skyvern skyvern) {
                float health = skyvern.getHealth();
                float maxHealth = skyvern.getMaxHealth();
                tooltip.add(new HealthElement(maxHealth, health));
            }
        }
    }

	@Override
	public ResourceLocation getUid() {
		return OpposingForce.modPrefix("skyvern_segment");
	}
}