package citi.stock.monitor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private int second = 0;
    private Random random = new Random();

    @Override
    public void start(Stage stage) {
	stage.setTitle("Citi Stock Monitor - AAPL");

	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();
	xAxis.setLabel("Time (seconds)");
	yAxis.setLabel("Stock Price ($)");
	yAxis.setAutoRanging(false);
	yAxis.setLowerBound(100);
	yAxis.setUpperBound(200);

	final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
	lineChart.setTitle("Real-time Stock Price (Mock Data)");
	series.setName("AAPL Price");

	Scene scene = new Scene(lineChart, 800, 600);
	lineChart.getData().add(series);

	stage.setScene(scene);
	stage.show();

	setupTimer();
    }

    private void setupTimer() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	executor.scheduleAtFixedRate(() -> {
	    double newPrice = 155 + (15 * random.nextDouble());

	    Platform.runLater(() -> {
	        series.getData().add(new XYChart.Data<>(second++, newPrice));
		if (series.getData().size() > 20) {
		    series.getData().remove(0);
		}
	    });
	}, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
	launch(args);
    }
}
