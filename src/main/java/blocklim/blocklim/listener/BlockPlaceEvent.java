package blocklim.blocklim.listener;

import blocklim.blocklim.config.RuleMap;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Map;


public class BlockPlaceEvent {

    private Map<String,Integer> ruleMap = RuleMap.getRuleMap();

    @SubscribeEvent
    public void forgeBlockPlaceEvent(BlockEvent.EntityPlaceEvent event){
        if (event.getEntity() instanceof EntityPlayer){
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntity();
            String nameAndMeta = getBlockNameAndMetaFromIBlockState(event.getPlacedBlock(),event.getWorld(),event.getPos(),entityPlayer);
            int getCount = countBlockInThisChunk(event.getWorld(),event.getPos(),nameAndMeta);
            ITextComponent iTextComponent =new TextComponentString("当前区块有"+getCount);
            entityPlayer.sendMessage(iTextComponent);
            if (ruleMap.containsKey(nameAndMeta)){
                int limit = ruleMap.get(nameAndMeta);
            }
        }
    }

    private static String getBlockNameAndMetaFromIBlockState(IBlockState iBlockState, World world, BlockPos pos, EntityPlayer player){
        ItemStack pickBlock;
        if (player!=null) {
            pickBlock = iBlockState.getBlock().getPickBlock(iBlockState, null, world, pos, player);
        }else {
            pickBlock = iBlockState.getBlock().getPickBlock(iBlockState, null, world, pos, null);
        }
        return pickBlock.getItem().getRegistryName() + ":" +pickBlock.getMetadata();
    }

    private static int countBlockInThisChunk(World world,BlockPos pos, String blockNameAndMeta){
        Chunk chunk = world.getChunk(pos);
        int count = 0 ;
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        for (int x = 0; x < 16 ; x++) {
            for (int z = 0; z < 16 ; z++) {
                for (int y = 0; y < 255 ; y++) {
                    IBlockState iBlockState = chunk.getBlockState(x,y,z);
                    ItemStack pickBlock = iBlockState.getBlock().getPickBlock(iBlockState,null,world,new BlockPos(chunkX+x,y,chunkZ+z),null);
                    String nameAndMeta = pickBlock.getItem().getRegistryName() + String.valueOf(pickBlock.getMetadata());
                    if (nameAndMeta.equals(blockNameAndMeta)){
                        count += 1;
                    }
                }

            }

        }
        return count;
    }


}
