package interpreter_demos;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

public class Matrice {




    public static void main(String[] args) {
        int x = 3;
        pretvori(x);
        //System.out.println(x);

        DoubleMatrix2D matrix = DoubleFactory2D.sparse.make(3, 4);
        matrix.set(1, 1, 3.5);

        DoubleMatrix2D matrix2 = DoubleFactory2D.sparse.make(3, 3);
        matrix2.set(1, 1, 5);
        matrix2.set(1, 2, 5);

       // DoubleMatrix2D result = DoubleFactory2D.sparse.make(3, 3);

        DoubleDoubleFunction plus = new DoubleDoubleFunction() {
            public double apply(double a, double b) { return a+b; }
        };

        DoubleDoubleFunction minus = new DoubleDoubleFunction() {
            public double apply(double a, double b) { return a-b; }
        };

        //ZBRAJANJE MATRICA
         //matrix.assign(matrix2, plus);
         System.out.println(matrix);

        //MNOZENJE MATRICA
        //DoubleMatrix2D result = Algebra.DEFAULT.mult(matrix, matrix2);
       // System.out.println(result);
    }

    private static int pretvori(int x) {
        return x;
    }
}
