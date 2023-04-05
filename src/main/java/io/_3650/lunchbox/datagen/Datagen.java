package io._3650.lunchbox.datagen;

import io._3650.lunchbox.Lunchbox;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lunchbox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		//server
		generator.addProvider(event.includeServer(), new DataRecipes(output));
		DataBlockTags blockTags = new DataBlockTags(output, event.getLookupProvider(), event.getExistingFileHelper());
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new DataItemTags(output, event.getLookupProvider(), blockTags, event.getExistingFileHelper()));
		//client
		generator.addProvider(event.includeClient(), new DataItemModels(output, event.getExistingFileHelper()));
		generator.addProvider(event.includeClient(), new DataBlockStates(output, event.getExistingFileHelper()));
	}
	
}