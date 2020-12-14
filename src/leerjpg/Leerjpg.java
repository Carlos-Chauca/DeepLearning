package leerjpg;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Leerjpg  extends Component {

  public static void main(String[] foo) {
    new Leerjpg();
  }

  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
  }

  private void convertGray(BufferedImage image, String image_name) throws IOException {
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
        float rr = (float) Math.pow(red   / 255.0 , 2.2);
        float gg = (float) Math.pow(green / 255.0 , 2.2);
        float bb = (float) Math.pow(blue  / 255.0 , 2.2);
        float Y = (float) (0.2126*rr + 0.7152*gg + 0.0722*bb);
        int grayPixel = (int) (255.0 * Math.pow(Y, 1.0 / 2.2));
        int gray = (alpha << 24) + (grayPixel << 16) + (grayPixel << 8) + grayPixel;
        img.setRGB(j, i, gray);
      }
    }
    File myNewJPegFile = new File(System.getProperty("user.dir")+"\\src\\leerjpg\\"+image_name);
    ImageIO.write(img, "jpg", myNewJPegFile);
    
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

  public Leerjpg() {
    try {
        String image_name = "imagen_gris.jpg";
      // get the BufferedImage, using the ImageIO class
      BufferedImage image = 
        ImageIO.read(this.getClass().getResource("imagen.jpg"));
      marchThroughImage(image);
      //convertGray(image,image_name);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

}