package org.mewaxdev.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModPlacedFeatures {
	public static final Map<String, RegistryKey<PlacedFeature>> ORE_KEYS = new HashMap<>();

	public static void bootstrap(Registerable<PlacedFeature> context) {
		var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			if (mat.ORE != null || mat.DEEPSLATE_ORE != null) {
				var gen = mat.options.getOreGenOptions();
				if (gen == null) continue;

				RegistryKey<ConfiguredFeature<?, ?>> configKey =
						RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Palamod.MOD_ID, mat.name() + "_ore"));

				RegistryKey<PlacedFeature> placedKey =
						RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Palamod.MOD_ID, mat.name() + "_ore_placed"));

				RegistryEntry<ConfiguredFeature<?, ?>> configEntry = configuredFeatures.getOrThrow(configKey);

				context.register(placedKey, new PlacedFeature(
						configEntry,
						ModOrePlacement.modifiersWithCount(gen.veinsPerChunk,
								HeightRangePlacementModifier.trapezoid(YOffset.fixed(gen.minY), YOffset.fixed(gen.maxY)))
				));

				ORE_KEYS.put(mat.name(), placedKey);
			}
		}
	}

	public static RegistryKey<PlacedFeature> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Palamod.MOD_ID, name));
	}

	private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
								 List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}

	private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
		register(context, key, configuration, List.of(modifiers));
	}
}
