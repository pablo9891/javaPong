package org.game.input.callback;

@FunctionalInterface
public interface MouseCallback<T, E, R> {
    void apply(T bar, E listener, R delta);
}