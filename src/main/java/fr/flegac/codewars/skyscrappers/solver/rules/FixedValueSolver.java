package fr.flegac.codewars.skyscrappers.solver.rules;

import java.util.BitSet;

import fr.flegac.codewars.skyscrappers.problem.Solution;

/**
 * When : v1 is the only possible value in cell with coordinates (x,y)
 *
 * Then : v1 can not be in position (x,i) with i != y or (i,y) with i != x
 */
public class FixedValueSolver implements SolverRule {

    @Override
    public void apply(Solution solution) {
        for (int id = 0; id < solution.cellNumber(); id++) {
            uniqueOnCell(solution, id);
        }

        for (int i = 0; i < solution.size(); i++) {
            uniqueOnColSolver(solution, i);
            uniqueOnRowSolver(solution, i);
        }
    }

    private void uniqueOnRowSolver(Solution solution, int row) {
        int size = solution.size();
        int[] occurences = new int[size];
        int[] cols = new int[size];

        for (int col = 0; col < size; col++) {
            BitSet availability = solution.getCellAvailabilities(solution.index(col, row));
            for (int value = 0; value < size; value++) {
                if (availability.get(value)) {
                    occurences[value]++;
                    cols[value] = col;
                }
            }
        }

        for (int value = 0; value < size; value++) {
            if (occurences[value] == 1) {
                solution.keepOnly(solution.index(cols[value], row), value);
            }
        }
    }

    private void uniqueOnColSolver(Solution solution, int col) {
        int size = solution.size();
        int[] occurences = new int[size];
        int[] rows = new int[size];

        for (int row = 0; row < size; row++) {
            BitSet availability = solution.getCellAvailabilities(solution.index(col, row));
            for (int value = 0; value < size; value++) {
                if (availability.get(value)) {
                    occurences[value]++;
                    rows[value] = row;
                }
            }
        }

        for (int value = 0; value < size; value++) {
            if (occurences[value] == 1) {
                solution.keepOnly(solution.index(col, rows[value]), value);
            }
        }
    }

    private void uniqueOnCell(Solution solution, int cellId) {
        if (!solution.isFixed(cellId)) {
            return;
        }
        int size = solution.size();

        int row = cellId / size;
        int col = cellId % size;

        int value = solution.getValue(cellId);
        for (int i = 0; i < size; i++) {
            if (i != row) {
                solution.remove(solution.index(col, i), value);
            }
            if (i != col) {
                solution.remove(solution.index(i, row), value);
            }
        }
    }

}
