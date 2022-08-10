package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	
	public static final TagKey<Item> LUNCHBOX = ItemTags.create(new ResourceLocation(Lunchbox.MOD_ID, "lunchboxes"));
	public static final TagKey<Item> LUNCHBOX_WHITELIST = ItemTags.create(new ResourceLocation(Lunchbox.MOD_ID, "lunchbox_whitelisted"));
	public static final TagKey<Item> LUNCHBOX_BLACKLIST = ItemTags.create(new ResourceLocation(Lunchbox.MOD_ID, "lunchbox_blacklisted"));
	//For hardcoded "always edible" food like *honey bottles*
	public static final TagKey<Item> FORCE_ALWAYS_EAT = ItemTags.create(new ResourceLocation(Lunchbox.MOD_ID, "force_always_eat"));
	
}