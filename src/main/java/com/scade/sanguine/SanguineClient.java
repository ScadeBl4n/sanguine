package com.scade.sanguine;

import com.scade.sanguine.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;


public class SanguineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(Sanguine.BLOOD_PARTICLE, BloodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.PURPLE_BLOOD_PARTICLE, BloodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.BLUE_BLOOD_PARTICLE, BlueBloodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.SPARKS_PARTICLE, SparksParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.BONE_DUST_PARTICLE, BoneDustParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.WITHERED_BONE_DUST_PARTICLE, BoneDustParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.SLIME_PARTICLE, SlimeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Sanguine.MAGMA_PARTICLE, SlimeParticle.Factory::new);
    }
}
