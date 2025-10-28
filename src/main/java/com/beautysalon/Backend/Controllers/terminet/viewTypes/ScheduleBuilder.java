package com.beautysalon.Backend.Controllers.terminet.viewTypes;


import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import java.time.*;
import java.time.format.*;
import java.time.temporal.WeekFields;
import java.util.*;

public class ScheduleBuilder {

    private final GridPane centerGrid;
    private final VBox leftTimeColumn;
    private final List<String> activeCells;

    public ScheduleBuilder(GridPane centerGrid, VBox leftTimeColumn, List<String> activeCells) {
        this.centerGrid = centerGrid;
        this.leftTimeColumn = leftTimeColumn;
        this.activeCells = activeCells;
    }

    public void buildSchedule(int week, int month, int year) {
        centerGrid.getChildren().clear();
        leftTimeColumn.getChildren().clear();

        LocalTime start = null;
        LocalTime end = null;

        // 1️⃣ Determine start and end time
        for (String data : activeCells) {
            LocalDateTime dt = LocalDateTime.parse(data.split("_")[3]);
            if (matchesCurrentWeekMonthYear(dt, week, month, year)) {
                LocalTime time = LocalTime.parse(data.split("_")[2].substring(0, 5),
                        DateTimeFormatter.ofPattern("HH:mm"));
                if (start == null || time.isBefore(start)) start = time;
                if (end == null || time.isAfter(end)) end = time;
            }
        }

        int hourCount = (start != null && end != null)
                ? Math.max(5, end.getHour() - start.getHour() + 1)
                : 5;

        // 2️⃣ Create time column
        for (int i = 0; i < hourCount; i++) {
            LocalTime labelTime = start.plusHours(i);
            Label timeLabel = new Label(labelTime.format(DateTimeFormatter.ofPattern("HH:00")));
            timeLabel.getStyleClass().add("time-label");
            leftTimeColumn.getChildren().add(timeLabel);
        }

        // 3️⃣ Create day grid
        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        Color[] colors = {
                Color.web("#1b9f00"), Color.web("#9f0095"),
                Color.web("#9f9500"), Color.web("#00759f"), Color.web("#961d1d")
        };

        int colorCounter = 0;

        for (int h = 0; h < hourCount; h++) {
            LocalTime slotTime = start.plusHours(h);

            for (int d = 0; d < daysOfWeek.length; d++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("calendar-cell");
                cell.setAlignment(Pos.CENTER);

                String key = daysOfWeek[d] + "_" + week + "_" + month + "_" + year + "_" + slotTime.getHour();

                for (String value : activeCells) {
                    String[] parts = value.split("_");
                    LocalDateTime dt = LocalDateTime.parse(parts[3]);

                    boolean match = key.equalsIgnoreCase(
                            parts[0] + "_" +
                            dt.get(WeekFields.of(Locale.getDefault()).weekOfMonth()) + "_" +
                            dt.getMonthValue() + "_" +
                            dt.getYear() + "_" +
                            parts[2].substring(0, 2)
                    );

                    if (match) {
                        colorCounter = (colorCounter + 1) % colors.length;

                        Label apptLabel = new Label(parts[5]); // Example text
                        apptLabel.setTextFill(Color.WHITE);
                        apptLabel.setStyle("-fx-background-color: " + toHex(colors[colorCounter])
                                + "; -fx-padding: 3 6; -fx-background-radius: 4;");
                        cell.getChildren().add(apptLabel);
                    }
                }

                centerGrid.add(cell, d, h);
            }
        }
    }

    private boolean matchesCurrentWeekMonthYear(LocalDateTime dt, int week, int month, int year) {
        return dt.get(WeekFields.of(Locale.getDefault()).weekOfMonth()) == week
                && dt.getMonthValue() == month
                && dt.getYear() == year;
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}