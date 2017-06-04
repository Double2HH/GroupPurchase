package www.huangheng.site.grouppurchase.entity;

/**
 * 商品分类
 */

public class ClassifyInfo {

    public String classifyName;
    public String classifyImageUrl;


    public ClassifyInfo(String classifyName, String classifyImageUrl) {
        this.classifyName = classifyName;
        this.classifyImageUrl = classifyImageUrl;
    }

    public String getClassifyImageUrl() {
        return classifyImageUrl;
    }

    public void setClassifyImageUrl(String classifyImageUrl) {
        this.classifyImageUrl = classifyImageUrl;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

}
