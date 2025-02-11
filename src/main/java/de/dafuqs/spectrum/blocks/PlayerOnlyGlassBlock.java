package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PlayerOnlyGlassBlock extends AbstractGlassBlock {

	// used for tinted glass to make light not shine through
	private final boolean tinted;

	public PlayerOnlyGlassBlock(Settings settings, boolean tinted) {
		super(settings);
		this.tinted = tinted;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if(context instanceof EntityShapeContext) {
			EntityShapeContext entityShapeContext = (EntityShapeContext) context;

			Entity entity = entityShapeContext.getEntity();
			if(entity instanceof PlayerEntity) {
				return VoxelShapes.empty();
			}
		}
		return state.getOutlineShape(world, pos);
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if(stateFrom.isOf(this)) {
			return true;
		}
		if(state.getBlock().equals(SpectrumBlocks.AMETHYST_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.AMETHYST_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.CITRINE_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.CITRINE_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.TOPAZ_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.TOPAZ_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.MOONSTONE_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.MOONSTONE_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.ONYX_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.ONYX_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.VANILLA_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(Blocks.GLASS)
				|| state.getBlock().equals(SpectrumBlocks.GLOWING_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(SpectrumBlocks.GLOWING_GLASS)
				|| state.getBlock().equals(SpectrumBlocks.TINTED_PLAYER_ONLY_GLASS) && stateFrom.getBlock().equals(Blocks.TINTED_GLASS)) {
			return true;
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}

	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return !tinted;
	}

	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		if(tinted) {
			return world.getMaxLightLevel();
		} else {
			return super.getOpacity(state, world, pos);
		}
	}

}
