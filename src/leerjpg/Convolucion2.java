package leerjpg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.Arrays;

class pathName {
  String name;
  String path;

  public pathName(String name, String path) {
    this.name = name;
    this.path = path;
  }
}

@FunctionalInterface
interface MatOperations {
  void apply();
}

class CallConv implements Callable<Double[][]> {
  MatOperations f;

  public CallConv(MatOperations f) {
    this.f = f;
  }

  @Override
  public Double[][] call() throws Exception {
    // TODO Auto-generated method stub
    f.apply();
    return null;
  }
}

@FunctionalInterface
interface ConvolucionPool {
  double[][] apply(double[][] imageMatrix, double[][] kernel);
}

public class Convolucion2 {
  MatrizService matrizService = new MatrizService();

  public ArrayList<MyData> lectura() {
    String __dirname = System.getProperty("user.dir");
    String[] cate = // { "BEANS", "CAKE","" };
        // { "BEANS", "CAKE", "CANDY", "CEREAL", "CHIPS", "CHOCOLATE", "COFFEE", "CORN",
        // "FISH", "FLOUR", "HONEY", "JAM",
        // "JUICE", "MILK", "NUTS", "OIL", "PASTA", "RICE", "SODA", "SPICES", "SUGAR",
        // "TEA", "TOMATO_SAUCE",
        // "VINEGAR", "WATER" };
        { "1", "2", "3", "4", "5" };
    List<String> categorias = Arrays.asList(cate);
    List<pathName> rutas = new ArrayList<pathName>();
    Function<String, String> getMiniA = (dir) -> Paths.get(__dirname, "miniAnimals", "img_parcial", dir).toString();
    categorias.forEach(x -> rutas.add(new pathName(x, getMiniA.apply(x))));
    double[] y = { 0 };// sino no deja usar en el escope de para el foreach
    // String path =
    // "C:\\Users\\carlo\\Documents\\NetBeansProjects\\leerjpg\\src\\leerjpg\\gris_img\\animals\\cats";
    ArrayList<MyData> ret = new ArrayList<MyData>();
    System.out.println(cate.length);
    rutas.forEach(path -> {
      String[] files = getFiles(path.path);
      if (files != null) {
        int i = 0;
        double[][] entradas = new double[files.length][];

        for (String f : files) {

          // System.out.println("entrada numero " + i);
          entradas[i] = ImageConvolucion(f);
          ret.add(new MyData(matrizService.toArrayList(entradas[i]), y[0]));
          i++;
          // System.out.printf("\n\n=========\n\n");
        }
        // ret.add(matrizService.vectorizar(entradas));
        escritura(entradas, path.name);

      }
      y[0] = y[0] + 1;
    });
    return ret;
  }

  public double[] ImageConvolucion(String file) {
    try {
      BufferedImage img = ImageIO.read(new File(file));
      double[][] imagenmatrix = escalar(img);
      return cnn(imagenmatrix);
    } catch (IOException e) {
      return null;
      // TODO: handle exception
    }
  }

  public void escritura(double[][] entradas, String name) {
    FileWriter fichero = null;
    PrintWriter pw = null;

    try {
      fichero = new FileWriter(name);
      pw = new PrintWriter(fichero);
      // entradasList.forEach(x -> pw.println(x.toString()));

      for (int i = 0; i < entradas.length; i++)
        pw.println(matrizService.vec2String(entradas[i]));

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Nuevamente aprovechamos el finally para
        // asegurarnos que se cierra el fichero.
        if (null != fichero)
          fichero.close();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }

  public double[][] poolprove(double[][] m) {
    double[][] aux = new double[m.length / 2][m.length / 2];
    double[][] aux2 = new double[2][2];
    for (int i = 0; i < m.length / 2; ++i) {
      for (int j = 0; j < m.length / 2; ++j) {
        aux2[0][0] = m[2 * i][2 * j];
        aux2[0][1] = m[2 * i][2 * j + 1];
        aux2[1][0] = m[2 * i + 1][2 * j];
        aux2[1][1] = m[2 * i + 1][2 * j + 1];
        aux[i][j] = matrizService.mamixmomatriz(aux2);

      }
    }
    return aux;
  }

  public void mostrar(BufferedImage img) {
    JLabel label = new JLabel(new ImageIcon(img));
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(label);
    f.pack();
    f.setLocation(200, 200);
    f.setVisible(true);

  }

  public double[][] escalar(BufferedImage img) {
    double matriz[][] = new double[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        // double mediapixel, colorSRGB;
        Color c = new Color(img.getRGB(j, i));
        double pi = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        // System.out.printf("%.2f " ,(pi/255));
        matriz[i][j] = (pi / 255);
      }
      // System.out.println(" ");
    }
    return matriz;
  }

