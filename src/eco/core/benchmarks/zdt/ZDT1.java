/*
 * Copyright (C) 2010-2016 José Luis Risco Martín <jlrisco@ucm.es>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *  - José Luis Risco Martín
 */
package eco.core.benchmarks.zdt;

import java.util.ArrayList;

import eco.core.operator.comparator.SolutionDominance;
import eco.core.problem.Solution;
import eco.core.problem.Solutions;
import eco.core.problem.Variable;

public class ZDT1 extends ZDT {

    public ZDT1(Integer numberOfVariables) {
        super(numberOfVariables);
        for (int i = 0; i < numberOfVariables; i++) {
            lowerBound[i] = 0.0;
            upperBound[i] = 1.0;
        }
    } // ZDT1

    public ZDT1() {
        this(30);
    }

    public void evaluate(Solution<Variable<Double>> solution) {
        for (int i = 0; i < numberOfObjectives; i++) {
            solution.getObjectives().set(i, 0.0);
        }
        double f1 = 0, g = 0, h = 0;
        ArrayList<Variable<Double>> variables = solution.getVariables();
        f1 = variables.get(0).getValue();
        g = 0;
        for (int j = 1; j < numberOfVariables; ++j) {
            g += Math.pow(variables.get(j).getValue(), 2);
        }
        g /= numberOfVariables - 1;
        g *= 9;
        g += 1;
        h = 1 - Math.sqrt(f1 / g);
        solution.getObjectives().set(0, f1);
        solution.getObjectives().set(1, g * h);
    }

    public Solutions<Variable<Double>> computeParetoOptimalFront(int n) {
        Solutions<Variable<Double>> result = new Solutions<Variable<Double>>();

        double temp;
        for (int i = 0; i < n; ++i) {
            Solution<Variable<Double>> sol = new Solution<Variable<Double>>(numberOfObjectives);
            temp = 0.0 + (1.0 * i) / (n - 1);
            sol.getVariables().add(new Variable<Double>(temp));
            for (int j = 1; j < numberOfVariables; ++j) {
                sol.getVariables().add(new Variable<Double>(0.0));
            }

            evaluate(sol);
            result.add(sol);
        }

        result.reduceToNonDominated(new SolutionDominance<Variable<Double>>());
        return result;
    }

    public ZDT1 clone() {
    	ZDT1 clone = new ZDT1(this.numberOfVariables);
    	for(int i=0; i<numberOfVariables; ++i) {
    		clone.lowerBound[i] = lowerBound[i];
    		clone.upperBound[i] = upperBound[i];
    	}
    	return clone;
    }
}
