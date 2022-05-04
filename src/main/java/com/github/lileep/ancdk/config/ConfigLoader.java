package com.github.lileep.ancdk.config;

import com.github.lileep.ancdk.AnCDK;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private static ConfigLoader instance;

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigurationLoader<CommentedConfigurationNode> exportLoader;
    private ConfigurationLoader<CommentedConfigurationNode> loggerLoader;

    private ConfigurationNode rootNode;
    private ConfigurationNode exportNode;

    private boolean useDB = false;
    private File logFile;

    public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
        return this.loader;
    }

    public ConfigurationNode getRootNode() {
        return this.rootNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getExportLoader() {
        return this.exportLoader;
    }

    public ConfigurationNode getExportNode() {
        return this.exportNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoggerLoader() {
        return this.loggerLoader;
    }

    public ConfigurationNode getLoggerNode() {
        return this.loggerNode;
    }

    public File getLogFile() {
        return logFile;
    }

    public void reload(){
        try {
            rootNode = loader.load();
            loader.save(rootNode);

            exportNode = exportLoader.load();
            exportLoader.save(exportNode);

            loggerNode = loggerLoader.load();
            loggerLoader.save(loggerNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ConfigLoader() {
        instance = this;
        File configPath = AnCDK.getInstance().getConfigPath();
        if (!configPath.exists()) {
            if (!configPath.mkdir()) {
                return;
            }
        }
        try {
            loader = HoconConfigurationLoader
                    .builder()
                    .setFile(new File(configPath, "cdks.conf"))
                    .build();
            rootNode = loader.load();
            loader.save(rootNode);

            exportLoader = HoconConfigurationLoader
                    .builder()
                    .setFile(new File(configPath, "export.conf"))
                    .build();
            exportNode = exportLoader.load();
            exportLoader.save(exportNode);

            logFile = new File(configPath, "info.log");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
