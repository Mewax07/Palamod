package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
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
		List<ModItems.MaterialItems> materials = ModItems.getAllMaterials();

		for (ModItems.MaterialItems mat : materials) {
			String name = getMaterialName(mat);

			if (mat.RAW != null) {
				offerSmelting(exporter, List.of(mat.RAW), RecipeCategory.MISC, mat.INGOT, 0.25f, 200, name + "_ingot");
				offerBlasting(exporter, List.of(mat.RAW), RecipeCategory.MISC, mat.INGOT, 0.5f, 100, name + "_ingot");
			}

			if (ModBlocks.hasBlock(name + "_block")) {
				offerReversibleCompactingRecipes(exporter,
						RecipeCategory.BUILDING_BLOCKS, mat.INGOT,
						RecipeCategory.DECORATIONS, ModBlocks.getBlock(name + "_block"));
			}

			if (mat.RAW != null && ModBlocks.hasBlock("raw_" + name + "_block")) {
				offerReversibleCompactingRecipes(exporter,
						RecipeCategory.BUILDING_BLOCKS, mat.RAW,
						RecipeCategory.DECORATIONS, ModBlocks.getBlock("raw_" + name + "_block"));
			}

			if (mat.SWORD != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.SWORD)
						.pattern(" P ")
						.pattern(" P ")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.BROAD_SWORD != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.BROAD_SWORD)
						.pattern(" PP")
						.pattern(" PP")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.PICKAXE != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.PICKAXE)
						.pattern("PPP")
						.pattern(" S ")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.AXE != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.AXE)
						.pattern("PP ")
						.pattern("PS ")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.SHOVEL != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.SHOVEL)
						.pattern(" P ")
						.pattern(" S ")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.HOE != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.HOE)
						.pattern("PP ")
						.pattern(" S ")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.HAMMER != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, mat.HAMMER)
						.pattern("PPP")
						.pattern("PPP")
						.pattern(" S ")
						.input('P', mat.INGOT)
						.input('S', Items.STICK)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
						.offerTo(exporter);
			}

			if (mat.HELMET != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, mat.HELMET)
						.pattern("PPP")
						.pattern("P P")
						.input('P', mat.INGOT)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.CHESTPLATE != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, mat.CHESTPLATE)
						.pattern("P P")
						.pattern("PPP")
						.pattern("PPP")
						.input('P', mat.INGOT)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.LEGGINGS != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, mat.LEGGINGS)
						.pattern("PPP")
						.pattern("P P")
						.pattern("P P")
						.input('P', mat.INGOT)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}

			if (mat.BOOTS != null) {
				ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, mat.BOOTS)
						.pattern("P P")
						.pattern("P P")
						.input('P', mat.INGOT)
						.criterion(hasItem(mat.INGOT), conditionsFromItem(mat.INGOT))
						.offerTo(exporter);
			}
		}

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PALADIUM_CHISEL)
				.pattern(" P")
				.pattern("S ")
				.input('P', ModItems.PALADIUM.INGOT)
				.input('S', Items.STICK)
				.criterion(hasItem(ModItems.PALADIUM.INGOT), conditionsFromItem(ModItems.PALADIUM.INGOT))
				.criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
				.offerTo(exporter);
	}

	private String getMaterialName(ModItems.MaterialItems mat) {
		return Registries.ITEM.getId(mat.INGOT).getPath().replace("_ingot", "");
	}
}