  public double[][] convolucion(double[][] m, double[][] n) {
    double[][] mcon = new double[m.length - n.length + 1][m.length - n.length + 1];
    ArrayList<Double> vectN = matrizService.vectorizar(n);
    for (int x = 0; x < m.length - n.length + 1; x++) {
      for (int y = 0; y < m.length - n.length + 1; y++) {
        Double[] aux3 = matrizService.cortar(m, x, y, n.length + x, n.length + y);
        mcon[x][y] = matrizService.multiplicación2(aux3, vectN);
      }
    }
    return mcon;
  }

  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);

  }

  public double[] cnn(double[][] imagenmatrix) {

    double[][][] kernels = { // kernel 1
        { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } },
        // kernel 2
        { { -1, -2, 1 }, { 0, 0, 0 }, { 1, 1, -1 } } };

    ConvolucionPool convolucionPool = (image, kernel) -> {
      double[][] conv = convolucion(image, kernel);
      return poolprove(matrizService.matrixrelu(conv));
    };

    double[][][] pooling1_ = new double[2][][];
    // generamos las 2 primeras convoluciones

    // maxpooling de las 2 convoluciones generadas
    pooling1_[0] = convolucionPool.apply(imagenmatrix, kernels[0]); // poolprove(matrixrelu(conv1_1));
    pooling1_[1] = convolucionPool.apply(imagenmatrix, kernels[1]);// poolprove(matrixrelu(conv1_2));

    // double[][] pooling1_1_alter = convolucionPool.apply(imagenmatrix, kernel_1);
    // System.out.println(compare(pooling1_1, pooling1_1_alter));
    final double[][][] pooling2_ = new double[4][][];

    // generamos las 4 segundas convoluciones

    // maxpooling de las 4 convoluciones generadas
    ExecutorService e = Executors.newFixedThreadPool(4);
    List<CallConv> list = new ArrayList<>();
    for (int i = 0; i < pooling1_.length; i++) {
      for (int j = 0; j < kernels.length; j++) {
        int i2 = i;
        int j2 = j;
        MatOperations f = () -> {
          pooling2_[i2 * 2 + j2] = convolucionPool.apply(pooling1_[i2], kernels[j2]);
        };

        list.add(new CallConv(f));

      }
    }
    try {
      e.invokeAll(list);
    } catch (InterruptedException er) {
      // TODO: handle exception
      er.printStackTrace();
    }
    e.shutdown();

    ArrayList<Double> vector_ini; // part1, part2, part3, part4;
    vector_ini = new ArrayList<Double>();
    for (double[][] pool : pooling2_) {
      vector_ini.addAll(matrizService.vectorizar(pool));
    }

    int dim_2 = (pooling2_[0].length * pooling2_[0].length) / 100;
    // generamos el vector de dimension dim
    double[] vector_fin = new double[dim_2];
    int cont_2 = 0;
    for (int k = 0; k < dim_2; k++) {
      vector_fin[cont_2] = 0;
      for (int z = 0; z < 100; z++) {
        vector_fin[cont_2] = vector_fin[cont_2] + vector_ini.get((cont_2) * 100 + z);
      }
      cont_2++;
    }
    double max = matrizService.maximovector(vector_fin);
    // System.out.println(pooling1_[0].length + " " + pooling2_[0].length + " " +
    // dim_2);
    for (int k = 0; k < dim_2; k++) {
      vector_fin[k] = vector_fin[k] / max;
    }
    return vector_fin;
  }

  public String[] getFiles(String dir_path) {
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

  private void marchThroughImage(BufferedImage image) {
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

  public void convertGray(BufferedImage image, String image_name) throws IOException {
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
