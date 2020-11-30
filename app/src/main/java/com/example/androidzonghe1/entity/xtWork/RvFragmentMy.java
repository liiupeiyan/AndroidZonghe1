package com.example.androidzonghe1.entity.xtWork;

public class RvFragmentMy {
    private int ImgRes;
    private String itemName;
    private int iconRes;

    public RvFragmentMy(int imgRes, String itemName, int iconRes) {
        ImgRes = imgRes;
        this.itemName = itemName;
        this.iconRes = iconRes;
    }

    public RvFragmentMy() {
    }

    public int getImgRes() {
        return ImgRes;
    }

    public void setImgRes(int imgRes) {
        ImgRes = imgRes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    @Override
    public String toString() {
        return "RvFragmentMy{" +
                "ImgRes=" + ImgRes +
                ", itemName='" + itemName + '\'' +
                ", iconRes=" + iconRes +
                '}';
    }
}
