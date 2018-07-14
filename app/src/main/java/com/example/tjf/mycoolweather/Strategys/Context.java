package com.example.tjf.mycoolweather.Strategys;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void contextInterface() {
        strategy.strategyInterface();
    }
}
