package org.mewaxdev.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModConfiguredFeatures {
	public static final Map<String, RegistryKey<ConfiguredFeature<?, ?>>> ORE_KEYS = new HashMap<>();

	public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplaceable = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplaceable = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			if (mat.ORE != null || mat.DEEPSLATE_ORE != null) {
				var gen = mat.options.getOreGenOptions();
				if (gen == null) continue;

				List<OreFeatureConfig.Target> targets = new ArrayList<>();
				if (mat.ORE != null) {
					targets.add(OreFeatureConfig.createTarget(stoneReplaceable, mat.ORE.getDefaultState()));
				}
				if (mat.DEEPSLATE_ORE != null) {
					targets.add(OreFeatureConfig.createTarget(deepslateReplaceable, mat.DEEPSLATE_ORE.getDefaultState()));
				}

				RegistryKey<ConfiguredFeature<?, ?>> key = registryKey(mat.name() + "_ore");

				context.register(key, new ConfiguredFeature<>(
						Feature.ORE,
						new OreFeatureConfig(targets, gen.veinSize(), 0.5f)
				));

				ORE_KEYS.put(mat.name(), key);
			}
		}
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(String name) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Palamod.MOD_ID, name));
	}
}
