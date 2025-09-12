package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.block.custom.PaladiumCropBlock;
import org.mewaxdev.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		BlockStateModelGenerator.BlockTexturePool paladiumBlockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PALADIUM_BLOCK);
		paladiumBlockPool.stairs(ModBlocks.PALADIUM_BLOCK_STAIRS);
		paladiumBlockPool.slab(ModBlocks.PALADIUM_BLOCK_SLAB);

		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_PALADIUM_BLOCK);

		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PALADIUM_ORE);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_PALADIUM_ORE);

		blockStateModelGenerator.registerCrop(ModBlocks.PALADIUM_CROP, PaladiumCropBlock.AGE, 0, 1, 2, 3, 4, 5, 6);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			if (mat.INGOT != null) itemModelGenerator.register(mat.INGOT, Models.GENERATED);
			if (mat.RAW != null) itemModelGenerator.register(mat.RAW, Models.GENERATED);

			if (mat.SWORD != null) itemModelGenerator.register(mat.SWORD, Models.HANDHELD);
			if (mat.PICKAXE != null) itemModelGenerator.register(mat.PICKAXE, Models.HANDHELD);
			if (mat.AXE != null) itemModelGenerator.register(mat.AXE, Models.HANDHELD);
			if (mat.SHOVEL != null) itemModelGenerator.register(mat.SHOVEL, Models.HANDHELD);
			if (mat.HOE != null) itemModelGenerator.register(mat.HOE, Models.HANDHELD);

			if (mat.HAMMER != null) itemModelGenerator.register(mat.HAMMER, Models.HANDHELD);

			if (mat.HELMET != null) itemModelGenerator.registerArmor((ArmorItem) mat.HELMET);
			if (mat.CHESTPLATE != null) itemModelGenerator.registerArmor((ArmorItem) mat.CHESTPLATE);
			if (mat.LEGGINGS != null) itemModelGenerator.registerArmor((ArmorItem) mat.LEGGINGS);
			if (mat.BOOTS != null) itemModelGenerator.registerArmor((ArmorItem) mat.BOOTS);
		}

		itemModelGenerator.register(ModItems.PALA_FLOWER, Models.GENERATED);
		itemModelGenerator.register(ModItems.PALADIUM_CHISEL, Models.GENERATED);
	}
}
