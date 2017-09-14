package cn.ejie.po;

/**
 * 设备维修记录的实体类
 */
public class FixedLog {
    private String fixId;      //维修记录id
    private String fixTime;   //维修记录时间
    private String fixType;   //维修记录类型
    private String fixDetail;//维修记录详情
    private String eqId;      //维修设备id

    public String getFixId() {
        return fixId;
    }

    public void setFixId(String fixId) {
        this.fixId = fixId;
    }

    public String getFixTime() {
        return fixTime;
    }

    public void setFixTime(String fixTime) {
        this.fixTime = fixTime;
    }

    public String getFixType() {
        return fixType;
    }

    public void setFixType(String fixType) {
        this.fixType = fixType;
    }

    public String getFixDetail() {
        return fixDetail;
    }

    public void setFixDetail(String fixDetail) {
        this.fixDetail = fixDetail;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    @Override
    public String toString() {
        return "FixedLog{" +
                "fixId='" + fixId + '\'' +
                ", fixTime='" + fixTime + '\'' +
                ", fixType='" + fixType + '\'' +
                ", fixDetail='" + fixDetail + '\'' +
                ", eqId='" + eqId + '\'' +
                '}';
    }
}
