package org.mewaxdev.trim;

import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.mewaxdev.Palamod;
import org.mewaxdev.item.ModItems;

import java.util.Map;

public class ModTrimMaterials {
	private static final Map<String, String> MATERIAL_COLORS = Map.of(
			"paladium", "#b03fe0",
			"endium", "#00ffcc"
	);

	public static void bootstrap(Registerable<ArmorTrimMaterial> registerable) {
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			String name = mat.name();

			RegistryEntry<Item> itemEntry = Registries.ITEM.getEntry(mat.INGOT);

			Style style = Style.EMPTY;
			if (MATERIAL_COLORS.containsKey(name)) {
				style = Style.EMPTY.withColor(TextColor.parse(MATERIAL_COLORS.get(name)).getOrThrow());
			}

			// Register le trim
			register(registerable,
					RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(Palamod.MOD_ID, name)),
					itemEntry,
					style,
					1.0f
			);
		}
	}

	public static void register(Registerable<ArmorTrimMaterial> registerable, RegistryKey<ArmorTrimMaterial> armorTrimKey,
								RegistryEntry<Item> item, Style style, float itemModelIndex) {

		ArmorTrimMaterial trimMaterial = new ArmorTrimMaterial(
				armorTrimKey.getValue().getPath(),
				item,
				itemModelIndex,
				Map.of(),
				Text.translatable(Util.createTranslationKey("trim_material", armorTrimKey.getValue())).fillStyle(style)
		);

		registerable.register(armorTrimKey, trimMaterial);
	}
}
