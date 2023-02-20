package io._3650.lunchbox.blocks;

import javax.annotation.Nullable;

import io._3650.lunchbox.blocks.entity.LunchboxBlockEntity;
import io._3650.lunchbox.registry.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkHooks;

public class LunchboxBlock extends Block implements EntityBlock {
	
	private final DyeColor color;
	
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	
	private static final VoxelShape SHAPE_X = Shapes.box(.25, 0, .125, .75, .5, .875);
	private static final VoxelShape SHAPE_Z = Shapes.box(.125, 0, .25, .875, .5, .75);
	
	public LunchboxBlock(Properties properties, @Nullable DyeColor color) {
		super(properties.sound(SoundType.METAL).instabreak().noOcclusion());
		this.color = color;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(BlockStateProperties.FACING).getAxis() == Axis.Z ? SHAPE_Z : SHAPE_X;
	}
	
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, OPEN);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof LunchboxBlockEntity lunchboxBE) {
			lunchboxBE.loadAllData(stack);
		}
	}
	
	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof LunchboxBlockEntity lunchboxBE && !level.isClientSide && !player.getAbilities().instabuild) {
			ItemStack newItem = lunchboxBE.makeItem(asItem());
			if (!(player instanceof FakePlayer) && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && Config.SERVER.lunchboxInstantPickup.get()) {
				player.setItemInHand(InteractionHand.MAIN_HAND, newItem);
			} else {
				ItemEntity droppedItem = new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, newItem);
				level.addFreshEntity(droppedItem);
			}
		}
		
		super.playerWillDestroy(level, pos, state, player);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
		if (!level.isClientSide) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof LunchboxBlockEntity) {
				LunchboxBlockEntity lunchboxBE = (LunchboxBlockEntity)blockEntity;
				NetworkHooks.openGui((ServerPlayer) player, lunchboxBE, pos);
				lunchboxBE.startOpen(player);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LunchboxBlockEntity(pos, state, this.getColor());
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static DyeColor getColorFromBlock(Block block) {
		return block instanceof LunchboxBlock lunchboxBlock ? lunchboxBlock.getColor() : null;
	}
	
}