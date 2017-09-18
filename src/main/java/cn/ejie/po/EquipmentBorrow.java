package cn.ejie.po;

public class EquipmentBorrow {
    private String borrowId;
    private String eqId;
    private String state;
    private String useBy;
    private String doTime;
    private String useByDepart;

    @Override
    public String toString() {
        return "EquipmentBorrow{" +
                "borrowId='" + borrowId + '\'' +
                ", eqId='" + eqId + '\'' +
                ", state='" + state + '\'' +
                ", useBy='" + useBy + '\'' +
                ", doTime='" + doTime + '\'' +
                ", useByDepart='" + useByDepart + '\'' +
                '}';
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUseBy() {
        return useBy;
    }

    public void setUseBy(String useBy) {
        this.useBy = useBy;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public String getUseByDepart() {
        return useByDepart;
    }

    public void setUseByDepart(String useByDepart) {
        this.useByDepart = useByDepart;
    }
}
