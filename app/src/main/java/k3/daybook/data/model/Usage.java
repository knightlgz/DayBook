package k3.daybook.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class Usage extends RealmObject{

    @PrimaryKey
    private long id;
    private String title;
    private int type;
    private int times;

    public Usage() {
        id = DBUtil.getLatestId(Usage.class) + 1;
        title = "";
        type = -1;
        times = 0;
    }

    public void updateUsage(Usage usage) {
        title = usage.getTitle();
        type = usage.getType();
        times = usage.getTimes();
    }

    public void renameUsage(String name) {
        title = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
