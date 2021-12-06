package com.example.cscb07_project.database;

/**
 * 单条的数据库记录
 */
public class ShoppingRecord {
    private String account = "";//账号
    private int recordId = -1;//记录的id（主键）
    private ProductInfo productInfo = null;//商品信息
    private int productAmount = 0;//该商品的数量

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
