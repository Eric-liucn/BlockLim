package blocklim.blocklim.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
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

    public static String getBlockStateIdFromLookAt(Player player){
        Predicate<BlockRayHit<World>> filter = BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(),1);
        BlockRay<World> blockRay =
                BlockRay.from(player)
                        .distanceLimit(10)
                        .stopFilter(filter)
                        .build();

        Optional<BlockRayHit<World>> optionalWorldBlockRayHit = blockRay.end();
        if (optionalWorldBlockRayHit.isPresent()){
            BlockRayHit<World> blockRayHit = optionalWorldBlockRayHit.get();
            return blockRayHit.getExtent().getBlock(blockRayHit.getBlockPosition()).getId();
        }else {
            return "&4查询失败";
        }
    }
}
