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

    private String name;
    private int times;

    private int type;

    public Usage() {
        id = DBUtil.getLatestId(Usage.class) + 1;
        name = "";
        type = -1;
        times = 0;
    }

    public void updateUsage(Usage usage) {
        name = usage.getName();
        type = usage.getType();
        times = usage.getTimes();
    }

    public void renameUsage(String name) {
        this.name = name;
    }

    public void utilized() {
        times += 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Usage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", times=" + times +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Usage)) {
            return false;
        }
        return id == ((Usage) obj).getId();
    }
}
