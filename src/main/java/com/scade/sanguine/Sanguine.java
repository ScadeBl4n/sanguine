package com.scade.sanguine;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Sanguine implements ModInitializer {
	public static final String MOD_ID = "sanguine";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType PURPLE_BLOOD_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType BLUE_BLOOD_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SPARKS_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType BONE_DUST_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType WITHERED_BONE_DUST_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SLIME_PARTICLE = FabricParticleTypes.simple();
	public static final DefaultParticleType MAGMA_PARTICLE = FabricParticleTypes.simple();



	public static final TagKey<EntityType<?>> BLEEDS_RED = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_red"));
	public static final TagKey<EntityType<?>> BLEEDS_PURPLE = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_purple"));
	public static final TagKey<EntityType<?>> BLEEDS_BLUE = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_blue"));
	public static final TagKey<EntityType<?>> SPARKS = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "sparks"));
	public static final TagKey<EntityType<?>> BLEEDS_BONE_DUST = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_bone_dust"));
	public static final TagKey<EntityType<?>> BLEEDS_WITHERED_BONE_DUST = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_withered_bone_dust"));
	public static final TagKey<EntityType<?>> BLEEDS_SLIME = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_slime"));
	public static final TagKey<EntityType<?>> BLEEDS_MAGMA = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(MOD_ID, "bleeds_magma"));



	@Override
	public void onInitialize() {
		LOGGER.info("Sanguine Initialized!");
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "blood"), BLOOD_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "purple_blood"), PURPLE_BLOOD_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "blue_blood"), BLUE_BLOOD_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "sparks"), SPARKS_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "bone_dust"), BONE_DUST_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "withered_bone_dust"), WITHERED_BONE_DUST_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "slime"), SLIME_PARTICLE);
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "magma"), MAGMA_PARTICLE);




		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			if (entity instanceof LivingEntity) {
				spawnBloodParticles(entity);
			}
			return true;
		});
	}

	private static final Map<TagKey<EntityType<?>>, DefaultParticleType> BLOOD_PARTICLE_MAP = Map.of(
			BLEEDS_RED, BLOOD_PARTICLE,
			BLEEDS_PURPLE, PURPLE_BLOOD_PARTICLE,
			BLEEDS_BLUE, BLUE_BLOOD_PARTICLE,
			SPARKS, SPARKS_PARTICLE,
			BLEEDS_BONE_DUST, BONE_DUST_PARTICLE,
			BLEEDS_WITHERED_BONE_DUST, WITHERED_BONE_DUST_PARTICLE,
			BLEEDS_SLIME, SLIME_PARTICLE,
			BLEEDS_MAGMA, MAGMA_PARTICLE
	);



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

				for (var entry : BLOOD_PARTICLE_MAP.entrySet()) {
					if (entity.getType().isIn(entry.getKey())) {
						world.spawnParticles(
								entry.getValue(),
								pos.getX(), pos.getY() + .5, pos.getZ(),
								1,
								direction.x, direction.y, direction.z,
								0.1
						);
						break;
					}
				}
			}
		}
	}
}