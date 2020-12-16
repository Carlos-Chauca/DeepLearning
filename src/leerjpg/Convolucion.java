package leerjpg;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Arrays;

public final class Convolucion extends Component {

  public static void main(String[] foo) throws IOException {
    lectura();
  }

  public static void lectura() throws IOException {
    String __dirname = System.getProperty("user.dir");
    String[] categorias = { "cats", "dogs", "panda" };
    String path = Paths.get(__dirname, "..", "..", "animals", "gris_img", "animals", "cats").toString();

    // String path =
    // "C:\\Users\\carlo\\Documents\\NetBeansProjects\\leerjpg\\src\\leerjpg\\gris_img\\animals\\cats";
    String[] files = getFiles(path);
    double[][] mat = new double[60][25];
    if (files != null) {
      int size = files.length;

      for (int i = 0; i < size; i++) {
        // System.out.println( files[ i ] );
        BufferedImage img = ImageIO.read(new File(files[i]));
        double[][] imagenmatrix = escalar(img);
        double[] entrada = new double[25];
        entrada = cnn(imagenmatrix);

        // System.out.printf("\nVector generado de entrada a la red neuronal de la
        // imagen"+ files[i]+ ":\n");
        for (int aux = 0; aux < 25; aux++) {
          System.out.printf("%11.4f", entrada[aux]);
          // mat[size][aux]=(int)entrada[aux];
          // System.out.printf("%11.4f",mat[size][aux]);
        }
        System.out.printf("\n\n=========\n\n");
      }
    }
  }

  public static double[][] poolprove(double[][] m) {
    double[][] aux = new double[m.length / 2][m.length / 2];
    double[][] aux2 = new double[2][2];
    for (int i = 0; i < m.length / 2; ++i) {
      for (int j = 0; j < m.length / 2; ++j) {
        aux2[0][0] = m[2 * i][2 * j];
        aux2[0][1] = m[2 * i][2 * j + 1];
        aux2[1][0] = m[2 * i + 1][2 * j];
        aux2[1][1] = m[2 * i + 1][2 * j + 1];
        aux[i][j] = mamixmomatriz(aux2);

      }
    }
    return aux;
  }

  public static void printmatriz(double[][] m) {
    for (int x = 0; x < m.length; x++) {
      for (int y = 0; y < m[x].length; y++) {
        System.out.printf("%11.1f", m[x][y]);
      }
      System.out.printf("\n");
    }
  }

  public static void mostrar(BufferedImage img) {
    JLabel label = new JLabel(new ImageIcon(img));
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(label);
    f.pack();
    f.setLocation(200, 200);
    f.setVisible(true);

  }

  public static double[][] matrixrelu(double[][] m) {
    // double n[][] = new double[ n.length][n.length];
    for (int x = 0; x < m.length; x++) {
      for (int y = 0; y < m[x].length; y++) {
        m[x][y] = relu(m[x][y]);
      }
      // System.out.printf("\n");
    }
    return m;
  }

  public static double relu(double n) {
    if (n > 0) {
      return n;
    } else {
      return 0;
    }
  }

  public static double mamixmomatriz(double[][] m) {
    double[] v = new double[m.length * 2];
    int cont = 0;
    for (int i = 0; i < m.length; i++) {
      // System.out.println("cont:"+cont);

      for (int j = 0; j < m[i].length; j++) {
        v[cont] = m[i][j];
        cont++;
        // System.out.println("cont:"+cont);
      }
    }
    double n = maximovector(v);
    return n;
  }

  public static double maximovector(double[] v) {
    double max = v[0];
    for (int i = 1; i < v.length; i++) {
      if (v[i] > max) {
        max = v[i];
      }
    }
    return max;
  }

