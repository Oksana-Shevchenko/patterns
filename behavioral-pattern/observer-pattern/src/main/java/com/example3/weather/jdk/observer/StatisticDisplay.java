package com.example3.weather.jdk.observer;

import java.util.Observable;
import java.util.Observer;

public class StatisticDisplay implements Observer, DisplayElement {
	private float temperature;
	private float humidity;
	private float pressure;

	@Override
	public void update(Observable obs, Object arg) {
		if (obs instanceof WeatherData) {
			WeatherData weatherData = (WeatherData)obs;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			this.pressure = weatherData.getPressure();
			display();
		}
	}

	@Override
	public void display() {
		System.out.println("Show statistics display");
		System.out.println("Statistics condition: "
				+ temperature + "F degrees and "
				+ humidity + "% humidity and "
				+ pressure + "bar pressure");
	}
}
