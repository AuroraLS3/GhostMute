package com.djrapitops.ghostmute.utils;

import com.djrapitops.ghostmute.GhostMute;
import java.net.URL;
import java.util.Scanner;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

/**
 *
 * @author Rsl1122
 */
public class MiscUtils {

    /**
     * Checks the version and returns response String.
     *
     * @return String informing about status of plugins version.
     */
    public static String checkVersion() {
        GhostMute plugin = getPlugin(GhostMute.class);
        String cVersion;
        String lineWithVersion;
        try {
            URL githubUrl = new URL("https://raw.githubusercontent.com/Rsl1122/GhostMute/master/GhostMute/src/plugin.yml");
            lineWithVersion = "";
            Scanner websiteScanner = new Scanner(githubUrl.openStream());
            while (websiteScanner.hasNextLine()) {
                String line = websiteScanner.nextLine();
                if (line.toLowerCase().contains("version")) {
                    lineWithVersion = line;
                    break;
                }
            }
            String versionString = lineWithVersion.split(": ")[1];
            int newestVersionNumber = parseVersionNumber(versionString);
            cVersion = plugin.getDescription().getVersion();
            int currentVersionNumber = parseVersionNumber(cVersion);
            if (newestVersionNumber > currentVersionNumber) {
                return "New Version (" + versionString + ") is availible at https://www.spigotmc.org/resources/ghostmute-fake-blocked-messages.33880/";
            } else {
                return "You're running the latest version";
            }
        } catch (Exception e) {
            plugin.logError("Failed to compare versions.");
        }
        return "Failed to get newest version number.";
    }

    /**
     * Turns the version string into a integer
     *
     * @param versionString String - number format 1.1.1
     * @return parsed double - for example 1,11
     * @throws NumberFormatException When wrong format
     */
    public static int parseVersionNumber(String versionString) throws NumberFormatException {
        String[] versionArray = versionString.split("\\.");
        if (versionArray.length != 3) {
            throw new NumberFormatException("Wrong format used");
        }
        int main = Integer.parseInt(versionArray[0]) * 100;
        int major = Integer.parseInt(versionArray[1]) * 10;
        int minor = Integer.parseInt(versionArray[2]);
        int versionNumber = main + major + minor;
        return versionNumber;
    }
}
