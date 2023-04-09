package io._3650.lunchbox.blocks;

import java.util.Optional;

import javax.annotation.Nullable;

import io._3650.lunchbox.blocks.entity.LunchboxBlockEntity;
import io._3650.lunchbox.registry.ModBlockEntities;
import io._3650.lunchbox.registry.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkHooks;

public class LunchboxBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
	
	private final DyeColor color;
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	
	private static final VoxelShape SHAPE_X = Shapes.box(.25, 0, .125, .75, .5, .875);
	private static final VoxelShape SHAPE_Z = Shapes.box(.125, 0, .25, .875, .5, .75);
	
	public LunchboxBlock(@Nullable DyeColor color) {
		super(Properties.of(Material.METAL, color == null ? MaterialColor.METAL : color.getMaterialColor()).sound(SoundType.METAL).instabreak().explosionResistance(4.0F).noOcclusion());
		this.color = color;
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(OPEN, false));
	}
	
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED, FACING, OPEN);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		boolean isWater = level.getFluidState(pos).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(OPEN, level.hasNeighborSignal(pos)).setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, isWater);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		
		return super.updateShape(state, direction, neighborState, level, pos, pos);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(BlockStateProperties.FACING).getAxis() == Axis.Z ? SHAPE_Z : SHAPE_X;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) {
			lunchboxBE.loadAllData(stack);
		}
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
		Optional<LunchboxBlockEntity> lunchboxBE = level.getBlockEntity(pos, ModBlockEntities.LUNCHBOX.get());
		return lunchboxBE.isPresent() ? lunchboxBE.get().makeItem(asItem()) : super.getCloneItemStack(state, target, level, pos, player);
	}
	
	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE && !level.isClientSide && !player.getAbilities().instabuild) {
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
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		if (!level.isClientSide && level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) {
			ItemStack newItem = lunchboxBE.makeItem(asItem());
			ItemEntity droppedItem = new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, newItem);
			level.addFreshEntity(droppedItem);
		}
		super.onBlockExploded(state, level, pos, explosion);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
		if (!level.isClientSide) {
			if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) {
				NetworkHooks.openScreen((ServerPlayer) player, lunchboxBE, pos);
				lunchboxBE.startOpen(player);
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			boolean isOpen = state.getValue(OPEN);
			if (isOpen != level.hasNeighborSignal(pos)) {
				if (isOpen) tryCloseState(level, pos, state);
				else tryOpenState(level, pos, state);
			}
		}
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) {
			lunchboxBE.recheckOpen();
		}
	}
	
	private void tryOpenState(Level level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) lunchboxBE.tryOpenState(level, pos, state);
	}
	
	private void tryCloseState(Level level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof LunchboxBlockEntity lunchboxBE) lunchboxBE.tryCloseState(level, pos, state);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LunchboxBlockEntity(pos, state, this.getColor());
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static DyeColor getColorFromBlock(Block block) {
		return block instanceof LunchboxBlock lunchboxBlock ? lunchboxBlock.getColor() : null;
	}
	
}