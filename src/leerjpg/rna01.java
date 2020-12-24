/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package leerjpg;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Administrador
 */
public class rna01 {
  static final Random rand = new Random();
  // int ci; capa de ingreso
  // int co; capa oculta
  // int cs; capa intermedia

  double xin[][];
  double xout[][];

  double y[];

  double s[];
  double g[];
  double w[];

  int c[] = new int[3];// capas de datos
  // capa de ingrso , capa e salida , capa oculta
  int capaIngreso;
  int capaOculta;
  int capaSalida;

  public rna01(int ci_, int co_, int cs_) {
    int ci = ci_;
    int co = co_;
    int cs = cs_;

    y = new double[co + cs];
    s = new double[co + cs];
    g = new double[co + cs];
    w = new double[ci * co + co * cs];

    capaIngreso = ci;
    capaOculta = co;
    capaSalida = cs;

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
    double[] error = new double[veces];
    for (int v = 0; v < veces; v++) {
      for (int i = 0; i < xin.length; i++) {
        entreno(i);
      }
      // System.out.println("error " + getError(xin, xout));
      error[v] = getError(xin, xout);
    }
    System.out.println("errorMax " + error[0]);

    System.out.println("error" + error[error.length - 1]);

    new G(error).graficar();

  }

  public double[] evaluar(int xi) {
    int ii = 0;// capa0*capa1
    double pls = 0;
    double[] a1 = new double[capaOculta];
    double[] z1 = new double[capaOculta];
    for (int i = 0; i < capaOculta; i++) {
      pls = 0;
      for (int j = 0; j < capaIngreso; j++) {
        pls = pls + w[ii] * xin[xi][j];
        ii++;
      }
      z1[i] = pls; // i = i+ capa0
      a1[i] = fun(z1[i]); // i = i+ capa0

    }
    // ++++++capa2
    pls = 0;
    ii = capaIngreso * capaOculta;// capa1*capa2
    double[] a2 = new double[capaSalida];
    double[] z2 = new double[capaOculta];

    for (int i = 0; i < capaSalida; i++) {
      for (int j = 0; j < capaOculta; j++) {
        pls = pls + w[ii] * a1[j];
        ii++;
      }
      z2[i] = pls; // i = i + capa1
      a2[i] = fun(z2[i]); // i = i + capa1
      pls = 0;
    }
    return a2;
  }

  public double getError(double[][] xin, double[][] xout) {
    double[][] out = new double[xin.length][];

    double sum = 0;

    for (int i = 0; i < out.length; i++) {
      out[i] = evaluar(i);
      for (int j = 0; j < out[i].length; j++) {
        sum += Math.pow(out[i][j] - xout[i][j], 2) / (2);
      }
    }

    return sum / xin.length;
  }

  public double[][][] randomW(int[] Capas) {
    double w[][][] = new double[Capas.length - 1][][];

    for (int i = 0; i < Capas.length - 1; i++) {
      w[i] = new double[Capas[i]][];
      for (int j = 0; j < w[i].length; j++) {
        w[i][j] = new double[Capas[i + 1]];
        for (int j2 = 0; j2 < w[i][j].length; j2++) {
          w[i][j][j2] = getRandom();
        }
      }

    }

    // w[0]=new double[x[0].length][capaOculta];
    // w[1]=new double[capaOculta][y[0].length];
    return w;
  }

  public double[][][] entreno2(int xi) {
    double[][] x = xin.clone();
    double[][] y = xout.clone();
    int[] inputCapas = { x[0].length, capaOculta, capaSalida };
    double w[][][] = randomW(inputCapas); // new double[2][][];
    // x_i ,capa , nodo de origen , nodo de destino

    double[][][][] z = new double[inputCapas[0]][][];
    double[][][][] a = new double[inputCapas[0]][][];

    for (int i = 0; i < z.length; i++) {
      z[i] = new double[inputCapas.length];
      a[i] = new double[inputCapas.length];

    }

    for (int k = 0; k < w[0].length; k++) {
      // para todos los x_i
      for (int i = 0; i < x.length; i++) {
        // z[i][0] = 0;
        // multiplicacion vetorial w capa 0 a 1 * X i
        // z[i][0][0][] = 0 ;
        for (int j = 0; j < x[0].length; j++) {
          sum += x[i][j] * w[0][k][j];
          // z[0][i] += x[i][j] * w[0][1][j];
        }
        // a[0][i] = fun(z[0][i]);
      }

    }

    return null;
  }

