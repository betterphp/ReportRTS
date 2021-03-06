/****************************************************************************************************
 ** Thanks to mbaxter for the base source code for the VersionChecker.                             **
 ** https://github.com/mbax/VanishNoPacket/blob/master/src/org/kitteh/vanish/VanishPlugin.java#L32 **
 ****************************************************************************************************/
package com.nyancraft.reportrts.util;

import com.nyancraft.reportrts.ReportRTS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VersionChecker {

    public boolean upToDate(){
        if(!ReportRTS.getPlugin().getConfig().getBoolean("versionCheck")) return true;
        try {
            final URLConnection connection = new URL("http://regularbox.com/api/check.php").openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-agent", "ReportRTS");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String version;
            String currentVersion = ReportRTS.getPlugin().getDescription().getVersion().substring(0,ReportRTS.getPlugin().getDescription().getVersion().lastIndexOf("-"));
            if ((version = bufferedReader.readLine()) != null) {
                ReportRTS.getPlugin().versionString = version;
                if (!currentVersion.equals(version)) {
                    ReportRTS.getPlugin().getLogger().info("Found a different version available: " + version);
                    ReportRTS.getPlugin().getLogger().info("Check http://dev.bukkit.org/server-mods/reportrts/ for a new version.");
                    return false;
                }
                return true;
            }
            bufferedReader.close();
            connection.getInputStream().close();
        } catch (final Exception e) {
            return true;
        }
        return true;
    }
}
