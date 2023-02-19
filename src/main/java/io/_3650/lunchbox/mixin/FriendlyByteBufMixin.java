package io._3650.lunchbox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io._3650.lunchbox.items.LunchboxItem;
import io._3650.lunchbox.registry.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.CapabilityProvider;

@Mixin(FriendlyByteBuf.class)
public abstract class FriendlyByteBufMixin {
	
	@Inject(method = "writeItemStack(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/network/FriendlyByteBuf;", at = @At(value = "INVOKE", target = "net/minecraft/network/FriendlyByteBuf.writeNbt(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/network/FriendlyByteBuf;", shift = At.Shift.AFTER))
	private void lunchbox_writeItemStack(ItemStack stack, boolean limitedTag, CallbackInfoReturnable<FriendlyByteBuf> cir) {
		if (stack.getItem() instanceof LunchboxItem && Config.COMMON.enableNetworkCapabilities.get()) {
			FriendlyByteBuf buf = (FriendlyByteBuf) (Object) this; //we do a little trolling
			CompoundTag caps = ((CapabilityProviderInvoker)((CapabilityProvider<ItemStack>)stack)).callSerializeCaps();
			if (caps != null && !caps.isEmpty()) {
				buf.writeBoolean(true);
				buf.writeNbt(caps);
			} else buf.writeBoolean(false);
		}
	}
	
	@Inject(method = "readItem()Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "net/minecraft/world/item/ItemStack.readShareTag(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void lunchbox_readItem(CallbackInfoReturnable<ItemStack> cir, int i, int j, ItemStack itemstack) {
		if (itemstack.getItem() instanceof LunchboxItem && Config.COMMON.enableNetworkCapabilities.get()) {
			FriendlyByteBuf buf = (FriendlyByteBuf) (Object) this; //we do a little trolling part 2
			if (buf.readBoolean()) {
				CompoundTag caps = buf.readAnySizeNbt();
				((CapabilityProviderInvoker)((CapabilityProvider<ItemStack>)itemstack)).callDeserializeCaps(caps);
			}
		}
	}
	
}