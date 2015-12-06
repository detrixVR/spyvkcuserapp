package serverdaemon.controller;

import java.util.Set;

public interface Refreshable<T> {
    void refresh(Set<T> set);
}
