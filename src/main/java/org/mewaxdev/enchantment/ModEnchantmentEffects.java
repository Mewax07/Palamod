package org.mewaxdev.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.enchantment.custom.SmeltDrops;
import org.mewaxdev.enchantment.custom.SmeltingEnchantmentEffect;

import java.util.List;

public class ModEnchantmentEffects {
	public static final ComponentType<List<SmeltDrops>> SMELT_DROPS =
			Registry.register(
					Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
					Identifier.of(Palamod.MOD_ID, "smelt_drops"),
					ComponentType.<List<SmeltDrops>>builder()
							.codec(SmeltDrops.CODEC.listOf())
							.build()
			);

	private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(
			String name,
			MapCodec<? extends EnchantmentEntityEffect> codec
	) {
		return Registry.register(
				Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
				Identifier.of(Palamod.MOD_ID, name),
				codec
		);
	}

	public static void registerEnchantmentEffects() {
		Palamod.LOGGER.info("Registering Palamod Enchantment Effects");
	}
}
