package blocklim.blocklim.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.HashMap;
import java.util.Map;

public class RuleMap {

    private static Map<String,Map<String,Integer>> ruleMap = new HashMap<>();

    private static void getValueFromConfig(){
        Map<Object, CommentedConfigurationNode> configMap;
        configMap = (Map<Object, CommentedConfigurationNode>) Config.getRootNode().getNode("Rules").getChildrenMap();

        for (Object key:configMap.keySet()){
            String blockId = configMap.get(key).getNode("BlockId").getString();
            int unsafeDamage = configMap.get(key).getNode("UnsafeDamage").getInt();
            int limit = configMap.get(key).getNode("Limit").getInt();
            Map<String,Integer> temMap = new HashMap<>();
            temMap.put("UnsafeDamage",unsafeDamage);
            temMap.put("Limit",limit);
            ruleMap.put(blockId,temMap);
        }
    }

    public static Map<String, Map<String, Integer>> getRuleMap() {
        getValueFromConfig();
        return ruleMap;
    }
}
