package blocklim.blocklim.command;

import blocklim.blocklim.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;

public class See implements CommandExecutor {


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player){
            Player player = (Player) src;
            PaginationList.builder()
                    .padding(Utils.strFormat("&a="))
                    .title(Utils.strFormat("&d方块识别ID"))
                    .contents(Utils.strFormat("&b"+Utils.getBlockStateIdFromLookAt(player)))
                    .sendTo(player);
        }else {
            src.sendMessage(Utils.strFormat("&4该指令只能由玩家执行"));
        }
        return CommandResult.success();
    }

    public static CommandSpec build(){
        return CommandSpec.builder()
                .executor(new See())
                .permission("blocklim.see")
                .build();
    }

}
