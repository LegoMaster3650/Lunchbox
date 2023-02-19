package io._3650.lunchbox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.CapabilityProvider;

@Mixin(CapabilityProvider.class)
public interface CapabilityProviderInvoker {
	
	@Invoker("serializeCaps")
	public abstract CompoundTag callSerializeCaps();
	
	@Invoker("deserializeCaps")
	public abstract void callDeserializeCaps(CompoundTag tag);
	
}
