package io._3650.lunchbox.menus;

import javax.annotation.Nullable;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.items.LunchboxContainer;
import io._3650.lunchbox.items.LunchboxItem;
import io._3650.lunchbox.registry.ModMenus;
import io._3650.lunchbox.registry.Reference;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class LunchboxSelectorMenu extends AbstractContainerMenu {
	
	private final int boxRows;
	
	private final ItemStack boxItem;
	private final int selectedSlot;
	private final InteractionHand hand;
	
	private final DyeColor color;
	
	public LunchboxSelectorMenu(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, data.readInt(), data.readInt(), InteractionHand.values()[data.readInt()], LunchboxItem.deserialzeDyeColor(data.readInt()));
	}
	
	public LunchboxSelectorMenu(int windowId, Inventory playerInv, int selSlot, int rows, InteractionHand selHand, @Nullable DyeColor color) {
		super(ModMenus.LUNCHBOX_SELECTOR.get(), windowId);
		this.boxRows = rows;
		
		this.selectedSlot = selSlot;
		this.hand = selHand;
		this.boxItem = playerInv.player.getItemInHand(hand);
		
		this.color = color;
		
		if (!(boxItem.getItem() instanceof LunchboxItem) && playerInv.player.level.isClientSide) {
			playerInv.player.closeContainer();
		} else {
			LunchboxContainer container = LunchboxItem.getContainer(boxItem);
			
			for (int i = 0; i < boxRows; i++) {
				for (int j = 0; j < 9; j++) {
					this.addSlot(new LunchboxSelectorSlot(container, i * 9 + j, 18 * j + 8, 18 * i + 18));
				}
			}
			
			Lunchbox.playClientSideSound(playerInv.player.level, Reference.LUNCHBOX_OPEN);
		}
	}
	
	@Override
	public void clicked(int slotNum, int button, ClickType clickType, Player player) {
		try {
			this.doClick(slotNum, button, clickType, player);
		} catch (Exception exception) { //forced to copy vanilla's crash report code here because I want crash reports and I just override private methods and I want smh java
			CrashReport crashreport = CrashReport.forThrowable(exception, "Container click");
			CrashReportCategory crashreportcategory = crashreport.addCategory("Click info");
			crashreportcategory.setDetail("Menu Type", () -> {
				return ModMenus.LUNCHBOX_SELECTOR.getKey().toString();
			});
			crashreportcategory.setDetail("Menu Class", () -> {
				return this.getClass().getCanonicalName();
			});
			crashreportcategory.setDetail("Slot Count", this.slots.size());
			crashreportcategory.setDetail("Slot", slotNum);
			crashreportcategory.setDetail("Button", button);
			crashreportcategory.setDetail("Type", clickType);
			throw new ReportedException(crashreport);
		}
	}
	
	private void doClick(int slotNum, int button, ClickType clickType, Player player) {
		if (clickType == ClickType.PICKUP) {
			Slot slot = this.slots.get(slotNum);
			if (slot instanceof LunchboxSelectorSlot) {
				LunchboxItem.setTargetFoodSlot(boxItem, slotNum);
				Lunchbox.playClientSideSound(player.level, SoundEvents.UI_BUTTON_CLICK.get());
			}
		}
	}
	
	@Override
	public void removed(Player player) {
		Lunchbox.playClientSideSound(player.level, Reference.LUNCHBOX_CLOSE);
		super.removed(player);
	}
	
	@Override
	public boolean stillValid(Player player) {
		return selectedSlot == player.getInventory().selected && player.getItemInHand(hand) == boxItem;
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return this.slots.get(index).getItem();
	}
	
	public int getBoxRows() {
		return boxRows;
	}
	
	public int getTargetSlot() {
		return LunchboxItem.getTargetFoodSlot(boxItem);
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
}