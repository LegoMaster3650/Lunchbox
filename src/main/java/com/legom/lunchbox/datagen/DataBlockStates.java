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
	}
	
	private void createLunchbox(Block block, String name, @Nullable String parentModel) {
		//Generate models if needed
		ResourceLocation baseRL = modLoc("block/".concat(name));
		ResourceLocation openRL = modLoc("block/".concat(name).concat("_open"));
		ModelFile closedModel;
		ModelFile openModel;
		if (parentModel == null || name.equals(parentModel)) {
			closedModel = models().getExistingFile(baseRL);
			openModel = models().getExistingFile(openRL);
		} else {
			BlockModelBuilder closedFrame = models().getBuilder("block/".concat(name));
			BlockModelBuilder openFrame = models().getBuilder("block/".concat(name).concat("_open"));
			closedFrame.parent(models().getExistingFile(modLoc("block/".concat(parentModel))));
			openFrame.parent(models().getExistingFile(modLoc("block/".concat(parentModel).concat("_open"))));
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