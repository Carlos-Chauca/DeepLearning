package leerjpg;

import java.nio.Buffer;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.awt.MediaTracker;

public class ImageData {
  public void escalar(String folder) {
    // imagen origen
    try {
      Image img = new ImageIcon(folder).getImage();

      Image newimg = img.getScaledInstance(350, 350, java.awt.Image.SCALE_SMOOTH);

      // wait for image to be ready
      MediaTracker tracker = new MediaTracker(new java.awt.Container());
      tracker.addImage(newimg, 0);
      try {
        tracker.waitForAll();
      } catch (InterruptedException ex) {
        throw new RuntimeException("Image loading interrupted", ex);
      }

      BufferedImage bi = this.imageToBufferedImage(newimg);

      File outputfile = new File("/home/gerald/deeplearning/trabajo/DeepLearning/src/leerjpg/saved.png");
      ImageIO.write(bi, "png", outputfile);
    } catch (Exception e) {
      // TODO: handle exception
      System.err.println(e);
    }

  }

  public BufferedImage imageToBufferedImage(Image im) {
    BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
    Graphics bg = bi.getGraphics();
    bg.drawImage(im, 0, 0, null);
    bg.dispose();
    return bi;
  }

}
