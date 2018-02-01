package com.ray.demo.waterfall;

/**
 * 图片数据实体
 * @author ray
 * @date 2018/02/01
 */

public class ImageBean {
    private String imgUrl;
    private int width;
    private int height;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageBean(String url, int width, int height) {
        this.imgUrl = url;
        this.width = width;
        this.height = height;
    }
}
