package blocklim.blocklim.utils;

import com.flowpowered.math.vector.Vector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.function.Predicate;


public class Utils {

    public static Text strFormat(String string){
        return TextSerializers.FORMATTING_CODE.deserialize(string);
    }

    private static Vector3i getBlockLookAtPosition(Player player){
        Predicate<BlockRayHit<World>> filter = BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(),1);
        BlockRay<World> blockRay =
                BlockRay.from(player)
                        .distanceLimit(10)
                        .stopFilter(filter)
                        .build();

        Optional<BlockRayHit<World>> optionalWorldBlockRayHit = blockRay.end();

        if (optionalWorldBlockRayHit.isPresent()){
            BlockRayHit<World> blockRayHit = optionalWorldBlockRayHit.get();
            return blockRayHit.getBlockPosition();
        }else {
            return null;
        }
    }

    public static String getBlockNameAndMetaFromLookAt(Player player){
        int dimensionID = player.getWorld().getProperties().getAdditionalProperties().getInt(DataQuery.of("SpongeData", "dimensionId")).get();

        net.minecraft.world.World world = DimensionManager.getWorld(dimensionID);

        Vector3i vector3i = getBlockLookAtPosition(player);
        assert vector3i != null;
        BlockPos blockPos = new BlockPos(vector3i.getX(),vector3i.getY(),vector3i.getZ());
        IBlockState iBlockState = world.getBlockState(blockPos);
        try {
            ItemStack itemStack = iBlockState.getBlock().getPickBlock(iBlockState,null,world,blockPos,null);
            return itemStack.getItem().getRegistryName() + ":" + itemStack.getMetadata();
        }catch (Exception e){
            player.sendMessage(Text.of(TextColors.DARK_RED,"可能无法准确判断方块类型或meta！"));
            return iBlockState.getBlock().getRegistryName().toString() + ":" + iBlockState.getBlock().getMetaFromState(iBlockState);
        }
    }

}
