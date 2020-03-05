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
        rootNode.getNode("Rules","rule 1","BlockId").setValue("minecraft:chest");
        rootNode.getNode("Rules","rule 1","UnsafeDamage").setValue(0);
        rootNode.getNode("Rules","rule 1","Limit").setValue(10);
        rootNode.getNode("Rules","rule 2","BlockId").setValue("minecraft:wool");
        rootNode.getNode("Rules","rule 2","UnsafeDamage").setValue(2);
        rootNode.getNode("Rules","rule 2","Limit").setValue(10);
    }

}
