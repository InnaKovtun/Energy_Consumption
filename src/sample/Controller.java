package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ModelTableClients> table_clients;

    @FXML
    private TableColumn<ModelTableClients, String> col_pibClient;

    @FXML
    private TableColumn<ModelTableClients, String> col_clientCity;

    @FXML
    private TableColumn<ModelTableClients, String> col_clientStreet;

    @FXML
    private TableColumn<ModelTableClients, Integer> col_clientBuilding;

    @FXML
    private TableColumn<ModelTableClients, Integer> col_clientFlat;

    @FXML
    private TableColumn<ModelTableClients, String> col_clientPhone;

    @FXML
    private TableColumn<ModelTableClients, Integer> col_clientDebt;

    @FXML
    private Button btn_sortAlpha;

    @FXML
    private Button btn_sortByDebt;

    @FXML
    private Button btn_filtrDebt;

    @FXML
    private Button btn_filtrCity;

    @FXML
    private TextField field_clientName;

    @FXML
    private TextField field_clientID;

    @FXML
    private TextField field_clientCity;

    @FXML
    private TextField field_clientStreet;

    @FXML
    private TextField field_clientBuilding;

    @FXML
    private TextField field_clientFlat;

    @FXML
    private TextField field_clientPhone;

    @FXML
    private TextField field_clientDebt;

    @FXML
    private Button btn_addClient;

    @FXML
    private TextField field_clientByPhone;

    @FXML
    private Button btn_findClient;

    @FXML
    private TableView<ModelTableCounters> table_readings;

    @FXML
    private TableColumn<ModelTableCounters, Integer> col_idReading;

    @FXML
    private TableColumn<ModelTableCounters, Integer> col_idController;

    @FXML
    private TableColumn<ModelTableCounters, String> col_FirstDate;

    @FXML
    private TableColumn<ModelTableCounters, String> col_EndDate;

    @FXML
    private TableColumn<ModelTableCounters, Integer> col_firstRead;

    @FXML
    private TableColumn<ModelTableCounters, Integer> col_endRead;

    @FXML
    private TableColumn<ModelTableCounters, Integer> col_outlay;

    @FXML
    private Button btn_sortByDate;

    @FXML
    private Button btn_sortByOutlay;

    @FXML
    private Button btn_filtrOutlay;

    @FXML
    private Button btn_filtrMay;

    @FXML
    private Button btn_findCounter;

    @FXML
    private TextField field_firstReading;

    @FXML
    private TextField field_idReading1;

    @FXML
    private TextField field_idController;

    @FXML
    private TextField field_datefirst;

    @FXML
    private TextField field_datesecond;

    @FXML
    private TextField field_fisrReadingAdd;

    @FXML
    private TextField field_lastReading;

    @FXML
    private TextField field_counterID;

    @FXML
    private Button btn_addReading;

    @FXML
    private TableView<ModelTableAccounts> table_accounts;

    @FXML
    private TableColumn<ModelTableAccounts, Integer> col_idClient;

    @FXML
    private TableColumn<ModelTableAccounts, Integer> col_idReading2;

    @FXML
    private TableColumn<ModelTableAccounts, String> col_dateRegistr;

    @FXML
    private TableColumn<ModelTableAccounts, Integer> col_sum;

    @FXML
    private TableColumn<ModelTableAccounts, String> col_state;

    @FXML
    private Button btn_sortByState;

    @FXML
    private Button btn_sortBySum;

    @FXML
    private Button btn_filtrPay;

    @FXML
    private Button btn_filtrSum;

    @FXML
    private TextField field_idReadingfind;

    @FXML
    private Button btn_findReading;

    @FXML
    private TextField field_idClientAdd;

    @FXML
    private TextField field_idAccount;

    @FXML
    private TextField field_idReadingAdd;

    @FXML
    private TextField field_dateRegistr;

    @FXML
    private TextField field_sumAdd;

    @FXML
    private TextField field_stateAdd;

    @FXML
    private Button btn_countAdd;

    @FXML
    private TextArea field_resultQuery;

    @FXML
    private Button btn_query1;

    @FXML
    private Button btn_query3;

    @FXML
    private Button btn_query2;

    @FXML
    private Button btn_query4;

    @FXML
    private TextField field_clientName_query1;

    @FXML
    private TextField field_counterName_query2;

    @FXML
    private TextField field_clientName_query4;

    @FXML
    private Button btn_report1;

    @FXML
    private Button btn_report2;

    @FXML
    private TextArea field_report1;

    @FXML
    private TextArea field_report2;

    @FXML
    private TextField doc1_field;

    @FXML
    private TextField doc2_field;

    @FXML
    private TextField fieldReport_clientName;

    DataBase db = new DataBase();

    ObservableList obListClients;

    {
        try {
            obListClients = (ObservableList) db.getClients();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        //выводим информацию с базы в таблицу о клиентах
        col_pibClient.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_clientCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_clientStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        col_clientBuilding.setCellValueFactory(new PropertyValueFactory<>("buildNum"));
        col_clientFlat.setCellValueFactory(new PropertyValueFactory<>("flatNum"));
        col_clientPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_clientDebt.setCellValueFactory(new PropertyValueFactory<>("debt"));
        ObservableList obListClients = (ObservableList) db.getClients();
        table_clients.setItems(obListClients);

        //выводим информацию с базы в таблицу о счетах
        col_idClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        col_idReading2.setCellValueFactory(new PropertyValueFactory<>("idReading"));
        col_dateRegistr.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_sum.setCellValueFactory(new PropertyValueFactory<>("sum"));
        col_state.setCellValueFactory(new PropertyValueFactory<>("state"));
        ObservableList obListAccounts = (ObservableList) db.getAccounts();
        table_accounts.setItems(obListAccounts);

        //выводим информацию с базы в таблицу о показаниях счётчиков
        col_idReading.setCellValueFactory(new PropertyValueFactory<>("idReading"));
        col_idController.setCellValueFactory(new PropertyValueFactory<>("idController"));
        col_FirstDate.setCellValueFactory(new PropertyValueFactory<>("firstDate"));
        col_EndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col_firstRead.setCellValueFactory(new PropertyValueFactory<>("firstReading"));
        col_endRead.setCellValueFactory(new PropertyValueFactory<>("endReading"));
        col_outlay.setCellValueFactory(new PropertyValueFactory<>("outlay"));
        ObservableList obListCounter = (ObservableList) db.getCounterReading();
        table_readings.setItems(obListCounter);


        //добавление данных о клиенте
        btn_addClient.setOnAction(actionEvent -> {
            field_clientID.setStyle("-fx-border-color: #fafafa");
            field_clientName.setStyle("-fx-border-color: #fafafa");
            field_clientCity.setStyle("-fx-border-color: #fafafa");
            field_clientStreet.setStyle("-fx-border-color: #fafafa");
            field_clientBuilding.setStyle("-fx-border-color: #fafafa");
            field_clientFlat.setStyle("-fx-border-color: #fafafa");
            field_clientPhone.setStyle("-fx-border-color: #fafafa");
            field_clientDebt.setStyle("-fx-border-color: #fafafa");

            btn_addClient.setText("Додати");

            //проверка ввода
            if (field_clientID.getCharacters().length() == 0) {
                field_clientID.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientName.getCharacters().length() <= 1) {
                field_clientName.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientCity.getCharacters().length() <= 2) {
                field_clientCity.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientStreet.getCharacters().length() <= 3) {
                field_clientStreet.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientBuilding.getCharacters().length() == 0) {
                field_clientBuilding.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientFlat.getCharacters().length() == 0) {
                field_clientFlat.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientPhone.getCharacters().length() != 10) {
                field_clientPhone.setStyle("-fx-border-color: red");
                return;
            } else if (field_clientDebt.getCharacters().length() == 0) {
                field_clientDebt.setStyle("-fx-border-color: red");
                return;
            }


            try {
                boolean isLitAdded = db.addClient(
                        Integer.parseInt(field_clientID.getText()),
                        field_clientName.getText(),
                        field_clientCity.getText(),
                        field_clientStreet.getText(),
                        Integer.parseInt(field_clientBuilding.getText()),
                        Integer.parseInt(field_clientFlat.getText()),
                        field_clientPhone.getText(),
                        Integer.parseInt(field_clientDebt.getText()));

                if (isLitAdded) {
                    field_clientID.setText("");
                    field_clientName.setText("");
                    field_clientCity.setText("");
                    field_clientStreet.setText("");
                    field_clientBuilding.setText("");
                    field_clientFlat.setText("");
                    field_clientPhone.setText("");
                    field_clientDebt.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Дані про цього клієнта вже існують, змінити дані?",
                            ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        db.updateClient(Integer.parseInt(field_clientID.getText()),
                                field_clientName.getText(),
                                field_clientCity.getText(),
                                field_clientStreet.getText(),
                                Integer.parseInt(field_clientBuilding.getText()),
                                Integer.parseInt(field_clientFlat.getText()),
                                field_clientPhone.getText(),
                                Integer.parseInt(field_clientDebt.getText()));
                        alert.close();
                    } else if (alert.getResult() == ButtonType.NO){
                        field_clientID.setText("");
                        field_clientName.setText("");
                        field_clientCity.setText("");
                        field_clientStreet.setText("");
                        field_clientBuilding.setText("");
                        field_clientFlat.setText("");
                        field_clientPhone.setText("");
                        field_clientDebt.setText("");
                        alert.close();
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_countAdd.setOnAction(actionEvent -> {
            field_idAccount.setStyle("-fx-border-color: #fafafa");
            field_idReadingAdd.setStyle("-fx-border-color: #fafafa");
            field_idClientAdd.setStyle("-fx-border-color: #fafafa");
            field_dateRegistr.setStyle("-fx-border-color: #fafafa");
            field_sumAdd.setStyle("-fx-border-color: #fafafa");
            field_stateAdd.setStyle("-fx-border-color: #fafafa");

            btn_countAdd.setText("Додати");

            String state = field_stateAdd.getText();

            //проверка ввода
            if (field_idAccount.getCharacters().length() == 0) {
                field_idAccount.setStyle("-fx-border-color: red");
                return;
            } else if (field_idReadingAdd.getCharacters().length() == 0) {
                field_idReadingAdd.setStyle("-fx-border-color: red");
                return;
            } else if (field_idClientAdd.getCharacters().length() == 0) {
                field_idClientAdd.setStyle("-fx-border-color: red");
                return;
            } else if (field_dateRegistr.getCharacters().length() != 10) {
                field_dateRegistr.setStyle("-fx-border-color: red");
                return;
            } else if (field_sumAdd.getCharacters().length() == 0) {
                field_sumAdd.setStyle("-fx-border-color: red");
                return;
            } else if (!(state.equals("оплачений") || state.equals("неоплачений"))) {
                field_stateAdd.setStyle("-fx-border-color: red");
                return;
            }

            try {
                boolean isLitAdded = db.addAccount(
                        Integer.parseInt(field_idAccount.getText()),
                        Integer.parseInt(field_idReadingAdd.getText()),
                        Integer.parseInt(field_idClientAdd.getText()),
                        field_dateRegistr.getText(),
                        Integer.parseInt(field_sumAdd.getText()),
                        state);
                if (isLitAdded) {
                    field_idAccount.setText("");
                    field_idReadingAdd.setText("");
                    field_idClientAdd.setText("");
                    field_dateRegistr.setText("");
                    field_sumAdd.setText("");
                    field_stateAdd.setText("");
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Дані про цей рахунок вже існують, змінити дані?",
                            ButtonType.YES, ButtonType.NO);
                    alert2.showAndWait();
                    if (alert2.getResult() == ButtonType.YES) {
                        db.updateAcount(
                                Integer.parseInt(field_idAccount.getText()),
                                Integer.parseInt(field_idReadingAdd.getText()),
                                Integer.parseInt(field_idClientAdd.getText()),
                                field_dateRegistr.getText(),
                                Integer.parseInt(field_sumAdd.getText()),
                                state);
                        alert2.close();
                        field_idAccount.setText("");
                        field_idReadingAdd.setText("");
                        field_idClientAdd.setText("");
                        field_dateRegistr.setText("");
                        field_sumAdd.setText("");
                        field_stateAdd.setText("");
                    } else if (alert2.getResult() == ButtonType.NO){
                       alert2.close();
                        field_idAccount.setText("");
                        field_idReadingAdd.setText("");
                        field_idClientAdd.setText("");
                        field_dateRegistr.setText("");
                        field_sumAdd.setText("");
                        field_stateAdd.setText("");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_addReading.setOnAction(actionEvent -> {
            field_idReading1.setStyle("-fx-border-color: #fafafa");
            field_idController.setStyle("-fx-border-color: #fafafa");
            field_counterID.setStyle("-fx-border-color: #fafafa");
            field_datefirst.setStyle("-fx-border-color: #fafafa");
            field_datesecond.setStyle("-fx-border-color: #fafafa");
            field_fisrReadingAdd.setStyle("-fx-border-color: #fafafa");
            field_lastReading.setStyle("-fx-border-color: #fafafa");

            btn_addReading.setText("Додати");

            //проверка ввода
            if (field_idReading1.getCharacters().length() == 0) {
                field_idReading1.setStyle("-fx-border-color: red");
                return;
            } else if (field_idController.getCharacters().length() == 0) {
                field_idController.setStyle("-fx-border-color: red");
                return;
            } else if (field_counterID.getCharacters().length() == 0) {
                field_counterID.setStyle("-fx-border-color: red");
                return;
            } else if (field_datefirst.getCharacters().length() != 10) {
                field_datefirst.setStyle("-fx-border-color: red");
                return;
            } else if (field_datesecond.getCharacters().length() != 10) {
                field_datesecond.setStyle("-fx-border-color: red");
                return;
            } else if (field_fisrReadingAdd.getCharacters().length() != 5) {
                field_fisrReadingAdd.setStyle("-fx-border-color: red");
                return;
            } else if (field_lastReading.getCharacters().length() != 5) {
                field_lastReading.setStyle("-fx-border-color: red");
                return;
            }

            try {
                boolean isLitAdded = db.addReading(
                        Integer.parseInt(field_idReading1.getText()),
                        Integer.parseInt(field_counterID.getText()),
                        Integer.parseInt(field_idController.getText()),
                        field_datefirst.getText(),
                        field_datesecond.getText(),
                        Integer.parseInt(field_fisrReadingAdd.getText()),
                        Integer.parseInt(field_lastReading.getText()),
                        //программа сама считает трату электроэтергии по показаниям
                        (Integer.parseInt(field_lastReading.getText()) - Integer.parseInt(field_fisrReadingAdd.getText())));
                if (isLitAdded) {
                    field_idReading1.setText("");
                    field_idController.setText("");
                    field_counterID.setText("");
                    field_datefirst.setText("");
                    field_datesecond.setText("");
                    field_fisrReadingAdd.setText("");
                    field_lastReading.setText("");
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION, "Дані про це  вже існують, змінити дані?",
                            ButtonType.YES, ButtonType.NO);
                    alert3.showAndWait();
                    if (alert3.getResult() == ButtonType.YES) {
                        db.updateReading(
                                Integer.parseInt(field_idReading1.getText()),
                                Integer.parseInt(field_counterID.getText()),
                                Integer.parseInt(field_idController.getText()),
                                field_datefirst.getText(),
                                field_datesecond.getText(),
                                Integer.parseInt(field_fisrReadingAdd.getText()),
                                Integer.parseInt(field_lastReading.getText()),
                                //программа сама считает трату электроэтергии по показаниям
                                (Integer.parseInt(field_lastReading.getText()) - Integer.parseInt(field_fisrReadingAdd.getText())));

                        field_idReading1.setText("");
                        field_idController.setText("");
                        field_counterID.setText("");
                        field_datefirst.setText("");
                        field_datesecond.setText("");
                        field_fisrReadingAdd.setText("");
                        field_lastReading.setText("");
                    }
                    else if (alert3.getResult() == ButtonType.NO) {
                        alert3.close();
                        field_idReading1.setText("");
                        field_idController.setText("");
                        field_counterID.setText("");
                        field_datefirst.setText("");
                        field_datesecond.setText("");
                        field_fisrReadingAdd.setText("");
                        field_lastReading.setText("");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        //поиск клиента по номеру
        FilteredList<ModelTableClients> filteredListClients = new FilteredList(obListClients, e -> true);
        field_clientByPhone.setOnKeyReleased(e ->{
            field_clientByPhone.textProperty().addListener((observable, oldvalue, newvalue) -> {
                filteredListClients.setPredicate((Predicate<? super ModelTableClients>) (ModelTableClients clients) -> {
                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    } else if (clients.getPhone().contains(newvalue)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ModelTableClients> sortedListClients = new SortedList<>(filteredListClients);
            sortedListClients.comparatorProperty().bind(table_clients.comparatorProperty());
            table_clients.setItems(sortedListClients);
        });

        //поиск счёта по коду показания
        FilteredList<ModelTableAccounts> filteredListAccounts = new FilteredList(obListAccounts, e -> true);
        field_idReadingfind.setOnKeyReleased(e ->{
            field_idReadingfind.textProperty().addListener((observable, oldvalue, newvalue) -> {
                filteredListAccounts.setPredicate((Predicate<? super ModelTableAccounts>) (ModelTableAccounts accounts) -> {
                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    } else if (accounts.getIdReading() == Integer.parseInt(newvalue)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ModelTableAccounts> sortedListAccounts = new SortedList<>(filteredListAccounts);
            sortedListAccounts.comparatorProperty().bind(table_accounts.comparatorProperty());
            table_accounts.setItems(sortedListAccounts);
        });

        //поиск показания счётчика по изначальному показанию
        FilteredList<ModelTableCounters> filteredListReadings = new FilteredList(obListCounter, e -> true);
        field_firstReading.setOnKeyReleased(e ->{
            field_firstReading.textProperty().addListener((observable, oldvalue, newvalue) -> {
                filteredListReadings.setPredicate((Predicate<? super ModelTableCounters>) (ModelTableCounters reading) -> {
                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    } else if (reading.getFirstReading() == Integer.parseInt(newvalue)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ModelTableCounters> sortedListReadings = new SortedList<>(filteredListReadings);
            sortedListReadings.comparatorProperty().bind(table_readings.comparatorProperty());
            table_readings.setItems(sortedListReadings);
        });

        //отфильтровать клиентов только из города Харьков
        FilteredList<ModelTableClients> filteredListClientsByCity = new FilteredList(obListClients, e -> true);
        btn_filtrCity.setOnAction(actionEvent -> {
            String city = "Харків";
            filteredListClientsByCity.setPredicate((Predicate<? super ModelTableClients>) (ModelTableClients clients) -> {
                if (clients.getCity().equals(city)){
                    return true;
                }
                return false;
            });

            SortedList<ModelTableClients> sortedListClientsByCity = new SortedList<>(filteredListClientsByCity);
            sortedListClientsByCity.comparatorProperty().bind(table_clients.comparatorProperty());
            table_clients.setItems(sortedListClientsByCity);
        });

        //отфильтровать клиентов которые имеют долг
        FilteredList<ModelTableClients> filteredListClientsByDebt = new FilteredList(obListClients, e -> true);
        btn_filtrDebt.setOnAction(actionEvent -> {
            filteredListClientsByDebt.setPredicate((Predicate<? super ModelTableClients>) (ModelTableClients clients) -> {
                if (clients.getDebt() > 0){
                    return true;
                }
                return false;
            });

            SortedList<ModelTableClients> sortedListClientsByDebt = new SortedList<>(filteredListClientsByDebt);
            sortedListClientsByDebt.comparatorProperty().bind(table_clients.comparatorProperty());
            table_clients.setItems(sortedListClientsByDebt);
        });

        //отфильтровать показания счётчиков только за Март
        FilteredList<ModelTableCounters> filteredListReadingsByMay = new FilteredList(obListCounter, e -> true);
        btn_filtrMay.setOnAction(actionEvent -> {
            filteredListReadingsByMay.setPredicate((Predicate<? super ModelTableCounters>) (ModelTableCounters reading) -> {
                String firstDate = reading.getFirstDate();
                String may = "05";
                //сравнение даты с 6-го символа
                    if (firstDate.regionMatches(5, may, 0, 2)) {
                        return true;
                    }
                    return false;
                });

            SortedList<ModelTableCounters> sortedListReadingsByMay = new SortedList<>(filteredListReadingsByMay);
            sortedListReadingsByMay.comparatorProperty().bind(table_readings.comparatorProperty());
            table_readings.setItems(sortedListReadingsByMay);
        });

        //отфильтровать показания счётчиков у которых трата больше 100 в месяц
        FilteredList<ModelTableCounters> filteredListReadingsByOutLay = new FilteredList(obListCounter, e -> true);
        btn_filtrOutlay.setOnAction(actionEvent -> {
            filteredListReadingsByOutLay.setPredicate((Predicate<? super ModelTableCounters>) (ModelTableCounters reading) -> {
                if (reading.getOutlay() > 100) {
                    return true;
                }
                return false;
            });

            SortedList<ModelTableCounters> sortedListReadingsByOutlay = new SortedList<>(filteredListReadingsByOutLay);
            sortedListReadingsByOutlay.comparatorProperty().bind(table_readings.comparatorProperty());
            table_readings.setItems(sortedListReadingsByOutlay);
        });

        //отфильтровать неоплаченные счета
        FilteredList<ModelTableAccounts> filteredListAccountsByState = new FilteredList(obListAccounts, e -> true);
        btn_filtrPay.setOnAction(actionEvent -> {
            filteredListAccountsByState.setPredicate((Predicate<? super ModelTableAccounts>) (ModelTableAccounts accounts) -> {
                if (accounts.getState().equals("неоплачений")) {
                    return true;
                }
                return false;
            });

            SortedList<ModelTableAccounts> sortedListAccountsByState = new SortedList<>(filteredListAccountsByState);
            sortedListAccountsByState.comparatorProperty().bind(table_accounts.comparatorProperty());
            table_accounts.setItems(sortedListAccountsByState);
        });

        //отфильтровать счета у который сумма больше 200
        FilteredList<ModelTableAccounts> filteredListAccountsBySum = new FilteredList(obListAccounts, e -> true);
        btn_filtrSum.setOnAction(actionEvent -> {
            filteredListAccountsBySum.setPredicate((Predicate<? super ModelTableAccounts>) (ModelTableAccounts accounts) -> {
                if (accounts.getSum() > 200) {
                    return true;
                }
                return false;
            });

            SortedList<ModelTableAccounts> sortedListAccountsBySum = new SortedList<>(filteredListAccountsBySum);
            sortedListAccountsBySum.comparatorProperty().bind(table_accounts.comparatorProperty());
            table_accounts.setItems(sortedListAccountsBySum);
        });

        //запрос на сумму всех платежей клиента по ID
        btn_query1.setOnAction(actionEvent -> {
            field_resultQuery.setText("");

            int clientID = Integer.parseInt(field_clientName_query1.getText());
            try {
                ResultSet res = db.firstQuery(clientID);
                List<Integer> totals = new ArrayList<>();
                while (res.next()){
                    totals.add(res.getInt("total"));
                }
                int sum = totals.stream().mapToInt(Integer::intValue).sum();
                field_resultQuery.setText(valueOf(sum));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        //запрос на трату электроэнергии
        btn_query2.setOnAction(actionEvent -> {
            field_resultQuery.setText("");

            try {
                ResultSet res = db.secondQuery();
                List<Integer> report = new ArrayList<>();
            while (res.next()){
                report.add(res.getInt("outlay"));
            }

            int max = Collections.max(report);
            field_resultQuery.setText("Максимальна витрата = " + valueOf(max));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        //запрос на показания счётчика в мае
        btn_query3.setOnAction(actionEvent -> {
            field_resultQuery.setText("");
            try {
                ResultSet res = db.thirdQuery();
                List<String> report = new ArrayList<>();
                while (res.next()) {
                    report.add("id Лічильника: ");
                    report.add(res.getString("idCounter")+" ");
                    report.add("Дата початкового показання: ");
                    report.add(res.getString("initialDate")+" ");
                    report.add("Дата кінцевого показання: ");
                    report.add(res.getString("endDate")+" ");
                    report.add("Початкове показання: ");
                    report.add(res.getString("initialReading")+" ");
                    report.add("Кінцеве показання: ");
                    report.add(res.getString("endReading")+" ");
                    report.add("\n");
                }

                String result = report.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
                field_resultQuery.setText(result);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        btn_query4.setOnAction(actionEvent -> {
            field_resultQuery.setText("");
            int clientID = Integer.parseInt(field_clientName_query4.getText());

            try {
                ResultSet res = db.fourthQuery(clientID);
                List<String> report = new ArrayList<>();
                while (res.next()) {
                    report.add("id Показання: ");
                    report.add(res.getString("idReading")+" ");
                    report.add("Дата оформлення: ");
                    report.add(res.getString("clearanceDate")+" ");
                    report.add("Сума: ");
                    report.add(res.getString("total")+" ");
                    report.add("Стан: ");
                    report.add(res.getString("state")+" ");
                    report.add("\n");
                }

                String result = report.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
                field_resultQuery.setText(result);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_report1.setOnAction(actionEvent -> {
            try {
                ResultSet res = db.reportOne();
                List<String> report = new ArrayList<>();

                //создание документа отчёта
                BaseFont timesBaseFont = BaseFont.createFont("A:\\Energy_Consumption\\TIMES.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font times = new Font(timesBaseFont);
                Document reportDoc = new Document();
                String docName = doc1_field.getText();
                PdfWriter pdfWriter = PdfWriter.getInstance(reportDoc, new FileOutputStream(docName + ".pdf"));
                reportDoc.open();
                Paragraph paragrph = new Paragraph("Звіт 1.  Список боржників", times);
                Paragraph paragrph2 = new Paragraph("     ");

                reportDoc.add(paragrph);
                reportDoc.add(paragrph2);
                var table = new PdfPTable(3);

                while (res.next()) {
                    String clientName = res.getString("clientName");

                    report.add("Ім'я: ");
                    report.add(clientName+" ");

                    Phrase clientNamePhrase = new Phrase(clientName, times);
                    PdfPCell clientNameCell = new PdfPCell(clientNamePhrase);
                    table.addCell(clientNameCell); //добавление данных в поле таблицы

                    String clientPhone = res.getString("phone");

                    report.add("Номер: ");
                    report.add(clientPhone+" ");

                    Phrase clientPhonePhrase = new Phrase(clientPhone, times);
                    PdfPCell clientPhoneCell = new PdfPCell(clientPhonePhrase);
                    table.addCell(clientPhoneCell); //добавление данных в поле таблицы

                    String debt = res.getString("debt")+" грн";

                    report.add("Розмір боргу: ");
                    report.add(debt);

                    Phrase debtPhrase = new Phrase(debt, times);
                    PdfPCell debtCell = new PdfPCell(debtPhrase);
                    table.addCell(debtCell); //добавление данных в поле таблицы

                    report.add("\n");
                }
                reportDoc.add(table);
                reportDoc.close();

                String result = report.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
                field_report1.setText(result);

                PrinterJob printerJob = PrinterJob.getPrinterJob();
                if (printerJob.printDialog()) {
                    File file = new File(docName + ".pdf");
                    PDDocument pdf = PDDocument.load(file);
                    Pageable pageable = new PDFPageable(pdf);
                    printerJob.setPageable(pageable);
                    printerJob.print();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });


        btn_report2.setOnAction(actionEvent -> {

            try {
                List<String> report = new ArrayList<>(); //данные о счёте с базы
                String clientName = fieldReport_clientName.getText();
                int[] clientInfo = db.reportTwo(clientName);

                //создание документа
                BaseFont timesBaseFont = BaseFont.createFont("A:\\Energy_Consumption\\TIMES.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font times = new Font(timesBaseFont);
                Document reportDoc = new Document();
                String doc2Name = doc2_field.getText();
                PdfWriter.getInstance(reportDoc, new FileOutputStream(doc2Name + ".pdf"));
                reportDoc.open();
                Paragraph paragrph = new Paragraph("Рахунок за електроенегрію", new Font(times));
                Paragraph paragrphName = new Paragraph("Клієнт: " + clientName, new Font(times));
                Paragraph paragrphID = new Paragraph("Номер клієнта:  " + clientInfo[3], new Font(times));

                Paragraph paragrphSpace = new Paragraph(" ", new Font(times));

                reportDoc.add(paragrph);
                reportDoc.add(paragrphName);
                reportDoc.add(paragrphID);
                reportDoc.add(paragrphSpace);

                var table = new PdfPTable(2);

                report.add("Розмір витрати: ");
                report.add(valueOf(clientInfo[0]));
                report.add("\n");
                Phrase outlayStringPhrase = new Phrase("Розмір витрати ", times);
                PdfPCell outlayStringCell = new PdfPCell(outlayStringPhrase);
                table.addCell(outlayStringCell);
                String outlay = String.valueOf(clientInfo[0]);
                Phrase outlayPhrase = new Phrase(outlay, times);
                PdfPCell outlayCell = new PdfPCell(outlayPhrase);
                table.addCell(outlayCell); //добавление данных в поле таблицы

                report.add("Ціна тарифу: ");
                report.add(clientInfo[1] + " коп.");
                report.add("\n");
                Phrase priceStringPhrase = new Phrase("Ціна тарифу ", times);
                PdfPCell priceStringCell = new PdfPCell(priceStringPhrase);
                table.addCell(priceStringCell);
                String price = String.valueOf(clientInfo[1]);
                Phrase pricePhrase = new Phrase(price + " коп.", times);
                PdfPCell priceCell = new PdfPCell(pricePhrase);
                table.addCell(priceCell); //добавление данных в поле таблицы

                report.add("Відсоток ПДВ: ");
                report.add(clientInfo[2] + " %");
                report.add("\n");
                Phrase percentStringPhrase = new Phrase("Відсоток ПДВ ", times);
                PdfPCell percentStringCell = new PdfPCell(percentStringPhrase);
                table.addCell(percentStringCell);
                String percent = String.valueOf(clientInfo[1]);
                Phrase percentPhrase = new Phrase(percent + " %", times);
                PdfPCell percentCell = new PdfPCell(percentPhrase);
                table.addCell(percentCell); //добавление данных в поле таблицы

                report.add("Загальна сума для виплати: ");
                double percentForTotal = clientInfo[2] / 100;
                double total = ((clientInfo[0]*clientInfo[1]) + ((clientInfo[0]*clientInfo[1])*percentForTotal))/100;
                report.add(total + " грн.");
                Phrase totalStringPhrase = new Phrase("Загальна сума ", times);
                PdfPCell totalStringCell = new PdfPCell(totalStringPhrase);
                table.addCell(totalStringCell);
                String totalSum = total + "грн.";
                Phrase totalPhrase = new Phrase(totalSum, times);
                PdfPCell totaltCell = new PdfPCell(totalPhrase);
                table.addCell(totaltCell);

                Paragraph paragrphSign = new Paragraph("Підпис платника    __________________", new Font(times));

                reportDoc.add(table);
                reportDoc.add(paragrphSpace);
                reportDoc.add(paragrphSign);
                reportDoc.close();

                String result = report.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(""));
                field_report2.setText(result);

                PrinterJob printerJob = PrinterJob.getPrinterJob();
                if (printerJob.printDialog()) {
                    File file = new File(doc2Name + ".pdf");
                    PDDocument pdf = PDDocument.load(file);
                    Pageable pageable = new PDFPageable(pdf);
                    printerJob.setPageable(pageable);
                    printerJob.print();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });

    }


}

