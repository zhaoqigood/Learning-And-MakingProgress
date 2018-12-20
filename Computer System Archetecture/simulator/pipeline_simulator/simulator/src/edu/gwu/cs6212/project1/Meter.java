package edu.gwu.cs6212.project1;


import static java.lang.System.currentTimeMillis;

public class Meter {
    ExperimentBase experiment;

    public Meter(ExperimentBase experiment) {
        this.experiment = experiment;
    }

    public long run(double n) {
        long start;
        long end;
        long cycles;

        //start = System.nanoTime();
        cycles = experiment.run(n);
        //end = System.nanoTime();

        return cycles;
    }
}
