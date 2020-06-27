package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class DataBase {
    private final String HOST = "DESKTOP-B43OQ71";
    private final String PORT = "3306";
    private final String DB_NAME = "energy_consumption";
    private final String LOGIN = "DESKTOP-B43OQ71/zoyas";
    private final String PASS = "root";

    private Connection dbConn = null;

    //подключение к базе данных
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:sqlserver://localhost;databaseName=master;integratedSecurity=true";
        Connection dbConn = DriverManager.getConnection(connStr);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    //получаем данные с таблицы о показаниях счётчика
    public List<ModelTableCounters> getCounterReading() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM counter_reading";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        ObservableList<ModelTableCounters> obListReadings = FXCollections.observableArrayList();
        while (res.next()){
            obListReadings.add(new ModelTableCounters(
                    res.getInt("idCounter"),
                    res.getInt("idController"),
                    res.getString("initialDate"),
                    res.getString("endDate"),
                    res.getInt("initialReading"),
                    res.getInt("endReading"),
                    res.getInt("outlay")));
        }
        return obListReadings;
    }

    //получаем данные с таблицы о счетах
    public List<ModelTableAccounts> getAccounts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM accounts";

        ObservableList<ModelTableAccounts> obListAccounts = FXCollections.observableArrayList();

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        while (res.next()){
            obListAccounts.add(new ModelTableAccounts(
                    res.getInt("idCient"),
                    res.getInt("idReading"),
                    res.getString("clearanceDate"),
                    res.getInt("total"),
                    res.getString("state")));
        }

        return obListAccounts;
    }

    //получаем данные с таблицы о клиентах
    public List<ModelTableClients> getClients() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM clients";
        ObservableList<ModelTableClients> obListClients = FXCollections.observableArrayList();

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        while (res.next()){
            obListClients.add(new ModelTableClients(
                    res.getString("clientName"),
                    res.getString("city"),
                    res.getString("street"),
                    res.getInt("buildingNum"),
                    res.getInt("flatNum"),
                    res.getString("phone"),
                    res.getInt("debt")));
        }

        return obListClients;
    }

    //добавление данных о клиенте
    public boolean addClient(int clientID, String name, String city, String street, int buildNum, int flatNum, String phone, int debt) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO clients (idClient, clientName, city, street, buildingNum, flatNum, phone, debt) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT TOP 1 * FROM clients WHERE clientName = '" + name + "' AND city = '" + city + "' AND street = '" + street + "'");
        if(res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, clientID);
        prSt.setString(2, name);
        prSt.setString(3, city);
        prSt.setString(4, street);
        prSt.setInt(5, buildNum);
        prSt.setInt(6, flatNum);
        prSt.setString(7, phone);
        prSt.setInt(8, debt);
        prSt.executeUpdate();
        return true;
    }

    //добавление данных о показаниях счётчика
    public boolean addReading(int readingID, int counterID, int controllerID, String initialDate, String endDate, int initialReading, int endReading, int outlay) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO counter_reading (idReading, idCounter, idController, initialDate, endDate, initialReading, endReading, outlay) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT TOP 1 * FROM counter_reading WHERE idReading = '" + readingID + "'");
        if(res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, readingID);
        prSt.setInt(2, counterID);
        prSt.setInt(3, controllerID);
        prSt.setString(4, initialDate);
        prSt.setString(5, endDate);
        prSt.setInt(6, initialReading);
        prSt.setInt(7, endReading);
        prSt.setInt(8, outlay);
        prSt.executeUpdate();
        return true;
    }

    //добавление данных о счетах
    public boolean addAccount(int idAccount, int idClient, int idReading, String clearanceDate, int total, String state) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO accounts (idAccount, idCient, idReading, clearanceDate, total, state) VALUES(?, ?, ?, ?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT TOP 1 * FROM accounts WHERE idAccount = '" + idAccount + "'");
        if(res.next())
            return false;

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, idAccount);
        prSt.setInt(2, idClient);
        prSt.setInt(3, idReading);
        prSt.setString(4, clearanceDate);
        prSt.setInt(5, total);
        prSt.setString(6, state);

        prSt.executeUpdate();
        return true;
    }

//запрос №1
    public ResultSet firstQuery (int clientID) throws SQLException, ClassNotFoundException {
        String sql2 = "SELECT total FROM accounts WHERE idCient = '"+ clientID + "'";
        Statement statement2 = getDbConnection().createStatement();
        ResultSet res2 = statement2.executeQuery(sql2);
        return res2;
    }

