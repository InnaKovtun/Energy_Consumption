package sample;

public class ModelTableAccounts {
    String date, state;
    int idClient, idReading, sum;

    public ModelTableAccounts(int idClient, int idReading, String date, int sum, String state) {
        this.idClient = idClient;
        this.idReading = idReading;
        this.date = date;
        this.state = state;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdReading() {
        return idReading;
    }

    public void setIdReading(int idReading) {
        this.idReading = idReading;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
