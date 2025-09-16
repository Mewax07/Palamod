package org.mewaxdev.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.item.ModItems;
import org.mewaxdev.item.custom.PickaxeOfGod;

public class ModModelPredicates {
	public static void registerModelPredicates() {
		ModelPredicateProviderRegistry.register(
				ModItems.GOD_PICKAXE,
				Identifier.of(Palamod.MOD_ID, "level"),
				(stack, world, entity, seed) -> {
					int lvl = PickaxeOfGod.getLevel(stack);
					return lvl / 100.0F;
				}
		);
	}
}
