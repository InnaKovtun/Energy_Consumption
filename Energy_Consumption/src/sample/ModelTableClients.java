package sample;

public class ModelTableClients {

    String name, city, street, phone;
    int debt, buildNum, flatNum;

    public ModelTableClients() {
    }

    public ModelTableClients(String name, String city, String street, int buildNum, int flatNum, String phone, int debt) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.phone = phone;
        this.debt = debt;
        this.buildNum = buildNum;
        this.flatNum = flatNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }

    public int getFlatNum() {
        return flatNum;
    }

    public void setFlatNum(int flatNum) {
        this.flatNum = flatNum;
    }
}
