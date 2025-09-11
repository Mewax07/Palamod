package org.mewaxdev.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;

public class ModItemGroups {
	public static final ItemGroup PALAMOD_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
			Identifier.of(Palamod.MOD_ID, "palamod_items"),
			FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.PALADIUM_INGOT))
					.displayName(Text.translatable("itemgroup.palamod.palamod_items"))
					.entries((displayContext, entries) -> {
						entries.add(ModItems.PALADIUM_INGOT);
						entries.add(ModItems.RAW_PALADIUM);

						entries.add(ModItems.PALADIUM_CHISEL);

						entries.add(ModItems.PALADIUM_SWORD);
						entries.add(ModItems.PALADIUM_PICKAXE);
						entries.add(ModItems.PALADIUM_AXE);
						entries.add(ModItems.PALADIUM_SHOVEL);
						entries.add(ModItems.PALADIUM_HOE);

						entries.add(ModItems.PALADIUM_HAMMER);

						entries.add(ModItems.PALADIUM_HELMET);
						entries.add(ModItems.PALADIUM_CHESTPLATE);
						entries.add(ModItems.PALADIUM_LEGGINGS);
						entries.add(ModItems.PALADIUM_BOOTS);

						entries.add(ModItems.PALADIUM_SEEDS);
					})
					.build()
	);

	public static final ItemGroup PALAMOD_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
			Identifier.of(Palamod.MOD_ID, "palamod_blocks"),
			FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.PALADIUM_BLOCK))
					.displayName(Text.translatable("itemgroup.palamod.palamod_blocks"))
					.entries((displayContext, entries) -> {
						entries.add(ModBlocks.PALADIUM_BLOCK);
						entries.add(ModBlocks.PALADIUM_BLOCK_STAIRS);
						entries.add(ModBlocks.PALADIUM_BLOCK_SLAB);
						entries.add(ModBlocks.RAW_PALADIUM_BLOCK);
						entries.add(ModBlocks.PALADIUM_ORE);
						entries.add(ModBlocks.DEEPSLATE_PALADIUM_ORE);
					})
					.build()
	);

	public static void registerItemGroups() {
		Palamod.LOGGER.info("Registering Palamod Item Groups.");
	}
}
