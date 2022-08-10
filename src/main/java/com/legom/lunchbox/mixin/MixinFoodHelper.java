package com.legom.lunchbox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.legom.lunchbox.items.LunchboxItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import squeek.appleskin.api.food.FoodValues;
import squeek.appleskin.helpers.FoodHelper;

@Mixin(value = FoodHelper.class, remap = false)
public abstract class MixinFoodHelper {
	
	@Inject(at = @At("HEAD"), method = "isFood(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
	private static void lunchbox_isFood(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(true);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "canConsume(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z", cancellable = true)
	private static void lunchbox_canConsume(ItemStack stack, Player player, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(FoodHelper.canConsume(LunchboxItem.getTargetFood(stack), player));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getDefaultFoodValues(Lnet/minecraft/world/item/ItemStack;)Lsqueek/appleskin/api/food/FoodValues;", cancellable = true)
	private static void lunchbox_getDefaultFoodValues(ItemStack stack, CallbackInfoReturnable<FoodValues> cir) {
		if(stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(FoodHelper.getDefaultFoodValues(LunchboxItem.getTargetFood(stack)));
		}
	}
	
	/*
	@Inject(at = @At("HEAD"), method = "getModifiedFoodValues(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lsqueek/appleskin/api/food/FoodValues", cancellable = true)
	private static void lunchbox_getModifiedFoodValues(ItemStack stack, Player player, CallbackInfoReturnable<FoodValues> cir) {
		if (stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(FoodHelper.getModifiedFoodValues(LunchboxItem.getTargetFood(stack), player));
		}
	}
	*/
	
	@Inject(at = @At("HEAD"), method = "isRotten(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
	private static void lunchbox_isRotten(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(FoodHelper.isRotten(LunchboxItem.getTargetFood(stack)));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getEstimatedHealthIncrement(Lnet/minecraft/world/item/ItemStack;Lsqueek/appleskin/api/food/FoodValues;Lnet/minecraft/world/entity/player/Player;)F", cancellable = true)
	private static void lunchbox_getEstimatedHealthIncrement(ItemStack stack, FoodValues foodValues, Player player, CallbackInfoReturnable<Float> cir) {
		if (stack.getItem() instanceof LunchboxItem) {
			cir.setReturnValue(FoodHelper.getEstimatedHealthIncrement(LunchboxItem.getTargetFood(stack), foodValues, player));
		}
	}
	
}
