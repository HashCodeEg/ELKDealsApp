package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreHomeResponse {
    @SerializedName("top")
    @Expose
    private List<Top> top = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public String[] getSliderUrls() {
        if(slider==null||slider.size()==0)
            return null;
        String[] sliders=new String[slider.size()];
        for(int i=0;i<slider.size();i++)
            sliders[i]=slider.get(i).getMsg();
        return sliders;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

}
