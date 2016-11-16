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

    public Usage() {
        id = DBUtil.getLatestId(Usage.class) + 1;
        title = "";
        type = -1;
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
}
