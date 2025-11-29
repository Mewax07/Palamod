package org.mewaxdev;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.component.ModDataComponentTypes;
import org.mewaxdev.event.ModEvents;
import org.mewaxdev.item.ModItemGroups;
import org.mewaxdev.item.ModItems;
import org.mewaxdev.util.HammerUsageEvent;
import org.mewaxdev.world.gen.ModWorldGeneration;
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

		ModEvents.registerEvents();
		ModDataComponentTypes.registerDataComponentTypes();

		ModWorldGeneration.generateModWorldGen();

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		CompostingChanceRegistry.INSTANCE.add(ModItems.KIWANO, 0.9f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.KIWANO_SEEDS, 0.5f);

		CompostingChanceRegistry.INSTANCE.add(ModItems.ORANGEBLUE, 0.80f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.ORANGEBLUE_SEEDS, 0.15f);

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (!world.isClient && state.isOf(ModBlocks.ORANGEBLUE_CROP)) {
				Random random = world.random;
				if (random.nextDouble() < 0.015) {
					ItemStack drop = new ItemStack(ModItems.ENDIUM.INGOT);
					Block.dropStack(world, pos, drop);
				}
			}
		});
	}
}