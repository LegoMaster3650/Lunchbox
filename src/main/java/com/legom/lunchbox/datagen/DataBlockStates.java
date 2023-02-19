package com.legom.lunchbox.datagen;

import javax.annotation.Nullable;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.registry.ModBlocks;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataBlockStates extends BlockStateProvider {
	
	public DataBlockStates(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Lunchbox.MOD_ID, helper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		createLunchbox(ModBlocks.LUNCHBOX.get(), "lunchbox", null);
		createLunchbox(ModBlocks.WHITE_LUNCHBOX.get(), "white_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.ORANGE_LUNCHBOX.get(), "orange_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.MAGENTA_LUNCHBOX.get(), "magenta_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.LIGHT_BLUE_LUNCHBOX.get(), "light_blue_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.YELLOW_LUNCHBOX.get(), "yellow_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.LIME_LUNCHBOX.get(), "lime_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.PINK_LUNCHBOX.get(), "pink_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.GRAY_LUNCHBOX.get(), "gray_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.LIGHT_GRAY_LUNCHBOX.get(), "light_gray_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.CYAN_LUNCHBOX.get(), "cyan_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.PURPLE_LUNCHBOX.get(), "purple_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.BLUE_LUNCHBOX.get(), "blue_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.BROWN_LUNCHBOX.get(), "brown_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.GREEN_LUNCHBOX.get(), "green_lunchbox", "lunchbox");
		createLunchbox(ModBlocks.RED_LUNCHBOX.get(), "red_lunchbox", null);
		createLunchbox(ModBlocks.BLACK_LUNCHBOX.get(), "black_lunchbox", "lunchbox");
	}
	
	private void createLunchbox(Block block, String name, @Nullable String parentModel) {
		//Generate models if needed
		ResourceLocation baseRL = modLoc("block/" + name);
		ResourceLocation openRL = modLoc("block/" + name + "_open");
		ModelFile closedModel;
		ModelFile openModel;
		if (parentModel == null || name.equals(parentModel)) {
			closedModel = models().getExistingFile(baseRL);
			openModel = models().getExistingFile(openRL);
		} else {
			BlockModelBuilder closedFrame = models().getBuilder("block/" + name);
			BlockModelBuilder openFrame = models().getBuilder("block/" + name + "_open");
			closedFrame.parent(models().getExistingFile(modLoc("block/" + parentModel)));
			openFrame.parent(models().getExistingFile(modLoc("block/" + parentModel + "_open")));
			closedFrame.texture("0", baseRL);
			closedFrame.texture("particle", baseRL);
			openFrame.texture("0", baseRL);
			openFrame.texture("particle", baseRL);
			closedModel = closedFrame;
			openModel = openFrame;
		}
		
		//Build blockstates
		VariantBlockStateBuilder builder = getVariantBuilder(block);
		builder.forAllStates(state -> {
			Direction dir = state.getValue(BlockStateProperties.FACING);
			boolean open = state.getValue(BlockStateProperties.OPEN);
			return ConfiguredModel.builder()
					.modelFile(open ? openModel : closedModel)
					.rotationX(0)
					.rotationY(dir.getAxis().isVertical() ? 0 : ((int)dir.toYRot() + 180) % 360)
					.build();
		});
	}
	
}