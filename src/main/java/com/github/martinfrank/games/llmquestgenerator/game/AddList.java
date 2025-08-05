package com.github.martinfrank.games.llmquestgenerator.game;

import java.util.ArrayList;
import java.util.List;

//FIXME richtiger ablage Ort?
class AddList<T> {

    private final List<T> items = new ArrayList<>();

    public T add(T addendum) {
        this.items.add(addendum);
        return addendum;
    }

    public List<T> toList() {
        return items;
    }
}
