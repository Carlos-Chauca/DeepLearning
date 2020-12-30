/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package leerjpg;

public class prueba01 {
    public static void main(String[] args) {
        double ingreso[][] = { { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 1 } };
        double salida[][] = { { 1 }, { 0 }, { 1 }, { 0 } };
        double evaluar[][] = { { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 },
                { 1, 1, 0 } };
        rna01 rn = new rna01(3, 2, 1);
        rn.entrenamiento(ingreso, salida, 1000);
        rn.prueba(evaluar);
    }
}
