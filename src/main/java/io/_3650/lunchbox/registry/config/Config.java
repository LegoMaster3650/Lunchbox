package io._3650.lunchbox.registry.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Config {
	
	public static class Server {
		
		public final BooleanValue lunchboxInstantPickup;
		
		Server(ForgeConfigSpec.Builder builder) {
			builder.push("lunchboxes");
			
			lunchboxInstantPickup = builder.comment("Lunchboxes instantly go into your hand if broken while holding nothing").define("lunchboxInstantPickup", true);
			
			builder.pop();
		}
		
	}
	
	public static class Common {
		
		public final BooleanValue enableNetworkCapabilities;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Slightly invasive patches that you may want to configure").push("patches");
			
			enableNetworkCapabilities = builder.comment(
					"Serializes item capabilities over network using a mixin.",
					"This is necessary for the items to properly synchronize between client & server.",
					"Only does this for Lunchbox items",
					"WARNING: If you disable this on a server, make sure to give the updated file to all players"
					).define("enableNetworkCapabilities", true);
			
			builder.pop();
		}
	}
	
	public static class Client {
		
		public final BooleanValue lunchboxItemPreview;
		public final BooleanValue itemPreviewMainhand;
		public final BooleanValue itemPreviewOffhand;
		public final IntValue itemPreviewYOffset;
		public final IntValue offhandPreviewXOffset;
		public final IntValue mainhandPreviewMode;
		public final IntValue centerPreviewYOffset;
		public final IntValue followPreviewYOffset;
		
		public final BooleanValue enableLunchboxTooltips;
		
		Client(ForgeConfigSpec.Builder builder) {
			builder.push("lunchboxes");
			
			builder.push("itemPreview");
			lunchboxItemPreview = builder.comment("Lunchboxes show the currently selected food when held","[Default: true]").define("lunchboxItemPreview", true);
			itemPreviewMainhand = builder.comment("Shows the item preview when held in the main hand","[Default: true]").define("itemPreviewMainhand", true);
			itemPreviewOffhand = builder.comment("Shows the item preview when held in the offhand","[Default: true]").define("itemPreviewOffhand", true);
			itemPreviewYOffset = builder.comment("Vertical offset of the item preview (+UP,-DOWN)", "Useful for aligning with mods that raise the hotbar","[Default: 0]").defineInRange("itemPreviewYOffset", 0, -100, 200);
			offhandPreviewXOffset = builder.comment("Horizontal offset of the item preview in the offhand position (+FURTHER,-CLOSER)", "Useful for aligning with mods that move the offhand", "[Default: 0]").defineInRange("offhandPreviewXOffset", 0, -100, 200);
			mainhandPreviewMode = builder.comment("How the main hand item preview shows",
												  "0 - CENTER  - The preview appears in the center of the hotbar",
												  "1 - OFFHAND - The preview appears in the opposite position of when in the offhand",
												  "2 - FOLLOW  - The preview appears above the selected hotbar slot",
												  "3 - OVERLAY - The preview appears ON the selected hotbar slot",
												  "[Default: 1 (OFFHAND)]").defineInRange("mainhandPreviewMode", 1, 0, 3);
			centerPreviewYOffset = builder.comment("Vertical offset when in CENTER (0) main hand preview mode (+UP,-DOWN)", "[Default: 14]").defineInRange("centerPreviewYOffset", 14, -100, 200);
			followPreviewYOffset = builder.comment("Vertical offset when in FOLLOW (2) main hand preview mode (+UP,-DOWN)", "[Default: 17]").defineInRange("followPreviewYOffset", 17, -100, 200);
			builder.pop();
			
			enableLunchboxTooltips = builder.comment("Enable colored lunchbox tooltips", "[Default: true]").define("enableLunchboxTooltips", true);
			
			builder.pop();
		}
		
	}
	
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final Server SERVER;
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;
	
	static {
		final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
		
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
		
		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
	
}