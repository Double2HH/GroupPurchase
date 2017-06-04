package www.huangheng.site.grouppurchase.entity;

import java.util.List;

/**
 * 商品详细信息
 */

public class GoodDetailsInfo {


    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address : 广东广州南沙区岭南路1号
         * commentCount : 0
         * createdAt : 2017-04-03 10:11:55
         * details : <div class="detail_cen" style="padding:0;">
         * <table border="1" width="100%" cellpadding="0" cellspacing="0" ><tbody><tr class="firstRow"><th width="33%" valign="middle" align="center">内容</th><th width="33%" valign="middle" align="center">数量</th><th width="33%" valign="middle" align="center">价格</th></tr><tr><td valign="middle" colspan="1" rowspan="1" align="left">现金抵用</td><td valign="middle" colspan="1" rowspan="1" align="center">1次</td><td valign="middle" colspan="1" rowspan="1" align="right">100 元</td></tr><tr><td valign="middle" colspan="3" rowspan="1" align="right">市场价100元，拉手团购仅需<strong><em >95</em></strong>元</td></tr></tbody></table><p><span >店内人均消费参考价：8元</span></p><p><span >适用范围：全场通用</span></p><p ><span >店内部分菜品价格参考：珍珠奶茶1杯10元、红豆奶茶1杯10元、茉香奶茶1杯10元、芋香奶茶1杯10元、仙草奶茶1杯10元、胚芽奶茶1杯10元、布丁奶茶1杯10元</span></p><div ></div>
         * distance : 38.3km
         * goodsId : 1111000
         * imagesUrl : ["https://d1.lashouimg.com/cms/M00/BD/FD/CqgBJlUTvviAK6XfAAB_b7B_tEQ645.jpg"]
         * isFavorite : false
         * objectId : Gl8PAAAC
         * product : 靓饮速递
         * ratingBar : 0
         * score : 0
         * service : [true,true,true]
         * time : 营业时间：11:00-23:00
         * tips : <div class="detail_cen" style="padding:0; font-size:12px;">
         * <ul id="tips_generated_by_system"> <li><strong>有效期：</strong>2015-05-28至2017-09-30 </li><li><strong>特殊日期使用规则：</strong>有效期内周末、法定节假日通用</li> <li><strong>预约提醒：</strong>无需预约，如遇客流量大导致的排队，请耐心等待；咨询请致电商家</li><li><strong>特别提示：</strong></li> <li> 本团购单请您大厅堂食</li> <li>拉手券不与店内其他优惠同享</li></ul> <ul id="tips_editor" class=" list-paddingleft-2"> <li><p>到店消费每张拉手券最多可抵用现金100元，拉手券可叠加使用</p></li> <li><p>每张拉手券仅限现金抵用一次，如拉手券抵用金额不足支付费用，需另付现金补齐，如拉手券抵用金额有剩余，剩余金额不兑现，不找零，不退还</p></li> </ul></div> <p><span ></span></p><div id="kfzs" class="new_h2_1">
         * </div>
         * updatedAt : 2017-04-03 12:59:28
         */

        private String address;
        private int commentCount;
        private String details;
        private String distance;
        private String goodsId;
        private boolean isFavorite;
        private String product;
        private int ratingBar;
        private int score;
        private String time;
        private String tips;
        private List<String> imagesUrl;
        private List<Boolean> service;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public boolean isIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public int getRatingBar() {
            return ratingBar;
        }

        public void setRatingBar(int ratingBar) {
            this.ratingBar = ratingBar;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public List<String> getImagesUrl() {
            return imagesUrl;
        }

        public void setImagesUrl(List<String> imagesUrl) {
            this.imagesUrl = imagesUrl;
        }

        public List<Boolean> getService() {
            return service;
        }

        public void setService(List<Boolean> service) {
            this.service = service;
        }
    }
}
