package de.webcode.system.utils.reporting;

import org.bukkit.entity.Player;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Warning {
    private Player warnedPlayer;
    private String mod;
    private String reason;
    private String date;

    public Warning(Player warnedPlayer, String mod, String reason){
        this.warnedPlayer = warnedPlayer;
        this.mod = mod;
        this.reason = reason;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    public Warning(Player warnedPlayer, String mod, String reason, String date){
        this.warnedPlayer = warnedPlayer;
        this.mod = mod;
        this.reason = reason;
        this.date = date;
    }

    public Player getWarnedPlayer() {
        return warnedPlayer;
    }

    public String getMod() {
        return mod;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }
}
