package vcarry.data;

public class Image_in_Background {
    int left_per;
    int top_per;
    int height_per;
    int width_ratio;
    int height_ratio;

    public Image_in_Background(int left_per, int top_per, int height_per, int width_ratio, int height_ratio) {
        this.left_per = left_per;
        this.top_per = top_per;
        this.height_per = height_per;
        this.width_ratio = width_ratio;
        this.height_ratio = height_ratio;
    }

    public int getLeft_per() {
        return left_per;
    }

    public void setLeft_per(int left_per) {
        this.left_per = left_per;
    }

    public int getTop_per() {
        return top_per;
    }

    public void setTop_per(int top_per) {
        this.top_per = top_per;
    }

    public int getHeight_per() {
        return height_per;
    }

    public void setHeight_per(int height_per) {
        this.height_per = height_per;
    }

    public int getWidth_ratio() {
        return width_ratio;
    }

    public void setWidth_ratio(int width_ratio) {
        this.width_ratio = width_ratio;
    }

    public int getHeight_ratio() {
        return height_ratio;
    }

    public void setHeight_ratio(int height_ratio) {
        this.height_ratio = height_ratio;
    }
}
