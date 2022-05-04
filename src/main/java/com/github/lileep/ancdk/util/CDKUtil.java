package com.github.lileep.ancdk.util;

import com.github.lileep.ancdk.AnCDK;
import com.github.lileep.ancdk.config.ConfigLoader;
import com.github.lileep.ancdk.lib.Reference;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class CDKUtil {

    private static TypeToken setToken = new TypeToken<Set<String>>() {
    };

    private static Set<String> emptySet = new HashSet<>();

    /**
     * 运行CDK主逻辑
     * 对于控制台指令剪掉其"console:"部分
     *
     * @param cdk    待处理的CDK
     * @param player 执行者
     */
    private static void runCDK(String cdk, Player player) {
        String command = ConfigLoader.getInstance().getCdkNode().getNode(cdk, "command").getString().replace("{player}", player.getName());
        CommandSource source;
        if (command.startsWith(Reference.CONSOLE_PREFIX)) {
            source = Sponge.getServer().getConsole();
            command = command.substring(Reference.CONSOLE_PREFIX.length());
        } else {
            source = player;
        }
        Sponge.getCommandManager().process(source, command);
    }


    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    /**
     * 日志记录主逻辑
     *
     * @param executor  此条记录的操作来源
     * @param operation 操作
     * @param cdks      待记录的CDK
     */
    private static void setLog(String executor, String operation, String... cdks) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConfigLoader.getInstance().getLogFile(), true), StandardCharsets.UTF_8))) {
            for (String cdk : cdks) {
                bw.write(FORMATTER.format(new Date(System.currentTimeMillis())) + executor + " " + operation + " " + cdk);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行CDK并记录log
     *
     * @param cdkey  待运行的CDK
     * @param player 执行者
     * @return 是否运行成功
     */
    public static boolean runCDKandLog(String cdkey, Player player) {
        ConfigurationNode node = ConfigLoader.getInstance().getCdkNode();
        if (Optional.ofNullable(node.getNode(cdkey).getString()).isPresent()) {
            runCDK(cdkey, player);
            try {
                if (Optional.ofNullable(node.getNode(cdkey, "usedPlayer").getValue(setToken)).isPresent()) {
                    Set<String> tempSet = (HashSet<String>) node.getNode(cdkey, "usedPlayer").getValue(setToken);
                    if (tempSet.contains(player.getName())) {
                        return false;
                    }
                    tempSet.add(player.getName());
                    node.getNode(cdkey, "usedPlayer").setValue(tempSet);
                } else {
                    node.getNode(cdkey).setValue(null);
                }
                ConfigLoader.getInstance().getCdkLoader().save(node);
                if (ConfigLoader.getInstance().getRootNode().getNode("General", "useLog").getBoolean()) {
                    setLog(player.getName(), "use", cdkey);
                }
            } catch (IOException | ObjectMappingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private static final Random RANDOM = new Random();

    /**
     * 生成九位及以上CDK
     *
     * @return 生成的CDK
     */
    public static String genCDK() {
        int a, b, c;
        a = RANDOM.nextInt(360000) + 2000;
        b = RANDOM.nextInt(300000) + 2000;
        c = RANDOM.nextInt(66000) + 2000;
        if (RANDOM.nextInt(10) % 2 == 0) {
            return (Integer.toString(a, 16) + Integer.toString(b, 16) + Integer.toString(c, 16));
        }
        return (Integer.toString(a, 36) + Integer.toString(b, 36) + Integer.toString(c, 36));
    }

    /**
     * 创建CDK
     *
     * @param command  CDK对应的命令
     * @param count    创建条数
     * @param once     是否一次性
     * @param executor 创建者
     * @return 是否创建成功
     * @throws IOException 需处理的读写异常
     */
    public static boolean createCDK(String command, int count, boolean once, String executor) throws IOException, ObjectMappingException {
        ConfigurationNode node = ConfigLoader.getInstance().getCdkNode();
        if (ConfigLoader.getInstance().getRootNode().getNode("General", "useLog").getBoolean()) {
            List<String> keyList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String key = genCDK();
                node.getNode(key, "command").setValue(command);
                if (!once) {
                    node.getNode(key, "usedPlayer").setValue(setToken, emptySet);
                }
                keyList.add(key);
            }
            String[] tempStr = new String[keyList.size()];
            setLog(executor, "create", keyList.toArray(tempStr));
        } else {
            for (int i = 0; i < count; i++) {
                String key = genCDK();
                node.getNode(key, "command").setValue(command);
                if (!once) {
                    node.getNode(key, "usedPlayer").setValue(setToken, emptySet);
                }
            }
        }
        ConfigLoader.getInstance().getCdkLoader().save(node);
        return true;
    }

    /**
     * 导出
     *
     * @return 是否导出成功
     * @throws IOException 需处理的读写异常
     */
    public static boolean exportCDK() throws IOException {
//        AnCDK.getInstance().getLogger().info("key set:"+cdkList.keySet()+", values:"+cdkList.values());
//        AnCDK.getInstance().getLogger().info("values:"+cdkList.values());
//        for (Map<String, String> sets : cdkList.values()) {
//            AnCDK.getInstance().getLogger().info(sets.get("command"));
//        }
        if (!Optional.ofNullable(ConfigLoader.getInstance().getCdkNode().getValue()).isPresent()) {
            return false;
        }
        Map<String, Map<String, String>> cdkList = (LinkedHashMap<String, Map<String, String>>) ConfigLoader.getInstance().getCdkNode().getValue(LinkedHashMap.class);
        ConfigurationNode exportNode = ConfigLoader.getInstance().getExportNode();

        Map<String, Integer> counter = new LinkedHashMap<>();
        for (String key : cdkList.keySet()) {
            String command = cdkList.get(key).get("command");
            counter.merge(command, 1, Integer::sum);
            exportNode.getNode(command, counter.get(command).toString()).setValue(key);
        }
        ConfigLoader.getInstance().getExportLoader().save(exportNode);
        return true;
    }

    /**
     * 导为CSV
     *
     * @return 是否导出成功
     */
    public static boolean exportCSV() {
        if (!Optional.ofNullable(ConfigLoader.getInstance().getCdkNode().getValue()).isPresent()) {
            return false;
        }
        Set<String> cdkSet = ((LinkedHashMap<String, Map<String, String>>) ConfigLoader.getInstance().getCdkNode().getValue(LinkedHashMap.class)).keySet();
        CSVUtil.writeCSVFileData(new File(AnCDK.getInstance().getConfigPath(), "export.csv"), cdkSet);

        return true;
    }

}
