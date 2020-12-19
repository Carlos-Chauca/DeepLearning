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
        Convolucion con = new Convolucion();

        try {
            con.lectura();
        } catch (IOException e) {
            // TODO: handle exception

        }
    }

    static double getRandom() {
        return (rand.nextDouble() * 2 - 1); // [-1;1[
    }

}
