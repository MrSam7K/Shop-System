package me.mrsam7k.shopsystem;

public class PlayerStats {

    private String playerUUID;
    private String upgrade;
    private int tier;

    public PlayerStats(String playerUUID, int tier) {
        this.playerUUID = playerUUID;
        this.tier = tier;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }


}
