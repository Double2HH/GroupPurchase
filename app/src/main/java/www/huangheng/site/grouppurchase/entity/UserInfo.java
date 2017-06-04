package www.huangheng.site.grouppurchase.entity;

/**
 * 用户信息
 */

public class UserInfo {


    /**
     * createdAt : 2017-04-12 19:11:37
     * objectId : 4c16feebc8
     * sessionToken : e1cfacde4037143d80db1aa3b771ee17
     * updatedAt : 2017-04-12 20:00:06
     * username : Hhhhhh
     */

    private String objectId;
    private String sessionToken;


    private String avartarUrl;
    private String username;
    private int accountLevel;

    private int ticketCount;
    private int collectCount;
    private int recentlyCount;

    private int unpaidedMessages;
    private int paidedMessages;
    private int uncommentedMessages;

    private int balanceCount;
    private int voucherCount;

    private int surpriseCount;



    public int getPaidedMessages() {
        return paidedMessages;
    }

    public void setPaidedMessages(int paidedMessages) {
        this.paidedMessages = paidedMessages;
    }

    public int getUncommentedMessages() {
        return uncommentedMessages;
    }

    public void setUncommentedMessages(int uncommentedMessages) {
        this.uncommentedMessages = uncommentedMessages;
    }

    public int getUnpaidedMessages() {
        return unpaidedMessages;
    }

    public void setUnpaidedMessages(int unpaidedMessages) {
        this.unpaidedMessages = unpaidedMessages;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvartarUrl() {
        return avartarUrl;
    }

    public void setAvartarUrl(String avartarUrl) {
        this.avartarUrl = avartarUrl;
    }

    public int getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(int accountLevel) {
        this.accountLevel = accountLevel;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getRecentlyCount() {
        return recentlyCount;
    }

    public void setRecentlyCount(int recentlyCount) {
        this.recentlyCount = recentlyCount;
    }

    public int getBalanceCount() {
        return balanceCount;
    }

    public void setBalanceCount(int balanceCount) {
        this.balanceCount = balanceCount;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public int getSurpriseCount() {
        return surpriseCount;
    }

    public void setSurpriseCount(int surpriseCount) {
        this.surpriseCount = surpriseCount;
    }
}
