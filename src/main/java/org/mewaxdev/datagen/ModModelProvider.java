package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.block.custom.ModCropBlock;
import org.mewaxdev.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			if (mat.BLOCK != null) {
				BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(mat.BLOCK);

				if (mat.STAIRS != null) pool.stairs(mat.STAIRS);
				if (mat.SLAB != null) pool.slab(mat.SLAB);
			}

			if (mat.RAW_BLOCK != null) blockStateModelGenerator.registerSimpleCubeAll(mat.RAW_BLOCK);
			if (mat.ORE != null) blockStateModelGenerator.registerSimpleCubeAll(mat.ORE);
			if (mat.DEEPSLATE_ORE != null) blockStateModelGenerator.registerSimpleCubeAll(mat.DEEPSLATE_ORE);
		}

		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WARNING);
		blockStateModelGenerator.registerCrop(ModBlocks.KIWANO_CROP, ModCropBlock.AGE, 0, 1, 2, 3, 4);
		blockStateModelGenerator.registerCrop(ModBlocks.ORANGEBLUE_CROP, ModCropBlock.AGE, 0, 1, 2, 3, 4);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			if (mat.INGOT != null) itemModelGenerator.register(mat.INGOT, Models.GENERATED);
			if (mat.RAW != null) itemModelGenerator.register(mat.RAW, Models.GENERATED);

			if (mat.SWORD != null) itemModelGenerator.register(mat.SWORD, Models.HANDHELD);
			if (mat.BROAD_SWORD != null) itemModelGenerator.register(mat.BROAD_SWORD, Models.HANDHELD);
			if (mat.FAST_SWORD != null) itemModelGenerator.register(mat.FAST_SWORD, Models.HANDHELD);
			if (mat.PICKAXE != null) itemModelGenerator.register(mat.PICKAXE, Models.HANDHELD);
			if (mat.AXE != null) itemModelGenerator.register(mat.AXE, Models.HANDHELD);
			if (mat.SHOVEL != null) itemModelGenerator.register(mat.SHOVEL, Models.HANDHELD);
			if (mat.HOE != null) itemModelGenerator.register(mat.HOE, Models.HANDHELD);

			if (mat.HAMMER != null) itemModelGenerator.register(mat.HAMMER, Models.HANDHELD);

			if (mat.HELMET != null) itemModelGenerator.registerArmor((ArmorItem) mat.HELMET);
			if (mat.CHESTPLATE != null) itemModelGenerator.registerArmor((ArmorItem) mat.CHESTPLATE);
			if (mat.LEGGINGS != null) itemModelGenerator.registerArmor((ArmorItem) mat.LEGGINGS);
			if (mat.BOOTS != null) itemModelGenerator.registerArmor((ArmorItem) mat.BOOTS);

			for (Item custom : mat.CUSTOM_ITEMS.values()) {
				itemModelGenerator.register(custom, Models.GENERATED);
			}
		}

		for (Item custom : ModItems.NON_CLASS_REGISTERED_ITEM.values()) {
			itemModelGenerator.register(custom, Models.GENERATED);
		}
	}
}
