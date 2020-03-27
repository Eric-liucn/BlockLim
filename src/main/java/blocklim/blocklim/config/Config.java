package blocklim.blocklim.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class Config {

    private static ConfigurationLoader<CommentedConfigurationNode> loader ;
    private static CommentedConfigurationNode rootNode;

    public static CommentedConfigurationNode getRootNode() {
        return rootNode;
    }

    public static void setUp(File file) throws IOException {
        if (!file.exists())
        {
            file.mkdir();
        }
        File config = new File(file,"blocklim.conf");
        loader = HoconConfigurationLoader.builder().setFile(config).build();
        if (!config.exists())
        {
            config.createNewFile();
            rootNode = loader.load();
            addValue();
            save();
        }
        load();
    }

    public static void save() throws IOException {
        loader.save(rootNode);
    }

    public static void load() throws IOException{
        rootNode = loader.load();
    }

    private static void addValue(){
        rootNode.getNode("Rules","工业2机器限制","BlockId").setValue("ic2:te");
        rootNode.getNode("Rules","工业2机器限制","Limit").setValue(30);
        rootNode.getNode("Rules","rule2","BlockId").setValue("botania:specialflower[color=white,type=poppy]");
        rootNode.getNode("Rules","rule2","Limit").setValue(10);
    }

}
