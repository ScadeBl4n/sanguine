package com.scade.sanguine;

import com.scade.sanguine.client.BloodParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class SanguineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(Sanguine.BLOOD_PARTICLE, BloodParticle.Factory::new);
    }
}
