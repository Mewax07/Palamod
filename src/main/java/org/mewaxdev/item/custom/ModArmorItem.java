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
		if(!world.isClient() && entity instanceof PlayerEntity player) {
			ItemStack helmet = player.getInventory().getArmorStack(3);
			if(helmet.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.HELMET) {
				player.addStatusEffect(new StatusEffectInstance(
						StatusEffects.NIGHT_VISION, 400, 0, false, false));
			}

			ItemStack boots = player.getInventory().getArmorStack(0);
			if(boots.getItem() instanceof ModArmorItem armorItem && armorItem.getType() == Type.BOOTS) {
				player.addStatusEffect(new StatusEffectInstance(
						StatusEffects.SPEED, 400, 0, false, false));
			}
		}

		super.inventoryTick(stack, world, entity, slot, selected);
	}
}