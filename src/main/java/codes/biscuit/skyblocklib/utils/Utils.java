package codes.biscuit.skyblocklib.utils;

public final class Utils {

    private static final String RAW_GITHUB_URL = "https://raw.githubusercontent.com/BiscuitDevelopment/SkyblockLib/";

    private Utils() {}

    /**
     * Get the URL to a raw file on the GitHub Repository on a given branch.
     *
     * @param branch Branch to link to
     * @param filePath File path relative to project root without leading /
     * @return Full URL to the raw file
     */
    public static String getRemoteFileUrl(String branch, String filePath) {
        return String.format("%s/%s/%s", RAW_GITHUB_URL, branch, filePath);
    }

    /**
     * Get the URL to a raw file on the GitHub Repository on the master branch.
     *
     * @param filePath File path relative to project root without leading /
     * @return Full URL to the raw file
     */
    public static String getRemoteFileUrl(String filePath) {
        return getRemoteFileUrl("master", filePath);
    }

    /**
     * Get the URL to a raw resource file on the GitHub Repository on master branch.
     *
     * @param filePath File path relative to src/main/resources/ without leading /
     * @return Full URL to the raw file
     */
    public static String getRemoteResourceFileUrl(String filePath) {
        return getRemoteFileUrl("src/main/resources/"+filePath);
    }
}
