package Ex05;

import java.util.*;

public class TemperatureSensor implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    public void setTemperature(int temperature) {

        this.temperature = temperature;

        System.out.println("Cam bien: Nhiet do = " + temperature);

        notifyObservers();
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {

        for (Observer o : observers) {
            o.update(temperature);
        }

    }
}
