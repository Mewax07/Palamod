package org.mewaxdev.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
	public static final ComponentType<Integer> XP_LEVEL = register("xp_level", build -> build.codec(Codec.INT));
	public static final ComponentType<Integer> LEVEL = register("level", build -> build.codec(Codec.INT));

	private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Palamod.MOD_ID, name),
				builderOperator.apply(ComponentType.builder()).build());
	}

	public static void registerDataComponentTypes() {
		Palamod.LOGGER.info("Registering Data Component Types for " + Palamod.MOD_ID);
	}
}
