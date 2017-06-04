package www.huangheng.site.grouppurchase.entity;


/**
 * 静态用户信息
 */

public class StaticUserInfo {


    private static final StaticUserInfo info = new StaticUserInfo();

    public synchronized static StaticUserInfo getInstance() {
        return info;
    }

    private StaticUserInfo() {

    }

    /**
     * 设置用户信息
     *
     * @param userInfo 用户信息
     */
    public void setUserInfo(UserInfo userInfo, boolean isUserPassLogin) {
        if (userInfo != null) {

            //登录成功的标志
            info.setIsSucceed(true);

            info.setObjectId(userInfo.getObjectId());

            //仅当用户名密码登录时才有SessionToken
            if (isUserPassLogin) {
                info.setSessionToken(userInfo.getSessionToken());
            }

            info.setAvartarUrl(userInfo.getAvartarUrl());
            info.setUsername(userInfo.getUsername());
            info.setAccountLevel(userInfo.getAccountLevel());

            info.setTicketCount(userInfo.getTicketCount());
            info.setCollectCount(userInfo.getCollectCount());
            info.setRecentlyCount(userInfo.getRecentlyCount());

            info.setUnpaidedMessages(userInfo.getUnpaidedMessages());
            info.setPaidedMessages(userInfo.getPaidedMessages());
            info.setUncommentedMessages(userInfo.getUncommentedMessages());

            info.setBalanceCount(userInfo.getBalanceCount());
            info.setVoucherCount(userInfo.getVoucherCount());

            info.setSurpriseCount(userInfo.getSurpriseCount());

        } else {

            //默认值
            info.setIsSucceed(false);

            info.setObjectId("000000");
            info.setSessionToken("000000");

            info.setAvartarUrl("");
            info.setUsername("Username");
            info.setAccountLevel(0);

            info.setTicketCount(0);
            info.setCollectCount(0);
            info.setRecentlyCount(0);

            info.setBalanceCount(0);
            info.setVoucherCount(0);

            info.setSurpriseCount(0);

            info.setUnpaidedMessages(0);
            info.setPaidedMessages(0);
            info.setUncommentedMessages(0);
        }
    }

    public static boolean isSucceed = false;

    public static String objectId = "000000";
    public static String sessionToken = "000000";

    public static String avartarUrl = "";
    public static String username = "Username";
    public static int accountLevel = 0;

    public static int ticketCount = 0;
    public static int collectCount = 0;
    public static int recentlyCount = 0;

    public static int balanceCount = 0;
    public static int voucherCount = 0;

    public static int surpriseCount = 0;

    public static int unpaidedMessages = 0;
    public static int paidedMessages = 0;
    public static int uncommentedMessages = 0;


    public boolean isSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        StaticUserInfo.isSucceed = isSucceed;
    }

    public int getPaidedMessages() {
        return paidedMessages;
    }

    public void setPaidedMessages(int paidedMessages) {
        StaticUserInfo.paidedMessages = paidedMessages;
    }

    public int getUncommentedMessages() {
        return uncommentedMessages;
    }

    public void setUncommentedMessages(int uncommentedMessages) {
        StaticUserInfo.uncommentedMessages = uncommentedMessages;
    }

    public int getUnpaidedMessages() {
        return unpaidedMessages;
    }

    public void setUnpaidedMessages(int unpaidedMessages) {
        StaticUserInfo.unpaidedMessages = unpaidedMessages;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        StaticUserInfo.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        StaticUserInfo.sessionToken = sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        StaticUserInfo.username = username;
    }


    public String getAvartarUrl() {
        return avartarUrl;
    }

    public void setAvartarUrl(String avartarUrl) {
        StaticUserInfo.avartarUrl = avartarUrl;
    }

    public int getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(int accountLevel) {
        StaticUserInfo.accountLevel = accountLevel;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        StaticUserInfo.ticketCount = ticketCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        StaticUserInfo.collectCount = collectCount;
    }

    public int getRecentlyCount() {
        return recentlyCount;
    }

    public void setRecentlyCount(int recentlyCount) {
        StaticUserInfo.recentlyCount = recentlyCount;
    }

    public int getBalanceCount() {
        return balanceCount;
    }

    public void setBalanceCount(int balanceCount) {
        StaticUserInfo.balanceCount = balanceCount;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        StaticUserInfo.voucherCount = voucherCount;
    }

    public int getSurpriseCount() {
        return surpriseCount;
    }

    public void setSurpriseCount(int surpriseCount) {
        StaticUserInfo.surpriseCount = surpriseCount;
    }
}
