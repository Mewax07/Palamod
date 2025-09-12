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
