package cn.royan.lithiumraycastfix.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = World.class, priority = 2147483647) //:)
public abstract class WorldMixin implements HeightLimitView {
    private static final BlockState OUTSIDE_WORLD_BLOCK = Blocks.VOID_AIR.getDefaultState();
    private static final BlockState INSIDE_WORLD_DEFAULT_BLOCK = Blocks.AIR.getDefaultState();

    @Shadow
    public abstract WorldChunk getChunk(int i, int j);

    /**
     * @reason Add HeightLimit check
     * @author AB
     */
    @Overwrite
    public BlockState getBlockState(BlockPos pos) {
        if (this.isOutOfHeightLimit(pos.getY())) {
            return Blocks.VOID_AIR.getDefaultState();
        }
        WorldChunk worldChunk = this.getChunk(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
        ChunkSection[] sections = worldChunk.getSectionArray();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int chunkY = this.getSectionIndex(y);
        if (chunkY < 0 || chunkY >= sections.length) {
            return OUTSIDE_WORLD_BLOCK;
        }

        ChunkSection section = sections[chunkY];
        if (section == null || section.isEmpty()) {
            return INSIDE_WORLD_DEFAULT_BLOCK;
        }
        return section.getBlockState(x & 15, y & 15, z & 15);
    }
}
