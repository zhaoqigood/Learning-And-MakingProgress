package edu.gwu.cs6212.project1;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class Experiment8 extends ExperimentBase {

    @Override
    public long run(double n) {
        long cycle1 = 0;
        long cycle2 = 0;
        double j = 5;
        double sum = 0;
        while (j < log(n)) {
            double k = 5;
            while (k < n) {
                //Sum += a[j]*b[k]
                //sum += Math.random() * Math.random();
                k = pow(k, 1.5);
                cycle2++;
            }
            cycle1++;
            j = 1.2 * j;
        }
        System.out.println(cycle1);
        return cycle2;
    }
}
