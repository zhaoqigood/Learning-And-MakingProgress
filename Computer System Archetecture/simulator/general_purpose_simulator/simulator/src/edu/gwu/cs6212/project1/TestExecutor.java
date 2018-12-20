package edu.gwu.cs6212.project1;

import java.util.ArrayList;
import java.util.List;

public class TestExecutor {

    Meter meter;

    public TestExecutor(ExperimentBase experiment) {
        meter = new Meter(experiment);
    }

    public static void main(String[] args) {
        TestExecutor executor = new TestExecutor(new Experiment8());
        List<Long> results = new ArrayList<>();
        for (double i = 10; i < Math.pow(10, 60); i = i * 100) {
            results.add(executor.run_test1(i));
        }
        System.out.println(results);
    }

    long run_test1(double n){
        return meter.run(n);
    }
}
