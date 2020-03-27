package blocklim.blocklim.command;

import blocklim.blocklim.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Base implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        List<Text> textList = new ArrayList<>();
        textList.add(Utils.strFormat("&d/blocklim see &a-> &e查看当前指向方块所用识别id"));
        textList.add(Utils.strFormat("&d/blocklim add [规则名称] [限制数量] &a-> &e将当前指向方块加入限制列表"));
        PaginationList.builder()
                .title(Utils.strFormat("&b帮助信息"))
                .contents(textList)
                .padding(Utils.strFormat("&a="))
                .sendTo(src);
        return CommandResult.success();
    }

    public static CommandSpec build(){
        return CommandSpec.builder()
                .executor(new Base())
                .child(See.build(),"see","s")
                .child(Create.build(),"create","c","add")
                .permission("blocklim.base")
                .build();
    }
}
