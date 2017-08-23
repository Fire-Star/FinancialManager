package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/23.
 */
public class City {
    private String cityID;//城市ID
    private String city;//城市

    public City() {
    }

    public City(String cityID, String city) {
        this.cityID = cityID;
        this.city = city;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityID='" + cityID + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
