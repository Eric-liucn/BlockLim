package blocklim.blocklim.listener;

import blocklim.blocklim.Main;
import com.flowpowered.math.vector.Vector3i;
import jdk.nashorn.internal.ir.Block;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.World;


public class BlockPlaceEvent {

    @Listener
    public void blockPlaceEvent(ChangeBlockEvent.Place event){
        if (event.getSource() instanceof Player)
        {
            for (Transaction<BlockSnapshot> transaction:event.getTransactions()){
                String blockId = getBlockNameFromTransaction(transaction);
                World world = Sponge.getServer().getWorld(transaction.getFinal().getWorldUniqueId()).get();
                Chunk chunk = world.getChunk(transaction.getFinal().getLocation().get().getChunkPosition()).get();
                if (!((Player) event.getSource()).hasPermission("blocklimit.bypass")){
                    int unsafeDamage = getUnsafeDamage(transaction.getFinal().getState());
                    Main.getLogger().info(String.format("你放置了一个"+blockId+",他的unsafedamge是"+unsafeDamage));
                }
            }
        }
    }

    private static String getBlockNameFromTransaction(Transaction<BlockSnapshot> transaction){
        return transaction.getFinal().getState().getType().getId();
    }

    private static int getCountOfBlock(String blockId,Chunk chunk){
        int count = 0;
        Vector3i max = chunk.getBlockMax();
        Vector3i min = chunk.getBlockMin();
        for (int x = min.getX(); x < max.getX() ; x++) {
            for (int y = min.getY(); y < max.getY();y++){
                for (int z = min.getZ(); z < max.getZ(); z++){
                    BlockState blockState = chunk.getBlock(x,y,z);
                    if (blockState.getType().getId().equals(blockId)){
                        count+=1;
                    }

                }
            }
        }
        return count;
    }

    private static int getUnsafeDamage(BlockState blockState){
        if (blockState.toContainer().getInt(DataQuery.of("UnsafeData")).isPresent()){
            return (int) blockState.toContainer().getInt(DataQuery.of("UnsafeData")).get();
        }else{
            return 0;
        }
    }
}
