package sample;

public class ModelTableCounters {
    String firstDate, endDate;
    int idReading, idController, outlay, firstReading, endReading;

    public ModelTableCounters(int idReading, int idController,String firstDate, String endDate, int firstReading, int endReading, int outlay) {
        this.idReading = idReading;
        this.idController = idController;
        this.firstDate = firstDate;
        this.endDate = endDate;
        this.firstReading = firstReading;
        this.endReading = endReading;
        this.outlay = outlay;

    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIdReading() {
        return idReading;
    }

    public void setIdReading(int idReading) {
        this.idReading = idReading;
    }

    public int getIdController() {
        return idController;
    }

    public void setIdController(int idController) {
        this.idController = idController;
    }

    public int getOutlay() {
        return outlay;
    }

    public void setOutlay(int outlay) {
        this.outlay = outlay;
    }

    public int getFirstReading() {
        return firstReading;
    }

    public void setFirstReading(int firstReading) {
        this.firstReading = firstReading;
    }

    public int getEndReading() {
        return endReading;
    }

    public void setEndReading(int endReading) {
        this.endReading = endReading;
    }
}
