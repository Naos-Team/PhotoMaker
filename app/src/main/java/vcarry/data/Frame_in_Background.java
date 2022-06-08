package vcarry.data;

public class Frame_in_Background {
    float hor_bias;
    float verti_bias;
    float height_per;
    int color;
    String ratio;

    public Frame_in_Background(float hor_bias, float verti_bias, float height_per, String ratio, int color) {
        this.hor_bias = hor_bias;
        this.verti_bias = verti_bias;
        this.height_per = height_per;
        this.color = color;
        this.ratio = ratio;
    }

    public float getHor_bias() {
        return hor_bias;
    }

    public void setHor_bias(float hor_bias) {
        this.hor_bias = hor_bias;
    }

    public float getVerti_bias() {
        return verti_bias;
    }

    public void setVerti_bias(float verti_bias) {
        this.verti_bias = verti_bias;
    }

    public float getHeight_per() {
        return height_per;
    }

    public void setHeight_per(float height_per) {
        this.height_per = height_per;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
