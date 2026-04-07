package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private final List<GameObserver> observers = new ArrayList<>();

    public void register(GameObserver observer) {
        observers.add(observer);
    }

    public void publish(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