  public void entreno(int cii) {
    int ii;
    double pls;
    int ci;

    // entrenamiento
    ////// ******** Ida**********//////
    // +++++++capa1
    /// ci=0;//entrenamiento primero /////HOPE
    ci = cii;
    ii = 0;// capa0*capa1
    pls = 0;
    for (int i = 0; i < capaOculta; i++) {
      for (int j = 0; j < capaIngreso; j++) {
        pls = pls + w[ii] * xin[ci][j];
        ii++;
      }
      s[i] = pls; // i = i+ capa0
      y[i] = fun(s[i]); // i = i+ capa0
      pls = 0;
    }
    // ++++++capa2
    pls = 0;
    ii = capaIngreso * capaOculta;// capa1*capa2
    for (int i = 0; i < capaSalida; i++) {
      for (int j = 0; j < capaOculta; j++) {
        pls = pls + w[ii] * y[j];
        ii++;
      }
      s[i + capaOculta] = pls; // i = i + capa1
      y[i + capaOculta] = fun(s[i + capaOculta]); // i = i + capa1
      pls = 0;
    }

    ////// ----------Fin Ida--------/////
    ////// ******** Vuelta**********/////
    // ++++capa2 g
    // System.out
    // .println("y " + y.length + " xout " + xout.length + " , " + xout[0].length +
    ////// " capa salida " + capaSalida);

    for (int i = 0; i < capaSalida; i++) {
      // System.out.println(ci + " ci ");
      // System.out.println("y " + y.length + " xout " + xout.length + " , " +
      // xout[ci].length + " capa salida "
      // + capaSalida + " ci " + ci);
      double err = (xout[ci][i] - y[i + capaOculta]);
      double df = y[i + capaOculta] * (1 - y[i + capaOculta]);
      g[i + capaOculta] = err * df;
    }

    // ++++capa1 g
    pls = 0;
    for (int i = 0; i < capaOculta; i++) {
      for (int j = 0; j < capaSalida; j++) {
        pls = pls + w[capaIngreso * capaOculta + j * capaOculta + i] * g[capaOculta + j];
      }
      g[i] = y[i] * (1 - y[i]) * pls;
      pls = 0;
    }
    ArrayList<Thread> hilos = new ArrayList<Thread>();
    // ++++capa2 w
    ii = capaIngreso * capaOculta;// capa1*capa2
    for (int i = 0; i < capaSalida; i++) {
      int tempII = ii;
      // TODO Auto-generated method stub
      for (int j = 0; j < capaOculta; j++) {
        w[tempII] = w[tempII] + 0.01 * g[i + capaOculta] * y[j];
      }
      ii++;
    }

    // ++++capa1 w
    ii = 0;// capa0*capa1
    for (int i = 0; i < capaOculta; i++) {
      for (int j = 0; j < capaIngreso; j++) {
        w[ii] = w[ii] + 0.01 * g[i] * xin[ci][j];
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
    double prubs[] = new double[capaIngreso];

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
    for (int i = 0; i < capaOculta; i++) {
      for (int j = 0; j < capaIngreso; j++) {
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
    ii = capaIngreso * capaOculta;// capa1*capa2
    for (int i = 0; i < capaSalida; i++) {
      for (int j = 0; j < capaOculta; j++) {
        pls = pls + w[ii] * y[j];
        ii++;
      }
      s[i + capaOculta] = pls; // i = i + capa1
      y[i + capaOculta] = fun(s[i + capaOculta]); // i = i + capa1
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
    for (int i = capaOculta; i < (capaOculta + capaSalida); i++) {
      System.out.print("[" + y[i] + "] ");
    }
    System.out.println();

    // System.out.println("-----------****Fin Test****----------");

  }

}
