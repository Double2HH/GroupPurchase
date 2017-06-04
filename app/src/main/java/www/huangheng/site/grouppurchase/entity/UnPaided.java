package www.huangheng.site.grouppurchase.entity;

import java.util.List;

/**
 * 待付款
 */

public class UnPaided {


    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * amount : 1
         * createdAt : 2017-04-15 23:16:52
         * goodsObjectId : {"__type":"Object","className":"GoodsListInfo","createdAt":"2017-04-02 08:44:31","goodsId":1111000,"imageUrl":"https://d1.lashouimg.com/cms/M00/BD/FD/CqgBJlUTvviAK6XfAAB_b7B_tEQ645.jpg","objectId":"JAgcGGGI","priceAfter":95,"product":"靓饮速递","updatedAt":"2017-04-03 10:16:19"}
         * objectId : YSRfEEEQ
         * updatedAt : 2017-04-16 12:23:43
         * username : Hhhhhh
         */

        private String objectId;

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        private int amount;
        private GoodsObjectIdBean goodsObjectId;
        private String username;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public GoodsObjectIdBean getGoodsObjectId() {
            return goodsObjectId;
        }

        public void setGoodsObjectId(GoodsObjectIdBean goodsObjectId) {
            this.goodsObjectId = goodsObjectId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public static class GoodsObjectIdBean {
            /**
             * __type : Object
             * className : GoodsListInfo
             * createdAt : 2017-04-02 08:44:31
             * goodsId : 1111000
             * imageUrl : https://d1.lashouimg.com/cms/M00/BD/FD/CqgBJlUTvviAK6XfAAB_b7B_tEQ645.jpg
             * objectId : JAgcGGGI
             * priceAfter : 95
             * product : 靓饮速递
             * updatedAt : 2017-04-03 10:16:19
             */

            private int goodsId;
            private String imageUrl;
            private int priceAfter;
            private String product;

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

            public int getPriceAfter() {
                return priceAfter;
            }

            public void setPriceAfter(int priceAfter) {
                this.priceAfter = priceAfter;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }
        }
    }
}
