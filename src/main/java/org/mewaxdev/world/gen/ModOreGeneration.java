package org.mewaxdev.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;

public class ModOreGeneration {
	public static void generateOres() {
		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			if (mat.ORE != null || mat.DEEPSLATE_ORE != null) {
				var gen = mat.options.getOreGenOptions();
				if (gen == null) continue;

				RegistryKey<PlacedFeature> placedKey =
						RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Palamod.MOD_ID, mat.name() + "_ore_placed"));

				BiomeModifications.addFeature(
						BiomeSelectors.foundInOverworld(),
						GenerationStep.Feature.UNDERGROUND_ORES,
						placedKey
				);
			}
		}
	}
}
