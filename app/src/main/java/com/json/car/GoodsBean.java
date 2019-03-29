package com.json.car;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类
 */

public class GoodsBean implements Serializable{
    private static final long serialVersionUID = 1254388575271277357L;

    private String shopId;//店铺id
    private String shopName;//店铺名称
    private boolean isGroupSelected;//父控件group是否被选中
    private List<GoodsDetailsBean> goods;

    public GoodsBean(String shopId, String shopName, List<GoodsDetailsBean> goods) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.goods = goods;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public void setGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
    }

    public List<GoodsDetailsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDetailsBean> goods) {
        this.goods = goods;
    }



    public static class GoodsDetailsBean implements Serializable{
        private static final long serialVersionUID = 897069296215760196L;

        private String goodsId;//商品id
        //private String goodsUrl;//商品的图片地址
        private int goodsUrl;//商品的图片地址
        private String goodsName;//商品的名称
        private String goodsPrice;//商品的价格
        private String goodsOriPrice;//商品原价
        private String goodsNum;//商品数量
        private boolean isChildSelected;//child是否被选中

        public GoodsDetailsBean(int goodsUrl, String goodsName, String goodsOriPrice, String goodsPrice, String goodsNum) {
            this.goodsUrl = goodsUrl;
            this.goodsName = goodsName;
            this.goodsOriPrice = goodsOriPrice;
            this.goodsPrice = goodsPrice;
            this.goodsNum = goodsNum;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsUrl() {
            return goodsUrl;
        }

        public void setGoodsUrl(int goodsUrl) {
            this.goodsUrl = goodsUrl;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsOriPrice() {
            return goodsOriPrice;
        }

        public void setGoodsOriPrice(String goodsOriPrice) {
            this.goodsOriPrice = goodsOriPrice;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public boolean isChildSelected() {
            return isChildSelected;
        }

        public void setChildSelected(boolean childSelected) {
            isChildSelected = childSelected;
        }
    }
}
