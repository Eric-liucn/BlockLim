package blocklim.blocklim.config;


import java.util.HashMap;
import java.util.Map;

public class RuleMap {

    private static Map<String,Integer> ruleMap = new HashMap<>();

    private static void getValueFromConfig(){

        for (Object key:Config.getRootNode().getNode("Rules").getChildrenMap().keySet()){
            String blockId = Config.getRootNode().getNode("Rules").getChildrenMap().get(key).getNode("BlockId").getString();
            int limit = Config.getRootNode().getNode("Rules").getChildrenMap().get(key).getNode("Limit").getInt();
            ruleMap.put(blockId,limit);
        }
    }

    public static void reloadRuleMap(){
        ruleMap.clear();
        getValueFromConfig();
    }

    public static Map<String, Integer> getRuleMap() {
        return ruleMap;
    }
}
