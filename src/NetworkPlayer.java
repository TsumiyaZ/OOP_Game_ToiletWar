public class NetworkPlayer {
    private String playerName;
    private int skinIndex;
    private String ipAddress;
    private boolean isReady;

    public NetworkPlayer(String playerName, int skinIndex, String ipAddress) {
        this.playerName = playerName;
        this.skinIndex = skinIndex;
        this.ipAddress = ipAddress;
        this.isReady = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getSkinIndex() {
        return skinIndex;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public void setSkinIndex(int skinIndex) {
        this.skinIndex = skinIndex;
    }
}