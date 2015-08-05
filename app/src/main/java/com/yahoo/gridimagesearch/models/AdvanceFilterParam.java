package com.yahoo.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by Mars on 2015/8/5.
 */
public class AdvanceFilterParam implements Serializable {
    private String size = "any";
    private String color = "any";
    private String type = "any";
    private String siteFilter;

    private int sizePosit;
    private int colorPosit;
    private int typePosit;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public int getSizePosit() {
        return sizePosit;
    }

    public void setSizePosit(int sizePosit) {
        this.sizePosit = sizePosit;
    }

    public int getColorPosit() {
        return colorPosit;
    }

    public void setColorPosit(int colorPosit) {
        this.colorPosit = colorPosit;
    }

    public int getTypePosit() {
        return typePosit;
    }

    public void setTypePosit(int typePosit) {
        this.typePosit = typePosit;
    }

    public String getQtyStr(String query , int offset){
        String serchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8&label="+offset+"&start="+(8*offset);
        serchUrl += getSizeParam();
        serchUrl += getColorParam();
        serchUrl += getTypeParam();
        serchUrl += getSiteFiter();


        return serchUrl;
    }

    /**
     *    <item>any</item>
     *    <item>small</item>
     *    <item>medium</item>
     *    <item>large</item>
     *    <item>extra-large</item>
     * @return
     */
    private String getSizeParam(){
        String param = "&imgsz=";
        if("any".equals(size)){
            return "";
        }else if("large".equals(size)){
            return param + "xlarge";
        }else{
            return param + size;
        }
    }

    private String getColorParam(){
        String param = "&imgcolor=";
        if("any".equals(color)){
            return "";
        }else{
            return param + color;
        }
    }

    /**
     * <item>any</item>
     <item>faces</item>
     <item>photo</item>
     <item>clip art</item>
     <item>line art</item>
     * @return
     */
    private String getTypeParam(){
        String param = "&imgtype=";
        if("any".equals(type)){
            return "";
        }else if("clip art".equals(type)){
            return param + "clipart";
        }else if("line art".equals(type)){
            return param + "lineart";
        }else{
            return param + color;
        }
    }

    private String getSiteFiter(){
        String param = "&as_sitesearch=";
        if(siteFilter == null || siteFilter.trim().length() == 0){
            return "";
        }else{
            return param + siteFilter;
        }
    }
}