//запрос №2
    public ResultSet secondQuery () throws SQLException, ClassNotFoundException {
        String sql = "SELECT outlay FROM counter_reading";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

//запрос №3
    public ResultSet thirdQuery() throws SQLException, ClassNotFoundException {
        String sql = "SELECT idCounter, initialDate, endDate, initialReading, endReading FROM counter_reading WHERE initialDate LIKE '%05%'";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

//запрос №4
    public ResultSet fourthQuery(int clientID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idReading, clearanceDate, total, state FROM accounts WHERE idCient = '" + clientID + "'";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }

//отчёт №1
    public ResultSet reportOne() throws SQLException, ClassNotFoundException {
        String sql = "SELECT clientName, debt, phone FROM clients WHERE debt > 0";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        return res;
    }

    //отчёт №2 счёт пользователя
    public int[] reportTwo(String clientName) throws SQLException, ClassNotFoundException {
        String sqlIdClient = "SELECT idClient FROM clients WHERE clientName = '" + clientName + "'";
        Statement statement = getDbConnection().createStatement();
        ResultSet resIdClient = statement.executeQuery(sqlIdClient);
        int clientId = 0;
        while (resIdClient.next()) {
            clientId = resIdClient.getInt(1);
        }

        String sqlIdReading = "SELECT idReading FROM accounts WHERE idCient = '" + clientId + "'";
        ResultSet resIdReading = statement.executeQuery(sqlIdReading);
        int readingId = 0;
        while (resIdReading.next()) {
            readingId = resIdReading.getInt(1);
        }

        String sqlOutLay = "SELECT outlay FROM counter_reading WHERE idReading = '" + readingId + "'";
        ResultSet resOutLay = statement.executeQuery(sqlOutLay);
        int clientOutlay = 0;
        while (resOutLay.next()) {
            clientOutlay = resOutLay.getInt(1);
        }

        int tariffAmount = 0, VAT = 0;

        if (clientOutlay > 100){
            String sqlTariffAmount = "SELECT amount FROM tariffs WHERE idTariff = 19";
            ResultSet resTariffAmount = statement.executeQuery(sqlTariffAmount);
            while (resTariffAmount.next()) {
                tariffAmount = resTariffAmount.getInt(1);
            }

            String sqlVAT = "SELECT VATpercentage FROM tariffs WHERE idTariff = 19";
            ResultSet resVAT = statement.executeQuery(sqlVAT);
            while (resVAT.next()) {
                VAT = resVAT.getInt(1);
            }
        } else {
            String sqlTariffAmount = "SELECT amount FROM tariffs WHERE idTariff = 18";
            ResultSet resTariffAmount = statement.executeQuery(sqlTariffAmount);
            tariffAmount = Integer.parseInt(String.valueOf(resTariffAmount));
            while (resTariffAmount.next()) {
                tariffAmount = resTariffAmount.getInt(1);
            }

            String sqlVAT = "SELECT VATpercentage FROM tariffs WHERE idTariff = 18";
            ResultSet resVAT = statement.executeQuery(sqlVAT);
            while (resVAT.next()) {
                VAT = resVAT.getInt(1);
            }
        }
        //кол-во использованной электроэнергии, цена тарифа, ПДВ, clientID
        int[] report2 = new int[4];
        report2[0] = clientOutlay;
        report2[1] = tariffAmount;
        report2[2] = VAT;
        report2[3] = clientId;

        return report2;
    }

    //добавление данных о счетах
    public boolean updateClient(int clientID, String name, String city, String street, int buildNum, int flatNum, String phone, int debt) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE clients SET idClient = ?, clientName = ?, city = ?, street = ?, buildingNum = ?, flatNum = ?, phone = ? debt = ? WHERE idClient = " + clientID + "";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, clientID);
        prSt.setString(2, name);
        prSt.setString(3, city);
        prSt.setString(4, street);
        prSt.setInt(5, buildNum);
        prSt.setInt(6, flatNum);
        prSt.setString(7, phone);
        prSt.setInt(8, debt);

        prSt.executeUpdate();
        return true;
    }

    public boolean updateAcount(int idAccount, int idClient, int idReading, String clearanceDate, int total, String state) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE accounts SET idAccount = ?, idCient = ?, idReading = ?, clearanceDate = ?, total = ?, state = ? WHERE idAccount = " + idAccount + "";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, idAccount);
        prSt.setInt(2, idClient);
        prSt.setInt(3, idReading);
        prSt.setString(4, clearanceDate);
        prSt.setInt(5, total);
        prSt.setString(6, state);
        prSt.executeUpdate();
        return true;
    }

    public boolean updateReading(int readingID, int counterID, int controllerID, String initialDate, String endDate, int initialReading, int endReading, int outlay) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE counter_reading SET idReading = ?, idCounter = ?, idController = ?, initialDate = ?, endDate = ?, initialReading = ?, endReading = ?, outlay = ? WHERE idReading = "+ readingID +"";

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, readingID);
        prSt.setInt(2, counterID);
        prSt.setInt(3, controllerID);
        prSt.setString(4, initialDate);
        prSt.setString(5, endDate);
        prSt.setInt(6, initialReading);
        prSt.setInt(7, endReading);
        prSt.setInt(8, outlay);
        prSt.executeUpdate();
        return true;
    }
}
