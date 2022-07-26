package de.webcode.system.utils;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.file.FileService;
import de.webcode.system.utils.reporting.PlayerReport;
import de.webcode.system.utils.reporting.Warning;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManagementService {
    private FileService fileService;
    private YamlConfiguration playerData;

    public PlayerManagementService() {
        this.fileService = ServerSystem.getInstance().getFileService();
        this.playerData = fileService.getPlayerData();
    }

    public void resetWarnings(Player player) {
        String pName = player.getName();

        playerData.set(pName + ".warning", null);
        fileService.saveFiles();

        playerData.set(pName + ".warning.count", 0);
        fileService.saveFiles();
    }

    public void addWarning(Player player, Warning warning) {
        String pName = player.getName();
        int count = getWarningCount(player) + 1;

        playerData.set(pName + ".warning.count", count);
        playerData.set(pName + ".warning." + count + ".mod", warning.getMod());
        playerData.set(pName + ".warning." + count + ".date", warning.getDate());
        playerData.set(pName + ".warning." + count + ".reason", warning.getReason());

        fileService.saveFiles();
    }

    public ArrayList<Warning> getWarnings(Player player) {
        int warningCount = getWarningCount(player);

        if (warningCount == 0) return null;

        String pName = player.getName();

        ArrayList<Warning> warnings = new ArrayList<>();

        for (int i = 1; i < warningCount + 1; i++) {
            String mod = playerData.getString(pName + ".warning." + i + ".mod");
            String date = playerData.getString(pName + ".warning." + i + ".date");
            String reason = playerData.getString(pName + ".warning." + i + ".reason");

            Warning w = new Warning(player, mod, reason, date);
            warnings.add(w);
        }

        return warnings;
    }

    public int getWarningCount(Player player) {
        String pName = player.getName();
        if (playerData.isSet(pName + ".warning.count")) {
            return playerData.getInt(pName + ".warning.count");
        }

        return 0;
    }

    public void banPlayer(Player player, String reason) {
        player.banPlayer("\n\n" +
                "§8----------------------------------------\n" +
                "§c§lGebannt\n" +
                "§7Du wurdest wegen folgendem Grund vom Server gebannt: \n\n" +
                reason +
                "\n\n" +
                "§7Für eine Entbannung wende dich bitte an die Administratoren des Servers.\n" +
                "§8----------------------------------------");
        player.kickPlayer(reason);
    }

    public void kickPlayer(Player player, String reason) {
        player.kickPlayer("\n\n" +
                "§8----------------------------------------\n" +
                "§c§§lDu wurdest aus folgendem Grund vom Server gekickt:\n\n" +
                reason +
                "\n\n" +
                "§8----------------------------------------");
    }

    public void reportPlayer(Player player, PlayerReport report) {
        String pName = player.getName();

        int count = getReportCount(player.getName()) + 1;
        playerData.set("Report." + pName + ".report.count", count);
        playerData.set("Report." + pName + ".report." + count + ".reason", report.getReason());
        playerData.set("Report." + pName + ".report." + count + ".reporter", report.getReporter());
        playerData.set("Report." + pName + ".report." + count + ".date", report.getDate());

        ServerSystem.getInstance().getFileService().saveFiles();
    }

    public int getReportCount(String player) {
        if (playerData.isSet("Report." + player + ".report.count")) {
            return playerData.getInt("Report." + player + ".report.count");
        }

        return 0;
    }

    public ArrayList<PlayerReport> getAllReportsOfPlayer(String player) {
        int reportCount = getReportCount(player);

        if (reportCount == 0) return null;

        ArrayList<PlayerReport> reports = new ArrayList<>();

        for (int i = 1; i < reportCount + 1; i++) {
            String reporter = playerData.getString("Report." + player + ".report." + i + ".reporter");
            String reason = playerData.getString("Report." + player + ".report." + i + ".reason");
            String date = playerData.getString("Report." + player + ".report." + i + ".date");

            reports.add(new PlayerReport(player, reason, reporter));
        }

        return reports;
    }

    public ArrayList<PlayerReport> getAllPlayerReports() {
        ArrayList<PlayerReport> reports = new ArrayList<>();

        playerData.getConfigurationSection("Report").getKeys(false).forEach(path -> {
            if (playerData.isSet("Report." + path)) {
                ArrayList<PlayerReport> allReportsOfPlayer = getAllReportsOfPlayer(path);
                allReportsOfPlayer.forEach(reports::add);
            }
        });

        return reports;
    }

    public static PlayerManagementService getService() {
        return ServerSystem.getInstance().getPlayerManagementService();
    }
}
