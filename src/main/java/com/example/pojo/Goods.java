package com.example.pojo;

/**
 * 商品实体类
 */
public class Goods {

    /**
     * 商品id
     * 数据库自增，无需初始化
     */
    private int id;
    // 商品名
    private String name;
    // 描述信息
    private String desc;
    // 商品分类(后续迭代是否拆分成新类？)
    private String cate;
    // 商品价格
    private float price;
    // 图片URl地址
    private String imgSrc;
    // 默认图片地址
    private static String defaultImageSrc = "";

    /**
     *  带图片地址的构造函数
     * @param name
     * @param desc
     * @param cate
     * @param price
     * @param imgSrc
     */
    public Goods(String name, String desc, String cate, float price, String imgSrc) {
        this.name = name;
        this.desc = desc;
        this.cate = cate;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    /**
     * 使用默认图片地址的构造函数
     * @param name
     * @param desc
     * @param cate
     * @param price
     */
    public Goods(String name, String desc, String cate, float price) {
        this.name = name;
        this.desc = desc;
        this.cate = cate;
        this.price = price;
        this.imgSrc = defaultImageSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public static String getDefaultImageSrc() {
        return defaultImageSrc;
    }

    public static void setDefaultImageSrc(String defaultImageSrc) {
        Goods.defaultImageSrc = defaultImageSrc;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", cate='" + cate + '\'' +
                ", price=" + price +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
