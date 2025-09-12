package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.block.custom.PaladiumCropBlock;
import org.mewaxdev.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
	public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			String name = mat.name().toLowerCase();

			if (ModBlocks.hasBlock(name + "_block")) {
				addDrop(ModBlocks.getBlock(name + "_block"));
			}

			if (ModBlocks.hasBlock(name + "_block_stairs")) {
				addDrop(ModBlocks.getBlock(name + "_block_stairs"));
			}

			if (ModBlocks.hasBlock(name + "_block_slab")) {
				addDrop(ModBlocks.getBlock(name + "_block_slab"), slabDrops(ModBlocks.getBlock(name + "_block_slab")));
			}

			if (ModBlocks.hasBlock("raw_" + name + "_block")) {
				addDrop(ModBlocks.getBlock("raw_" + name + "_block"));
			}

			if (ModBlocks.hasBlock(name + "_ore")) {
				addDrop(ModBlocks.getBlock(name + "_ore"), oreDrops(ModBlocks.getBlock(name + "_ore"), mat.RAW));
			}
			if (ModBlocks.hasBlock("deepslate_" + name + "_ore")) {
				addDrop(ModBlocks.getBlock("deepslate_" + name + "_ore"), oreDrops(ModBlocks.getBlock("deepslate_" + name + "_ore"), mat.RAW));
			}
		}

		BlockStatePropertyLootCondition.Builder matureCropCondition = BlockStatePropertyLootCondition.builder(ModBlocks.PALADIUM_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(PaladiumCropBlock.AGE, PaladiumCropBlock.MAX_AGE));
		this.addDrop(ModBlocks.PALADIUM_CROP,
				this.cropDrops(ModBlocks.PALADIUM_CROP, ModItems.PALA_FLOWER, ModItems.PALADIUM_SEEDS, matureCropCondition));
	}
}
