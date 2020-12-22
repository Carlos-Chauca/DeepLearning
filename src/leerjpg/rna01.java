/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package leerjpg;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Administrador
 */
public class rna01 {
  static final Random rand = new Random();
  int ci;
  int co;
  int cs;

  double xin[][];// ={{0,1,0},{0,1,1},{1,0,0},{1,0,1}};
  // double xin[][]={{0,1,0},{0,1,1},{1,0,0},{1,0,1}};
  double xout[][];// ={{1},{0},{1},{0}};
  // double xout[][]={{1},{0},{1},{0}};

  ////////////////// double
  ////////////////// prueba[][]={{0,1,0},{0,1,1},{1,0,0},{1,0,1},{1,1,1},{0,0,0},{1,1,0}};

  double y[];

  double s[];
  double g[];
  double w[];

  int c[] = new int[3];// capas de datos
  // capa de ingrso , capa e salida , capa oculta

  public rna01(int ci_, int co_, int cs_) {
    int ci = ci_;
    int co = co_;
    int cs = cs_;

    y = new double[co + cs];
    s = new double[co + cs];
    g = new double[co + cs];
    w = new double[ci * co + co * cs];

    c[0] = ci;
    c[1] = co;
    c[2] = cs;

    for (int i = 0; i < y.length; i++) {
      y[i] = 0;
      s[i] = 0;
      g[i] = 0;
    }
    for (int i = 0; i < w.length; i++) {
      w[i] = getRandom();
    }

  }

  public double fun(double d) {
    return 1 / (1 + Math.exp(-d));
  }

  public void printxingreso() {
    // visualizar x ingreso
    for (int i = 0; i < xin.length; i++)
      for (int j = 0; j < xin[i].length; j++)
        System.out.println("xingreso[" + i + "," + j + "]=" + xin[i][j]);
    System.out.println("                ");
  }

  public void printxysalida() {
    // visalizar x de salida
    for (int i = 0; i < xout.length; i++)
      for (int j = 0; j < xout[i].length; j++)
        System.out.println("xsalida[" + i + "," + j + "]=" + xout[i][j]);
  }

  public void printy() {
    for (int i = 0; i < y.length; i++)
      System.out.println("y[" + i + "]=" + y[i]);
  }

  public void printw() {
    for (int i = 0; i < w.length; i++)
      System.out.println("w[" + i + "]=" + w[i]);
  }

  public void prints() {
    for (int i = 0; i < s.length; i++)
      System.out.println("s[" + i + "]=" + s[i]);
  }

  public void printg() {
    for (int i = 0; i < g.length; i++)
      System.out.println("g[" + i + "]=" + g[i]);
  }

  double getRandom() {
    return (rand.nextDouble() * 2 - 1); // [-1;1[
  }

  public void entrenamiento(double[][] in, double[][] sal, int veces) {
    xin = in;
    xout = sal;
    for (int v = 0; v < veces; v++)
      for (int i = 0; i < xin.length; i++) {
        entreno(i);
      }

  }

