package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/23.
 */
public class Department {
    private String id;//部门ID
    private String city;//城市
    private String department;//城市下的部门

    public Department() {
    }

    public Department(String id, String city, String department) {
        this.id = id;
        this.city = city;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
