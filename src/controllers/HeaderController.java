package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.*;
import socket.Requester;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;

public class HeaderController {

    ListView<Notification> notificationList;
    ObservableList<Notification> notifications;
    private static HeaderController controller;
    //sad

    @FXML
    private AnchorPane calendarHeaderView;
    @FXML
    private Label month_Year;
    @FXML
    private Label weekNr;
    @FXML
    private Label mondayDayOfWeek;
    @FXML
    private Label tuesdayDayOfWeek;
    @FXML
    private Label wednesdayDayOfWeek;
    @FXML
    private Label thursdayDayOfWeek;
    @FXML
    private Label fridayDayOfWeek;
    @FXML
    private Label saturdayDayOfWeek;
    @FXML
    private Label sundayDayOfWeek;
    @FXML
    private Button notificationButton;

    LocalDate date = LocalDate.now();
    ArrayList<Label> weekday_labels = new ArrayList<>();
    Notification selected_notification;
    Timer timer;
    Scheduler scheduler;

    @FXML
    private void initialize() {
        controller = this;
        /* Add all labels to an ArrayList<Label> */
        Label[] temp_weekday_labels = {mondayDayOfWeek, tuesdayDayOfWeek, wednesdayDayOfWeek, thursdayDayOfWeek,
                fridayDayOfWeek, saturdayDayOfWeek, sundayDayOfWeek};
        weekday_labels.addAll(Arrays.asList(temp_weekday_labels));
        notificationButton.setText(Integer.toString(PersonInfo.getPersonInfo().getNotifications().size()));
        System.out.println("HeaderController inited");
        timer = new Timer();
        scheduler = new Scheduler();
        timer.scheduleAtFixedRate(scheduler, 1, 10000);
    }

    @FXML
    private void addEventOnAction() {
        WindowController.goToEventView(null);
    }

    @FXML
    private void incrementWeek() {

		/* Add 1 week to LocalDate date */
        date = date.plusWeeks(1);
        updateDates();
    }

    @FXML
    private void decrementWeek() {

		/* Subtract 1 week of LocalDate date */
        date = date.minusWeeks(1);
        updateDates();

    }

    @FXML
    private void updateDates() {

		/* Set current week number */
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        weekNr.setText("" + weekNumber);
        
        /* Update WeekController drawings */
        WeekController.getController().drawEvents(PersonInfo.getPersonInfo().getEventsForWeek(weekNumber));

		/* Set current month/year */
        month_Year.setText(date.getMonth() + " " + date.getYear());
		
		/* Set date of weekday_labels */
        for (int i = 0; i < weekday_labels.size(); i++) {
            int date_value = date.with(weekFields.getFirstDayOfWeek()).plusDays(i).getDayOfMonth();
            weekday_labels.get(i).setText("" + date_value + ".");
        }
    }

    @FXML
    private void logOff() {
		/* Sends the user to the log-in screen */
        WindowController.goToLogin();
        timer.cancel();
    }

    @FXML
    private void openNotifications() {
        Scene scene = WindowController.thisStage.getScene();
        Pane notificationWindow = (Pane) scene.lookup("#notificationWindow");
        notificationList = (ListView) scene.lookup("#notificationList");
        notificationButton.setStyle("-fx-background-color: #187E96");

        if (notificationWindow.isVisible()) {
            notificationWindow.setVisible(false);
        } else {
            Requester requester = new Requester();

            /* Replace new Person med PersonInfo.getPerson() */
            ArrayList<Notification> notes = requester.getNotifications(PersonInfo.getPersonInfo().getPersonalUserGroup());
            notifications = FXCollections.observableArrayList(notes);
            notificationList.setItems(notifications);

            notificationList.setCellFactory((list) -> {
                return new NotificationCell(notifications);
            });
            notificationWindow.setVisible(true);
        }
    }

    public static HeaderController getController() {
        return controller;
    }

    public void weekInit() {
        updateDates();
    }

    public void updateNotifications() {
        Requester req = new Requester();
        int current_num_notifications = PersonInfo.getPersonInfo().getNotifications().size();
        PersonInfo.personInfo.setNotifications(req.getNotifications(PersonInfo.getPersonInfo().getPersonalUserGroup()));
        int new_num_notifications = PersonInfo.getPersonInfo().getNotifications().size();

        if (current_num_notifications < new_num_notifications) {
            System.out.println("New notification");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateNotificationButton(PersonInfo.getPersonInfo().getNotifications().size());
                    notificationButton.setStyle("-fx-background-color: red");
                }
            });
        } else {
            System.out.println("No new notifications");
        }
    }
    public void updateNotificationButton(int value){
        notificationButton.setText(Integer.toString(value));
    }
    

}