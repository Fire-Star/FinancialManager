package cn.ejie.po;

public class EquipmentState {
    private String eqStateID;//状态ID
    private String state;//状态

    @Override
    public String toString() {
        return "EquipmentState{" +
                "eqStateID='" + eqStateID + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getEqStateID() {
        return eqStateID;
    }

    public void setEqStateID(String eqStateID) {
        this.eqStateID = eqStateID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
