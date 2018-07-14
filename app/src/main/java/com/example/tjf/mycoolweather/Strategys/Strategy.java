package com.example.tjf.mycoolweather.Strategys;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public interface Strategy {
    /**
     * @author 小飞
     * @create 2018-06-05 9:47
     * @Describe  策略方法
     */
    public void strategyInterface();

        /**
         * @author 小飞
         * @create 2018-06-05 9:48
         * @Describe  具体实现A
         */
    public class ConcreteStrategyA implements Strategy {

        @Override
        public void strategyInterface() {

        }
    }
    /**
     * @author 小飞
     * @create 2018-06-05 9:48
     * @Describe 具体实现B
     */
    public class ConcreteStrategyB implements Strategy {

        @Override
        public void strategyInterface() {

        }
    }
}
