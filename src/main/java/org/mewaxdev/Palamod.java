package org.mewaxdev;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.enchantment.ModEnchantmentEffects;
import org.mewaxdev.item.ModItemGroups;
import org.mewaxdev.item.ModItems;
import org.mewaxdev.util.HammerUsageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Palamod implements ModInitializer {
	public static final String MOD_ID = "palamod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();

		ModEnchantmentEffects.registerEnchantmentEffects();

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		CompostingChanceRegistry.INSTANCE.add(ModItems.PALA_FLOWER, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.PALADIUM_SEEDS, 0.25f);
	}
}