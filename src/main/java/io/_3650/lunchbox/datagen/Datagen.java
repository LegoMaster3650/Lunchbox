package io._3650.lunchbox.datagen;

import io._3650.lunchbox.Lunchbox;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Lunchbox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			generator.addProvider(new DataRecipes(generator));
			DataBlockTags blockTags = new DataBlockTags(generator, event.getExistingFileHelper());
			generator.addProvider(blockTags);
			generator.addProvider(new DataItemTags(generator, blockTags, event.getExistingFileHelper()));
		}
		if (event.includeClient()) {
			generator.addProvider(new DataItemModels(generator, event.getExistingFileHelper()));
			generator.addProvider(new DataBlockStates(generator, event.getExistingFileHelper()));
		}
	}
	
}