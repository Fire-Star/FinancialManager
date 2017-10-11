package cn.ejie.po;

public class SystemLog {

    private String logID;
    private String userID;
    private String module;
    private String method;
    private String userIP;
    private String date;
    private String parament;
    private String commit;

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParament() {
        return parament;
    }

    public void setParament(String parament) {
        this.parament = parament;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "SystemLog{" +
                "logID='" + logID + '\'' +
                ", userID='" + userID + '\'' +
                ", module='" + module + '\'' +
                ", method='" + method + '\'' +
                ", userIP='" + userIP + '\'' +
                ", date='" + date + '\'' +
                ", parament='" + parament + '\'' +
                ", commit='" + commit + '\'' +
                '}';
    }
}
