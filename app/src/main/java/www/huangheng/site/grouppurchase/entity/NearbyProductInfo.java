package www.huangheng.site.grouppurchase.entity;

import java.util.List;

/**
 * 附近商品
 */

public class NearbyProductInfo {


    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * classify : 饮料甜品
         * commentCount : 0
         * createdAt : 2017-04-02 08:44:31
         * freeAppointment : true
         * geoLocation : {"__type":"GeoPoint","latitude":23.049122,"longitude":113.391023}
         * goodsId : 1111000
         * imageUrl : https://d1.lashouimg.com/cms/M00/BD/FD/CqgBJlUTvviAK6XfAAB_b7B_tEQ645.jpg
         * isGroup : true
         * location : 南沙旧镇
         * objectId : JAgcGGGI
         * priceAfter : 95
         * priceBefore : 100
         * product : 靓饮速递
         * score : 0
         * sold : 11571
         * title : 现金抵用一次，可叠加使用，全场通用
         * updatedAt : 2017-05-23 22:04:17
         */

        private String classify;
        private int commentCount;
        private GeoLocationBean geoLocation;
        private int goodsId;
        private String imageUrl;
        private boolean isGroup;
        private String location;
        private String objectId;
        private int priceAfter;
        private int priceBefore;
        private String product;
        private int score;
        private int sold;
        private String title;

        //自定义
        private String distance;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public GeoLocationBean getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(GeoLocationBean geoLocation) {
            this.geoLocation = geoLocation;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isIsGroup() {
            return isGroup;
        }

        public void setIsGroup(boolean isGroup) {
            this.isGroup = isGroup;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public int getPriceAfter() {
            return priceAfter;
        }

        public void setPriceAfter(int priceAfter) {
            this.priceAfter = priceAfter;
        }

        public int getPriceBefore() {
            return priceBefore;
        }

        public void setPriceBefore(int priceBefore) {
            this.priceBefore = priceBefore;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSold() {
            return sold;
        }

        public void setSold(int sold) {
            this.sold = sold;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class GeoLocationBean {
            /**
             * __type : GeoPoint
             * latitude : 23.049122
             * longitude : 113.391023
             */

            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }
}
