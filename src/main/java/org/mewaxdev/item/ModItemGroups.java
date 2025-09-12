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

import java.util.List;

public class ModItemGroups {
	public static final ItemGroup PALAMOD_ITEMS_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			Identifier.of(Palamod.MOD_ID, "palamod_items"),
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(ModItems.PALADIUM.INGOT))
					.displayName(Text.translatable("itemgroup.palamod.palamod_items"))
					.entries((displayContext, entries) -> {

						List<ModItems.MaterialItems> allMaterials = List.of(
								ModItems.PALADIUM,
								ModItems.ENDIUM
						);

						for (ModItems.MaterialItems mat : allMaterials) {
							if (mat.INGOT != null) entries.add(mat.INGOT);
							if (mat.RAW != null) entries.add(mat.RAW);

							if (mat.SWORD != null) entries.add(mat.SWORD);
							if (mat.PICKAXE != null) entries.add(mat.PICKAXE);
							if (mat.AXE != null) entries.add(mat.AXE);
							if (mat.SHOVEL != null) entries.add(mat.SHOVEL);
							if (mat.HOE != null) entries.add(mat.HOE);
							if (mat.HAMMER != null) entries.add(mat.HAMMER);

							if (mat.HELMET != null) entries.add(mat.HELMET);
							if (mat.CHESTPLATE != null) entries.add(mat.CHESTPLATE);
							if (mat.LEGGINGS != null) entries.add(mat.LEGGINGS);
							if (mat.BOOTS != null) entries.add(mat.BOOTS);
						}

						entries.add(ModItems.PALADIUM_SEEDS);
						entries.add(ModItems.PALA_FLOWER);
						entries.add(ModItems.PALADIUM_CHISEL);
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
