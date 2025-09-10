package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import org.mewaxdev.block.ModBlocks;
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
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.PALADIUM_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.RAW_PALADIUM, Models.GENERATED);

		itemModelGenerator.register(ModItems.PALADIUM_CHISEL, Models.GENERATED);

		itemModelGenerator.register(ModItems.PALADIUM_SWORD, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PALADIUM_PICKAXE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PALADIUM_AXE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PALADIUM_SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PALADIUM_HOE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PALADIUM_HAMMER, Models.HANDHELD);
	}
}
