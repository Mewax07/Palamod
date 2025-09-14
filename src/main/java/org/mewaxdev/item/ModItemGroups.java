package org.mewaxdev.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;

import java.util.Map;

public class ModItemGroups {
	public static final ItemGroup PALAMOD_ITEMS_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			Identifier.of(Palamod.MOD_ID, "palamod_items"),
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(ModItems.PALADIUM.INGOT))
					.displayName(Text.translatable("itemgroup.palamod.palamod_items"))
					.entries((displayContext, entries) -> {
						for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
							if (mat.INGOT != null) entries.add(mat.INGOT);
							if (mat.RAW != null) entries.add(mat.RAW);

							if (mat.SWORD != null) entries.add(mat.SWORD);
							if (mat.BROAD_SWORD != null) entries.add(mat.BROAD_SWORD);
							if (mat.FAST_SWORD != null) entries.add(mat.FAST_SWORD);
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

						for (Item items : ModItems.NON_CLASS_REGISTERED_ITEM.values()) {
							entries.add(items);
						}
					})
					.build()
	);

	public static final ItemGroup PALAMOD_BLOCKS_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			Identifier.of(Palamod.MOD_ID, "palamod_blocks"),
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(ModBlocks.PALADIUM.BLOCK))
					.displayName(Text.translatable("itemgroup.palamod.palamod_blocks"))
					.entries((displayContext, entries) -> {
						for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
							if (mat.BLOCK != null) entries.add(mat.BLOCK);
							if (mat.STAIRS != null) entries.add(mat.STAIRS);
							if (mat.SLAB != null) entries.add(mat.SLAB);

							if (mat.RAW_BLOCK != null) entries.add(mat.RAW_BLOCK);
							if (mat.ORE != null) entries.add(mat.ORE);
							if (mat.DEEPSLATE_ORE != null) entries.add(mat.DEEPSLATE_ORE);
						}

						for (Block block : ModBlocks.NON_CLASS_REGISTERED_BLOCKS.values()) {
							entries.add(block);
						}
					})
					.build()
	);

	public static void registerItemGroups() {
		Palamod.LOGGER.info("Registering Palamod Item Groups.");
	}
}
