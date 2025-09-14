package org.mewaxdev.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class ModArmorItem extends ArmorItem {
	public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
		super(material, type, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient() && entity instanceof PlayerEntity player) {
			ItemStack helmet = player.getInventory().getArmorStack(3);
			if (helmet.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.HELMET) {
				String helmetName = helmet.getItem().toString();

				if (helmetName.contains("paladium") || helmetName.contains("green_paladium") || helmetName.contains("endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 1, false, false));
				}
				if (helmetName.contains("mixed_endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false));
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 400, 0, false, false));
				}
			}

			ItemStack chestplate = player.getInventory().getArmorStack(2);
			if (chestplate.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.CHESTPLATE) {
				String chestName = chestplate.getItem().toString();

				if (chestName.contains("endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 400, 0, false, false));
				}
				if (chestName.contains("mixed_endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0, false, false));
				}
			}

			ItemStack leggings = player.getInventory().getArmorStack(1);
			if (leggings.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.LEGGINGS) {
				String legName = leggings.getItem().toString();

				if (legName.contains("green_paladium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400, 0, false, false));
				}
				if (legName.contains("mixed_endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400, 1, false, false));
				}
			}

			ItemStack boots = player.getInventory().getArmorStack(0);
			if (boots.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.BOOTS) {
				String bootsName = boots.getItem().toString();

				if (bootsName.contains("paladium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 0, false, false));
				}
				if (bootsName.contains("green_paladium") || bootsName.contains("endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 1, false, false));
				}
				if (bootsName.contains("mixed_endium")) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 1, false, false));
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 400, 0, false, false));
				}
			}
		}

		super.inventoryTick(stack, world, entity, slot, selected);
	}
}
