package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

public class ParticleSpawnerScreenHandler extends ScreenHandler {

   protected final ScreenHandlerContext context;
   protected final PlayerEntity player;
   protected ParticleSpawnerBlockEntity particleSpawnerBlockEntity;

   public ParticleSpawnerScreenHandler(int syncId, PlayerInventory inventory) {
	  this(syncId, inventory, ScreenHandlerContext.EMPTY);
   }

   public ParticleSpawnerScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
	  super(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, syncId);
	  this.context = context;
	  this.player = playerInventory.player;
   }

	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory inv, ParticleSpawnerBlockEntity particleSpawnerBlockEntity) {
		this(syncId, inv);
		this.particleSpawnerBlockEntity = particleSpawnerBlockEntity;
	}

	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
		this(syncId, playerInventory, packetByteBuf.readBlockPos());
	}

	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos readBlockPos) {
		super(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, syncId);
		this.player = playerInventory.player;
		this.context = null;
		BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(readBlockPos);
		if(blockEntity instanceof ParticleSpawnerBlockEntity particleSpawnerBlockEntity) {
			this.particleSpawnerBlockEntity = particleSpawnerBlockEntity;
		}
	}

	public boolean canUse(PlayerEntity player) {
	  return this.context.get((world, pos) -> player.squaredDistanceTo((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D, true);
   }
   
   public ParticleSpawnerBlockEntity getBlockEntity() {
	   return this.particleSpawnerBlockEntity;
   }

}
