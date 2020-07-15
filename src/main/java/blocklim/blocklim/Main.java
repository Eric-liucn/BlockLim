package blocklim.blocklim;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.world.World;

import java.io.File;

@Plugin(
        id = "blocklim",
        name = "Blocklim",
        description = "A plugin used to limit the max number of a blocktype in a single chunk",
        authors = {
                "EricLiu"
        }
)
public class Main {

    private static Main INSTANCE;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File file;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        INSTANCE = this;


        CommandSpec spec = CommandSpec.builder()
                .executor((src, args) -> {
                    if (src instanceof Player){
                        Player player = ((Player) src);
                        BlockRay<World> blockRay = BlockRay.from(player).stopFilter(BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(),1)).build();
                        while (blockRay.hasNext()){
                            BlockState blockState = blockRay.end().get().getLocation().getBlock();
                            System.out.println(blockState);
                            blockState.getTraits().forEach(blockTrait -> {
                                System.out.println(blockTrait.getName());
                            });
                            System.out.println(blockState.getTraitValues());
                        }
                    }
                    return CommandResult.success();
                })
                .build();

        Sponge.getCommandManager().register(this, spec, "test");
    }

    public Logger getLogger() {
        return logger;
    }

    public static Main getINSTANCE() {
        return INSTANCE;

    }

    public File getFile() {
        return file;
    }
}
