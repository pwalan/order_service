package cn.edu.bupt.order_service.domain;


import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{

    private int id;

    private String tradeNo;

    private String productName;

    private int price;

    private Date createTime;

    private int userId;

    private String userName;

    public Order() {
    }

    public Order(int id, String tradeNo, String productName, int price, Date createTime, int userId, String userName) {
        this.id = id;
        this.tradeNo = tradeNo;
        this.productName = productName;
        this.price = price;
        this.createTime = createTime;
        this.userId = userId;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
