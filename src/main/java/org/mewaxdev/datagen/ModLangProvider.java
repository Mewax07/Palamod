package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.item.ModItems;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModLangProvider extends FabricLanguageProvider {
	public ModLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(dataOutput, "en_us", registriesFuture);
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			String name = parseName(mat.name());

			if (mat.INGOT != null) translationBuilder.add(mat.INGOT, name + " Ingot");
			if (mat.RAW != null) translationBuilder.add(mat.RAW, "Raw " + name);

			if (mat.SWORD != null) translationBuilder.add(mat.SWORD, name + " Sword");
			if (mat.BROAD_SWORD != null) translationBuilder.add(mat.BROAD_SWORD, name + "Broad Sword");
			if (mat.FAST_SWORD != null) translationBuilder.add(mat.FAST_SWORD, name + "Fast Sword");
			if (mat.PICKAXE != null) translationBuilder.add(mat.PICKAXE, name + " Pickaxe");
			if (mat.AXE != null) translationBuilder.add(mat.AXE, name + " Axe");
			if (mat.SHOVEL != null) translationBuilder.add(mat.SHOVEL, name + " Shovel");
			if (mat.HOE != null) translationBuilder.add(mat.HOE, name + " Hoe");

			if (mat.HAMMER != null) translationBuilder.add(mat.HAMMER, name + " Hammer");

			if (mat.HELMET != null) translationBuilder.add(mat.HELMET, name + " Helmet");
			if (mat.CHESTPLATE != null) translationBuilder.add(mat.CHESTPLATE, name + " Chestplate");
			if (mat.LEGGINGS != null) translationBuilder.add(mat.LEGGINGS, name + " Leggings");
			if (mat.BOOTS != null) translationBuilder.add(mat.BOOTS, name + " Boots");

			for (Map.Entry<String, Item> entry : mat.CUSTOM_ITEMS.entrySet()) {
				String suffix = entry.getKey();
				Item item = entry.getValue();
				translationBuilder.add(item, parseName(mat.name() + " " + suffix));
			}
		}

		for (Item item : ModItems.NON_CLASS_REGISTERED_ITEM.values()) {
			Identifier id = Registries.ITEM.getId(item);
			String name = id.getPath();

			translationBuilder.add(item, parseName(name));
		}

		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			String name = capitalize(mat.name());

			if (mat.BLOCK != null) translationBuilder.add(mat.BLOCK, name + " Block");
			if (mat.STAIRS != null) translationBuilder.add(mat.STAIRS, name + " Block Stairs");
			if (mat.SLAB != null) translationBuilder.add(mat.SLAB, name + " Block Slab");

			if (mat.RAW_BLOCK != null) translationBuilder.add(mat.RAW_BLOCK, "Raw " + name + " Block");
			if (mat.ORE != null) translationBuilder.add(mat.ORE, name + " Ore");
			if (mat.DEEPSLATE_ORE != null) translationBuilder.add(mat.DEEPSLATE_ORE, "Deepslate " + name + " Ore");
		}

		for (Block block : ModBlocks.NON_CLASS_REGISTERED_BLOCKS.values()) {
			Identifier id = Registries.BLOCK.getId(block);
			String name = id.getPath();

			translationBuilder.add(block, parseName(name));
		}

		translationBuilder.add("itemgroup.palamod.palamod_items", "Palamod Items");
		translationBuilder.add("itemgroup.palamod.palamod_blocks", "Palamod Blocks");

		translationBuilder.add("trim_material." + Palamod.MOD_ID + ".paladium", "Paladium Material");
	}

	private static String parseName(String name) {
		return Arrays.stream(name.split("_"))
				.map(ModLangProvider::capitalize)
				.collect(Collectors.joining(" "));
	}

	private static String capitalize(String str) {
		if (str == null || str.isEmpty()) return str;
		return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
	}
}
