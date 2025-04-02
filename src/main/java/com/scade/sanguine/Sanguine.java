package com.scade.sanguine;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sanguine implements ModInitializer {
	public static final String MOD_ID = "sanguine";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();


	@Override
	public void onInitialize() {
		LOGGER.info("Sanguine Initialized!");
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "blood"), BLOOD_PARTICLE);

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			if (entity instanceof LivingEntity) {
				spawnBloodParticles(entity);
			}
			return true;
		});
	}

	private void spawnBloodParticles(LivingEntity entity) {
		if (entity.getWorld() instanceof ServerWorld world) {
			Vec3d pos = entity.getPos();

			int particleCount = 35;
			double speed = 0.5;

			for (int i = 0; i < particleCount; i++) {
				double theta = world.random.nextDouble() * 2 * Math.PI;
				double phi = world.random.nextDouble() * Math.PI;
				double x = Math.sin(phi) * Math.cos(theta);
				double y = Math.sin(phi) * Math.sin(theta);
				double z = Math.cos(phi);

				Vec3d direction = new Vec3d(x, y, z).normalize().multiply(speed);

				world.spawnParticles(
						BLOOD_PARTICLE,
						pos.getX(), pos.getY() + .5, pos.getZ(),
						1,
						direction.x, direction.y, direction.z,
						0.1
				);
			}
		}
	}
}