package org.game.input.callback;

@FunctionalInterface
public interface MouseCallback<Bar, MouseListenerCallback, Double> {
    void apply(Bar bar, MouseListenerCallback listener, Double delta);
}