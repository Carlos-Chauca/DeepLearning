/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package leerjpg;

import java.io.IOException;

/**
 *
 * @author Administrador
 */

import java.util.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    static final Random rand = new Random();

    public static void main(String[] args) {
        Convolucion2 con = new Convolucion2();
        MatrizService matrizService = new MatrizService();
        ArrayList<MyData> data = con.lectura();
        matrizService.printData(data);

        matrizService.print(matrizService.getYArray(data));
        // rna01 red = new rna01(data.size(),);
        // System.out.println(con.lectura().toString());
        /*
         * double[][] mat1 = { { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }
         * }; Double[][] mat2 = { { 2.0, 1.0, 0.3 }, { 2.0, 1.0, 0.3 }, { 2.0, 1.0, 0.3
         * } }; Double[][] mat3 = { { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0,
         * 1.0 } }; System.out.println(con.compare(mat1, mat3));
         * System.out.println(con.compare(mat1, mat2));
         */
    }

    static double getRandom() {
        return (rand.nextDouble() * 2 - 1); // [-1;1[
    }

}
