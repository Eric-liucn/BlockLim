package blocklim.blocklim.command;

import blocklim.blocklim.config.Config;
import blocklim.blocklim.config.RuleMap;
import blocklim.blocklim.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Create implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String name = args.<String>getOne("name").get();
        Integer number = args.<Integer>getOne("number").get();
        if (src instanceof Player){
            Player player = (Player) src;
            String BlockId = Utils.getBlockStateIdFromLookAt(player);
            Config.getRootNode().getNode("Rules",name,"Limit").setValue(number);
            Config.getRootNode().getNode("Rules",name,"BlockId").setValue(BlockId);
            try {
                Config.save();
                RuleMap.reloadRuleMap();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Text> textList = new ArrayList<>();
            textList.add(Utils.strFormat("&b识别ID: "+"&d"+BlockId));
            textList.add(Utils.strFormat("&B限制数量: "+"&d"+number));
            PaginationList.builder()
                    .title(Utils.strFormat("&d添加规则 &b"+name+"&d到配置"))
                    .contents(textList)
                    .padding(Utils.strFormat("&a="))
                    .sendTo(player);
        }

        return CommandResult.success();
    }

    public static CommandSpec build(){
        return CommandSpec.builder()
                .permission("blocklim.create")
                .executor(new Create())
                .arguments(
                        GenericArguments.seq(
                                GenericArguments.onlyOne(
                                        GenericArguments.string(Text.of("name"))
                                ),
                                GenericArguments.onlyOne(
                                        GenericArguments.integer(Text.of("number"))
                                )
                        )
                )
                .build();
    }

    private static String regReplace(String originString){
        return originString.replaceAll("\\bMetaTeBlock\\{[A-z]+","MetaTeBlock{invalid");
    }
}
