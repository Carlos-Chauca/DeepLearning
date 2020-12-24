package leerjpg;

import java.util.ArrayList;

public class MatrizService {
  public String mat2String(double[][] m) {
    String s = "";
    for (double[] ds : m) {
      for (double ds2 : ds) {
        s += " " + ds2;
      }
    }
    return s;
  }

  public String vec2String(Double[] v) {
    String s = "";
    for (Double double1 : v) {
      s += " " + double1;
    }
    return s;
  }

  public String vec2String(double[] v) {
    String s = "";
    for (double double1 : v) {
      s += " " + double1;
    }
    return s;
  }

  public String mat2String(Double[][] m) {
    String s = "";
    for (Double[] ds : m) {
      for (Double ds2 : ds) {
        s += " " + ds2;
      }
    }
    return s;
  }

  public Double[] cortar(double[][] mat, int x0, int y0, int xf, int yf) {
    Double[] ret = new Double[(xf - x0) * (yf - y0)];
    int k = 0;
    for (int i = x0; i < xf; i++) {
      for (int j = y0; j < yf; j++) {
        ret[k++] = mat[i][j];
      }
    }
    return ret;
  }

  public double[][] matrixrelu(double[][] m) {
    // double n[][] = new double[ n.length][n.length];
    for (int x = 0; x < m.length; x++) {
      for (int y = 0; y < m[x].length; y++) {
        m[x][y] = m[x][y] > 0 ? m[x][y] : 0;
      }
      // System.out.printf("\n");
    }
    return m;
  }

  public double mamixmomatriz(double[][] m) {
    double max = m[0][0];
    for (int i = 0; i < m.length; i++) {
      double temp = maximovector(m[i]);
      max = (max < temp ? temp : max);
    }
    return max;
  }

  public double maximovector(double[] v) {
    double max = v[0];
    for (int i = 1; i < v.length; i++) {
      if (v[i] > max) {
        max = v[i];
      }
    }
    return max;
  }

  public boolean compare(double[][] mat1, double[][] mat2) {
    if (!(mat1.length == mat2.length && mat1[0].length == mat2[0].length)) {
      System.out.println("dif leng  ");
      return false;
    }
    int i, j = 0;
    for (double[] doubles : mat2) {
      i = 0;
      for (double doubles2 : doubles) {
        if ((double) doubles2 != (double) mat1[j][i]) {
          System.out.println("dif value   " + (double) doubles2 + " " + (double) mat1[i][j]);
          return false;
        }
        i++;
      }
      j++;
    }
    return true;
  }

  public double multiplicación2(Double[] v1, ArrayList<Double> v2) {
    double d = 0;
    for (int i = 0; i < v1.length; i++) {
      d += v1[i] * v2.get(i);
    }

    return d;
  }

  public ArrayList<Double> vectorizar(double[][] mat) {
    ArrayList<Double> a = new ArrayList<Double>();
    for (double[] vector : mat) {
      for (double value : vector) {
        a.add(value);
      }
    }

    return a;
  }

  public void printmatriz(double[][] m) {
    for (int x = 0; x < m.length; x++) {
      for (int y = 0; y < m[x].length; y++) {
        System.out.printf("%11.1f", m[x][y]);
      }
      System.out.printf("\n");
    }
  }

  public void printmatriz(ArrayList<ArrayList<Double>> m) {
    for (int x = 0; x < m.size(); x++) {
      for (int y = 0; y < m.get(0).size(); y++) {
        System.out.print(m.get(x).get(y) + " ");
      }
      System.out.println();
    }
  }

  public void print(double[] mat) {
    for (double d : mat) {
      System.out.print(d);
    }

  }

  public void printData(ArrayList<MyData> m) {
    for (int x = 0; x < m.size(); x++) {
      for (int y = 0; y < m.get(0).X.size(); y++) {
        System.out.print(m.get(x).X.get(y) + " ");
      }
      System.out.println(" " + m.get(x).Y);

    }
  }

  public ArrayList<Double> toArrayList(double[] V) {
    ArrayList<Double> ret = new ArrayList<Double>();
    for (double v : V) {
      ret.add((Double) (v));
    }
    return ret;
  }

  public double[] getYArray(ArrayList<MyData> myDatas) {
    double[] ret = new double[myDatas.size()];
    for (int i = 0; i < myDatas.size(); i++) {
      ret[i] = myDatas.get(i).Y;
    }
    return ret;
  }

  public double[][] getXArray(ArrayList<MyData> myDatas) {

    double[][] ret = new double[myDatas.size()][];
    for (int i = 0; i < myDatas.size(); i++) {
      ret[i] = new double[myDatas.get(i).X.size()];
      for (int j = 0; j < myDatas.get(i).X.size(); j++) {
        ret[i][j] = myDatas.get(i).X.get(j);

      }
    }
    return ret;
  }

}