  public static double[][] escalar(BufferedImage img) {
    double matriz[][] = new double[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        // double mediapixel, colorSRGB;
        Color c = new Color(img.getRGB(j, i));
        double pi = c.getRed();
        // System.out.printf("%.2f " ,(pi/255));
        matriz[i][j] = (pi / 255);
      }
      // System.out.println(" ");
    }
    return matriz;
  }

  public static double[][] convolucion(double[][] m, double[][] n) {
    double[][] mcon = new double[m.length - n.length + 1][m.length - n.length + 1];
    for (int x = 0; x < m.length - n.length + 1; x++) {
      for (int y = 0; y < m.length - n.length + 1; y++) {
        double[][] aux = new double[n.length][n.length];
        for (int k = x, a = 0; k < n.length + x; k++, a++) {
          for (int l = y, b = 0; l < n.length + y; l++, b++) {
            // System.out.printf("%11.1f", m[k][l]);
            aux[a][b] = m[k][l];
            // System.out.printf("%11.1f", aux[a][b]);
          }
          // System.out.printf("\n");
        }
        mcon[x][y] = multiplicación(aux, n);
        // System.out.println("------");
      }
    }
    return mcon;
  }

  public static double multiplicación(double[][] A, double[][] B) {
    double a = 0;
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < B.length; j++) {
        a = a + (A[i][j] * B[i][j]);
      }
    }
    return a;
  }

  public static void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);

  }

  public static double[] cnn(double[][] imagenmatrix) {
    // kernel 1
    double[][] kernel_1 = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };
    // kernel 2
    double[][] kernel_2 = { { -1, -2, 1 }, { 0, 0, 0 }, { 1, 1, -1 } };
    // generamos las 2 primeras convoluciones
    double[][] conv1_1 = new double[imagenmatrix.length - kernel_1.length + 1][imagenmatrix.length - kernel_1.length
        + 1];
    double[][] conv1_2 = new double[imagenmatrix.length - kernel_2.length + 1][imagenmatrix.length - kernel_2.length
        + 1];
    conv1_1 = (convolucion(imagenmatrix, kernel_1));
    conv1_2 = (convolucion(imagenmatrix, kernel_2));
    // maxpooling de las 2 convoluciones generadas
    double[][] pooling1_1 = new double[conv1_1.length / 2][conv1_1.length / 2];
    double[][] pooling1_2 = new double[conv1_2.length / 2][conv1_2.length / 2];
    pooling1_1 = poolprove(matrixrelu(conv1_1));
    pooling1_2 = poolprove(matrixrelu(conv1_2));

    // generamos las 4 segundas convoluciones
    double[][] conv2_1 = new double[pooling1_1.length - kernel_1.length + 1][pooling1_1.length - kernel_1.length + 1];
    double[][] conv2_2 = new double[pooling1_1.length - kernel_1.length + 1][pooling1_1.length - kernel_1.length + 1];
    double[][] conv2_3 = new double[pooling1_2.length - kernel_2.length + 1][pooling1_2.length - kernel_2.length + 1];
    double[][] conv2_4 = new double[pooling1_2.length - kernel_2.length + 1][pooling1_2.length - kernel_2.length + 1];
    conv2_1 = (convolucion(pooling1_1, kernel_1));
    conv2_2 = (convolucion(pooling1_1, kernel_2));
    conv2_3 = (convolucion(pooling1_2, kernel_1));
    conv2_4 = (convolucion(pooling1_2, kernel_2));

    // maxpooling de las 4 convoluciones generadas
    double[][] pooling2_1 = new double[conv2_1.length / 2][conv2_1.length / 2];
    double[][] pooling2_2 = new double[conv2_2.length / 2][conv2_2.length / 2];
    double[][] pooling2_3 = new double[conv2_3.length / 2][conv2_3.length / 2];
    double[][] pooling2_4 = new double[conv2_4.length / 2][conv2_4.length / 2];
    pooling2_1 = poolprove(matrixrelu(conv2_1));
    pooling2_2 = poolprove(matrixrelu(conv2_2));
    pooling2_3 = poolprove(matrixrelu(conv2_3));
    pooling2_4 = poolprove(matrixrelu(conv2_4));

    /*
     * imprimimos las 4 matrices generadas a traves de 2 convoluciones de la imagen
     * inicial System.out.printf("-----Matriz 1:\n"); printmatriz(pooling2_1);
     * System.out.printf("-----Matriz 2:\n"); printmatriz(pooling2_2);
     * System.out.printf("-----Matriz 3:\n"); printmatriz(pooling2_3);
     * System.out.printf("-----Matriz 4:\n"); printmatriz(pooling2_4);
     * 
     */
    // agregamos las matrices a un vector
    // dim contiene la dimension del vector
    int dim = pooling2_1.length * pooling2_1.length * 4;
    // generamos el vector de dimension dim
    double[] vector_ini = new double[dim];
    int cont = 0;
    // añadimos la 1ra matriz a los 25 primeros valores del vector
    for (int i = 0; i < pooling2_1.length; i++) {
      for (int j = 0; j < pooling2_1.length; j++) {
        vector_ini[cont] = pooling2_1[i][j];
        cont++;
      }
    }
    // añadimos la 2da matriz a los 25 proximos valores del vector
    for (int i = 0; i < pooling2_2.length; i++) {
      for (int j = 0; j < pooling2_2.length; j++) {
        vector_ini[cont] = pooling2_2[i][j];
        cont++;
      }
    }

    // añadimos la 3ra matriz a los 25 proximos valores del vector
    for (int i = 0; i < pooling2_3.length; i++) {
      for (int j = 0; j < pooling2_3.length; j++) {
        vector_ini[cont] = pooling2_3[i][j];
        cont++;
      }
    }

    // añadimos la 4ta matriz a los 25 proximos valores del vector
    for (int i = 0; i < pooling2_4.length; i++) {
      for (int j = 0; j < pooling2_4.length; j++) {
        vector_ini[cont] = pooling2_4[i][j];
        cont++;
      }
    }
    int dim_2 = (pooling2_1.length * pooling2_1.length) / 100;
    // generamos el vector de dimension dim
    double[] vector_fin = new double[dim_2];
    int cont_2 = 0;
    for (int k = 0; k < dim_2; k++) {
      vector_fin[cont_2] = 0;
      for (int z = 0; z < 100; z++) {
        vector_fin[cont_2] = vector_fin[cont_2] + vector_ini[(cont_2) * 100 + z];
      }
      cont_2++;
    }
    double max = maximovector(vector_fin);

    for (int k = 0; k < dim_2; k++) {
      vector_fin[k] = vector_fin[k] / max;
    }
    return vector_fin;
  }

  public static String[] getFiles(String dir_path) {

    String[] arr_res = null;

    File f = new File(dir_path);

    if (f.isDirectory()) {

      List<String> res = new ArrayList<>();
      File[] arr_content = f.listFiles();

      int size = arr_content.length;

      for (int i = 0; i < size; i++) {

        if (arr_content[i].isFile())
          res.add(arr_content[i].toString());
      }

      arr_res = res.toArray(new String[0]);

    } else
      System.err.println("¡ Path NO válido !");

    return arr_res;
  }

  private static void marchThroughImage(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    System.out.println("width, height: " + w + ", " + h);
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        printPixelARGB(pixel);
        System.out.println("");
      }
    }
  }

  public static void convertGray(BufferedImage image, String image_name) throws IOException {
    int w = image.getWidth();
    int h = image.getHeight();
    BufferedImage img = image;
    System.out.println("width, height: " + w + ", " + h);
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int pixel = img.getRGB(j, i);
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        float rr = (float) Math.pow(red / 255.0, 2.2);
        float gg = (float) Math.pow(green / 255.0, 2.2);
        float bb = (float) Math.pow(blue / 255.0, 2.2);
        float Y = (float) (0.2126 * rr + 0.7152 * gg + 0.0722 * bb);
        int grayPixel = (int) (255.0 * Math.pow(Y, 1.0 / 2.2));
        int gray = (alpha << 24) + (grayPixel << 16) + (grayPixel << 8) + grayPixel;
        img.setRGB(j, i, gray);
      }
    }
    File myNewJPegFile = new File(
        System.getProperty("user.dir") + "\\src\\leerjpg\\gris_img\\images\\" + image_name + ".jpg");
    ImageIO.write(img, "jpg", myNewJPegFile);
  }
}
