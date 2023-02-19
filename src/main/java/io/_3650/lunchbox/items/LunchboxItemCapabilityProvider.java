package io._3650.lunchbox.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io._3650.lunchbox.registry.Reference;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LunchboxItemCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final int invRows;
	private final ItemStackHandler itemHandler;
	private final LazyOptional<ItemStackHandler> handler;
	
	public LunchboxItemCapabilityProvider() {
		this.invRows = Reference.lunchboxRows;
		this.itemHandler = createHandler(invRows);
		this.handler = LazyOptional.of(() -> this.itemHandler);
	}
	
	private ItemStackHandler createHandler(int rows) {
		return new ItemStackHandler(rows * 9) {
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return LunchboxItem.checkItemValid(stack) && super.isItemValid(slot, stack);
			}
			
			@Override
			protected void onContentsChanged(int slot) {
				
			}
		};
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		if (handler.isPresent()) {
			return handler.resolve().get().serializeNBT();
		}
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		handler.ifPresent(h -> h.deserializeNBT(nbt));
	}
	
	
	
}