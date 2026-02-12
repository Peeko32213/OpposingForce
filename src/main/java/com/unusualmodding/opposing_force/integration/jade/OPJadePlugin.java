package com.unusualmodding.opposing_force.integration.jade;

import com.unusualmodding.opposing_force.entity.SkyvernSegment;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class OPJadePlugin implements IWailaPlugin {

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(new SkyvernSegmentProvider(), SkyvernSegment.class);
	}
}