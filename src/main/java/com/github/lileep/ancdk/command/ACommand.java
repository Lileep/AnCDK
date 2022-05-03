package com.github.lileep.ancdk.command;

import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.lib.Reference;
import com.github.lileep.ancdk.util.CDKUtil;
import com.github.lileep.ancdk.util.TextUtil;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.Optional;

public class ACommand {

    public final static CommandSpec CREATE =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        String command = args.<String>getOne("command").get();
                        int count = args.<Integer>getOne("count").get();
                        try {
                            if (CDKUtil.createCDK(command, count, args.hasAny("once"), (src instanceof Player ? (((Player) src).getName()) : ("console")))) {
                                src.sendMessage(TextUtil.prefixedText("§6设置成功！成功创建§c %d §6张卡密, 详情请浏览配置文件", count));
                                return CommandResult.success();
                            }
                        } catch (IOException | ObjectMappingException e) {
                            src.sendMessage(TextUtil.prefixedText("§c设置失败！"));
                        }
                        return CommandResult.empty();
                    }))
                    .arguments(
                            GenericArguments.flags().flag("-once").buildWith(GenericArguments.none()),
                            GenericArguments.integer(Text.of("count")),
                            GenericArguments.remainingJoinedStrings(Text.of("command"))
                    )
                    .permission(Reference.PERM_NODE_CREATE)
                    .build();

    public final static CommandSpec EXPORT_CSV =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        if (CDKUtil.exportCSV()) {
                            src.sendMessage(TextUtil.prefixedText("§6导出CSV文件成功！请检查配置文件中export.csv"));
                        } else {
                            src.sendMessage(TextUtil.prefixedText("§c在导出CSV文件过程中发生错误！请检查！"));
                        }
                        return CommandResult.success();
                    }))
                    .permission(Reference.PERM_NODE_EXPORT)
                    .build();

    public final static CommandSpec EXPORT =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        try {
                            if (CDKUtil.exportCDK()) {
                                src.sendMessage(TextUtil.prefixedText("§6导出成功！请检查配置文件中export.conf"));
                            } else {
                                src.sendMessage(TextUtil.prefixedText("§c在导出过程中发生错误！请检查！"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return CommandResult.success();
                    }))
                    .child(EXPORT_CSV, "csv")
                    .permission(Reference.PERM_NODE_EXPORT)
                    .build();

    public final static CommandSpec RELOAD =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        ConfigLoader.getInstance().reload();
                        src.sendMessage(TextUtil.prefixedText("§c重载成功！"));
                        return CommandResult.success();
                    }))
                    .permission(Reference.PERM_NODE_RELOAD)
                    .build();

    public final static CommandSpec BASE =
            CommandSpec.builder()
                    .executor(((src, args) -> {
                        if (args.getOne("cdkey") != Optional.empty()) {
                            if (src instanceof Player && src.hasPermission(Reference.USER_NODE)) {
                                String cdkey = args.<String>getOne("cdkey").get();
                                if (CDKUtil.runCDKandLog(cdkey, (Player) src)) {
                                    src.sendMessage(TextUtil.prefixedText("§6激活码领取成功！"));
                                    return CommandResult.success();
                                } else {
                                    src.sendMessage(TextUtil.prefixedText("§c激活码领取失败"));
                                }
                            }
                            return CommandResult.empty();
                        }

                        if (src.hasPermission(Reference.PERM_NODE_ADMIN)) {
                            src.sendMessages(
                                    Text.of("§a==============================AnCDK=============================="),
                                    Text.of("§9/ancdk create <--once> [num] [command]    创建[num]个执行[command]命令的CDK"),
                                    Text.of("§9/ancdk export                             批量一键导出所有CDK"),
                                    Text.of("§9/ancdk reload                             重载配置文件"),
                                    Text.of("§9/ancdk [CDK]                              使用CDK"),
                                    Text.of("§a==============================AnCDK==============================")
                            );
                        } else {
                            src.sendMessages(
                                    Text.of("§a==============================AnCDK=============================="),
                                    Text.of("§9/ancdk [CDK]                              使用CDK"),
                                    Text.of("§a==============================AnCDK==============================")
                            );
                        }
                        return CommandResult.success();
                    }))
                    .arguments(
                            GenericArguments.optionalWeak(GenericArguments.remainingJoinedStrings(Text.of("cdkey")))
                    )
                    .child(CREATE, "create", "generate", "gen", "c")
                    .child(EXPORT, "export")
                    .child(RELOAD, "reload")
                    .build();

}
