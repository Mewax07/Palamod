package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter exporter) {
		List<ItemConvertible> PALADIUM_SMELTABLES = List.of(ModItems.RAW_PALADIUM, ModBlocks.PALADIUM_ORE, ModBlocks.DEEPSLATE_PALADIUM_ORE);

		offerSmelting(exporter, PALADIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PALADIUM_INGOT, 0.25f, 200, "paladium_ingot");
		offerBlasting(exporter, PALADIUM_SMELTABLES, RecipeCategory.MISC, ModItems.PALADIUM_INGOT, 0.5f, 100, "paladium_ingot");

		offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PALADIUM_INGOT, RecipeCategory.DECORATIONS, ModBlocks.PALADIUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_PALADIUM, RecipeCategory.DECORATIONS, ModBlocks.RAW_PALADIUM_BLOCK);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PALADIUM_CHISEL)
				.pattern(" P")
				.pattern("S ")
				.input('P', ModItems.PALADIUM_INGOT)
				.input('S', Items.STICK)
				.criterion(hasItem(ModItems.PALADIUM_INGOT), conditionsFromItem(ModItems.PALADIUM_INGOT))
				.criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
				.offerTo(exporter);
	}
}
