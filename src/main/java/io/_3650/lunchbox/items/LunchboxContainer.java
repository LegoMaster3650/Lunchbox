package io._3650.lunchbox.items;

import io._3650.lunchbox.mixin.SimpleContainerAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class LunchboxContainer extends SimpleContainer {
	
	public final ItemStack box;
	
	public LunchboxContainer(ItemStack box) {
		super(LunchboxItem.getInventoryRows(box));
		this.box = box;
		this.load();
	}
	
	public void load() {
		if (box.hasTag()) {
			ContainerHelper.loadAllItems(box.getTag(), ((SimpleContainerAccessor)this).getItems());
		}
	}
	
	public void save() {
		if (!box.isEmpty()) {
			CompoundTag tag = box.getOrCreateTag();
			ContainerHelper.saveAllItems(tag, ((SimpleContainerAccessor)this).getItems());
			box.setTag(tag);
		}
	}
	
}