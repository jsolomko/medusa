package info.fandroid.quizapp.quizapplication.models.staff;

public class Staff {
    String name;
    String desc;
    int srcImageId;

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

    public int getSrcImageId() {
        return srcImageId;
    }

    public void setSrcImageId(int srcImageId) {
        this.srcImageId = srcImageId;
    }

    public Staff(String name, String desc, int srcImageId) {
        this.name = name;
        this.desc = desc;
        this.srcImageId = srcImageId;
    }
}
