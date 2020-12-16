package leerjpg;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestImage {
  public static void main(String[] args) {
    String __dirname = System.getProperty("user.dir");
    String[] categorias = { "cats", "dogs", "panda" };
    for (String categorita : categorias) {
      String path = Paths.get(__dirname, "..", "..", "animals", "gris_img", "animals", categorita).toString();

      System.out.println(path);
      File dir = new File(path);
      int count = 0;
      for (File file : dir.listFiles()) {

        // System.out.println(file.toString());
        // try {
        // System.out.println(Files.probeContentType(file.toPath()));
        // } catch (Exception e) {
        // TODO: handle exception
        // System.err.println(e);
        // }

        new ImageData().escalar(file.toString(),
            Paths.get(__dirname, "miniAnimals", categorita, count + ".png").toString());
        count++;
      }
      //

    }
  }
}