  public void entreno(int cii) {
    int ii;
    double pls;
    int ci;

    // entrenamiento
    //////////////////////////////////
    ////// ******** Ida**********//////
    // +++++++capa1
    /// ci=0;//entrenamiento primero /////HOPE
    ci = cii;
    ii = 0;// capa0*capa1
    pls = 0;
    for (int i = 0; i < c[1]; i++) {
      for (int j = 0; j < c[0]; j++) {
        pls = pls + w[ii] * xin[ci][j];
        ii++;
      }
      s[i] = pls; // i = i+ capa0
      y[i] = fun(s[i]); // i = i+ capa0
      pls = 0;
    }
    // ++++++capa2
    pls = 0;
    ii = c[0] * c[1];// capa1*capa2
    for (int i = 0; i < c[2]; i++) {
      for (int j = 0; j < c[1]; j++) {
        pls = pls + w[ii] * y[j];
        ii++;
      }
      s[i + c[1]] = pls; // i = i + capa1
      y[i + c[1]] = fun(s[i + c[1]]); // i = i + capa1
      pls = 0;
    }

    // printy();
    // prints();
    // printw();
    // printxingreso();
    // printxysalida();
    // System.out.println("----------------------********------------------------");

    ////// ----------Fin Ida--------/////
    ////// ******** Vuelta**********/////
    // ++++capa2 g
    for (int i = 0; i < c[2]; i++) {
      g[i + c[1]] = (xout[ci][i] - y[i + c[1]]) * y[i + c[1]] * (1 - y[i + c[1]]);
    }

    // ++++capa1 g
    pls = 0;
    for (int i = 0; i < c[1]; i++) {
      for (int j = 0; j < c[2]; j++) {
        pls = pls + w[c[0] * c[1] + j * c[1] + i] * g[c[1] + j];
      }
      g[i] = y[i] * (1 - y[i]) * pls;
      pls = 0;
    }
    ArrayList<Thread> hilos = new ArrayList<Thread>();
    // ++++capa2 w
    ii = c[0] * c[1];// capa1*capa2
    for (int i = 0; i < c[2]; i++) {
      int tempII = ii;
      // TODO Auto-generated method stub
      for (int j = 0; j < c[1]; j++) {
        w[tempII] = w[tempII] + g[i + c[1]] * y[j];
      }
      ii++;
    }

    // ++++capa1 w
    ii = 0;// capa0*capa1
    for (int i = 0; i < c[1]; i++) {
      for (int j = 0; j < c[0]; j++) {
        w[ii] = w[ii] + g[i] * xin[ci][j];
        ii++;
      }
    }
    ////// ----------Fin Vuelta--------/////
    // printg();
    // printy();
    // prints();
    // printw();
    // printxingreso();
    // printxysalida();
    // System.out.println("----------------------****Fin****------------------------");
  }

  public void prueba(double[][] pruebas) {
    double prubs[] = new double[c[0]];

    for (int i = 0; i < pruebas.length; i++) {
      for (int j = 0; j < pruebas[i].length; j++) {
        prubs[j] = pruebas[i][j];
        // System.out.println("["+i+","+j+"]"+pruebas[i][j]);
      }
      usored(prubs);
    }

  }

  public void usored(double[] datatest) {
    System.out.println("-----------****Inicio Test****----------");
    int ii;
    double pls;
    // int ci;

    // entrenamiento
    //////////////////////////////////
    ////// ******** Ida**********//////
    // +++++++capa1
    /// ci=0;//entrenamiento primero /////HOPE
    // ci=cii;
    ii = 0;// capa0*capa1
    pls = 0;
    for (int i = 0; i < c[1]; i++) {
      for (int j = 0; j < c[0]; j++) {
        // pls=pls+w[ii]*xin[ci][j];
        pls = pls + w[ii] * datatest[j];
        ii++;
      }
      s[i] = pls; // i = i+ capa0
      y[i] = fun(s[i]); // i = i+ capa0
      pls = 0;
    }
    // ++++++capa2
    pls = 0;
    ii = c[0] * c[1];// capa1*capa2
    for (int i = 0; i < c[2]; i++) {
      for (int j = 0; j < c[1]; j++) {
        pls = pls + w[ii] * y[j];
        ii++;
      }
      s[i + c[1]] = pls; // i = i + capa1
      y[i + c[1]] = fun(s[i + c[1]]); // i = i + capa1
      pls = 0;
    }
    // printy();
    // printy();
    System.out.print("prueba");
    for (int i = 0; i < datatest.length; i++) {
      System.out.print("[" + datatest[i] + "] ");
    }
    System.out.println();
    System.out.print("salida");
    // for(int i=(co-1);i<(co+cs);i++){
    // for(int i=2;i<3;i++){
    for (int i = c[1]; i < (c[1] + c[2]); i++) {
      System.out.print("[" + y[i] + "] ");
    }
    System.out.println();

    // System.out.println("-----------****Fin Test****----------");

  }

}
