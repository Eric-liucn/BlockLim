package blocklim.blocklim;

import blocklim.blocklim.command.Base;
import blocklim.blocklim.config.Config;
import blocklim.blocklim.config.RuleMap;
import blocklim.blocklim.listener.BlockPlaceEvent;
import com.google.inject.Inject;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

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

    public static Main getINSTANCE() {
        return INSTANCE;
    }

    @Inject
    private Logger logger;

    public static Logger getLogger() {
        return INSTANCE.logger;
    }

    @Inject
    @ConfigDir(sharedRoot = false)
    File file;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        INSTANCE = this;
        //加载配置文件
        try {
            Config.setUp(file);
        }catch (IOException e){
            logger.info("配置文件加载失败！");
        }

        RuleMap.reloadRuleMap();

        Sponge.getCommandManager().register(this, Base.build(),"blocklim","bl");

        MinecraftForge.EVENT_BUS.register(new BlockPlaceEvent());
    }

    @Listener
    public void onReload(GameReloadEvent event) throws IOException {
        Config.load();
        Config.save();
        RuleMap.reloadRuleMap();
    }
}
