package org.game.input;

@FunctionalInterface
public interface KeyboardCallback<Bar, KeyListenerCallback, Double> {
    void apply(Bar bar, KeyListenerCallback listener, Double delta);
}
