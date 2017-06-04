package www.huangheng.site.grouppurchase.entity;

import java.util.List;

/**
 * 主页静态数据
 */

public class HomepageStaticInfo {


    private static final HomepageStaticInfo info = new HomepageStaticInfo();

    public synchronized static HomepageStaticInfo getInstance() {
        return info;
    }

    private HomepageStaticInfo() {
    }

    /**
     * 设置数据
     *
     * @param resultsBean 信息
     */
    public void setStaticInfo(HomepageInfo.ResultsBean resultsBean) {
        if (resultsBean != null) {

            info.setIsSucceed(true);

            info.setBannerImageUrl(resultsBean.getBannerImageUrl());
            info.setBannerUrl(resultsBean.getBannerUrl());

            info.setClassifyImageUrl(resultsBean.getClassifyImageUrl());
            info.setClassifyString(resultsBean.getClassifyString());
            info.setClassifyUrl(resultsBean.getClassifyUrl());

            info.setHeadlineString(resultsBean.getHeadlineString());
            info.setHeadlineUrl(resultsBean.getHeadlineUrl());

            info.setAdImageUrl(resultsBean.getAdImageUrl());
            info.setAdString(resultsBean.getAdString());
            info.setAdUrl(resultsBean.getAdUrl());

        } else {

            info.setIsSucceed(false);

            info.setBannerImageUrl(null);
            info.setBannerUrl(null);

            info.setClassifyImageUrl(null);
            info.setClassifyString(null);
            info.setClassifyUrl(null);

            info.setHeadlineString(null);
            info.setHeadlineUrl(null);

            info.setAdImageUrl(null);
            info.setAdString(null);
            info.setAdUrl(null);

        }
    }

    public static boolean isSucceed = true;

    public static List<String> adImageUrl;
    public static List<String> adString;
    public static List<String> adUrl;
    public static List<String> bannerImageUrl;
    public static List<String> bannerUrl;
    public static List<String> classifyImageUrl;
    public static List<String> classifyString;
    public static List<String> classifyUrl;
    public static List<String> headlineString;
    public static List<String> headlineUrl;


    public boolean isSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        HomepageStaticInfo.isSucceed = isSucceed;
    }

    public List<String> getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(List<String> adImageUrl) {
        this.adImageUrl = adImageUrl;
    }

    public List<String> getAdString() {
        return adString;
    }

    public void setAdString(List<String> adString) {
        this.adString = adString;
    }

    public List<String> getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(List<String> adUrl) {
        this.adUrl = adUrl;
    }

    public List<String> getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(List<String> bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public List<String> getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(List<String> bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public List<String> getClassifyImageUrl() {
        return classifyImageUrl;
    }

    public void setClassifyImageUrl(List<String> classifyImageUrl) {
        this.classifyImageUrl = classifyImageUrl;
    }

    public List<String> getClassifyString() {
        return classifyString;
    }

    public void setClassifyString(List<String> classifyString) {
        this.classifyString = classifyString;
    }

    public List<String> getClassifyUrl() {
        return classifyUrl;
    }

    public void setClassifyUrl(List<String> classifyUrl) {
        this.classifyUrl = classifyUrl;
    }

    public List<String> getHeadlineString() {
        return headlineString;
    }

    public void setHeadlineString(List<String> headlineString) {
        this.headlineString = headlineString;
    }

    public List<String> getHeadlineUrl() {
        return headlineUrl;
    }

    public void setHeadlineUrl(List<String> headlineUrl) {
        this.headlineUrl = headlineUrl;
    }

}
