package de.webcode.system.utils.reporting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerReport {
    private String reason;
    private String date;
    private String reporter;
    private String reportedPlayer;

    public PlayerReport(String reportedPlayer, String reason, String reporter) {
        this.reason = reason;
        this.reportedPlayer = reportedPlayer;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = new Date();
        this.date = dateFormat.format(d);
        this.reporter = reporter;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReportedPlayer() {
        return reportedPlayer;
    }
}
