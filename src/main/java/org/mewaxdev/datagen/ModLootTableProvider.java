package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.block.custom.ModCropBlock;
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

			Item withoutSilkTouch = mat.RAW != null ? mat.RAW : mat.INGOT;
			if (ModBlocks.hasBlock(name + "_ore")) {
				addDrop(ModBlocks.getBlock(name + "_ore"), oreDrops(ModBlocks.getBlock(name + "_ore"), withoutSilkTouch));
			}
			if (ModBlocks.hasBlock("deepslate_" + name + "_ore")) {
				addDrop(ModBlocks.getBlock("deepslate_" + name + "_ore"), oreDrops(ModBlocks.getBlock("deepslate_" + name + "_ore"), withoutSilkTouch));
			}
		}

		ModCropBlock kiwanoCrop = (ModCropBlock) ModBlocks.KIWANO_CROP;
		BlockStatePropertyLootCondition.Builder matureCropConditionKiwano = BlockStatePropertyLootCondition.builder(ModBlocks.KIWANO_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(kiwanoCrop.getAgeProperty(), kiwanoCrop.getMaxAge()));
		this.addDrop(ModBlocks.KIWANO_CROP,
				this.cropDrops(ModBlocks.KIWANO_CROP, ModItems.KIWANO, ModItems.KIWANO_SEEDS, matureCropConditionKiwano));

		ModCropBlock orangeblueCrop = (ModCropBlock) ModBlocks.ORANGEBLUE_CROP;
		BlockStatePropertyLootCondition.Builder matureCropConditionOrangeblue = BlockStatePropertyLootCondition.builder(ModBlocks.ORANGEBLUE_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(orangeblueCrop.getAgeProperty(), orangeblueCrop.getMaxAge()));
		this.addDrop(ModBlocks.ORANGEBLUE_CROP,
				this.cropDrops(ModBlocks.ORANGEBLUE_CROP, ModItems.ORANGEBLUE, ModItems.ORANGEBLUE_SEEDS, matureCropConditionOrangeblue));
	}
}
