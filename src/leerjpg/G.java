
package leerjpg;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class G extends JPanel {
  double[] coordinates = { 100, 20, 20, 20, 50, 100, 20, 20, 20, 50, 100, 20, 20, 20, 50 };
  int mar = 50;

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g1 = (Graphics2D) g;
    g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int width = getWidth();
    int height = getHeight();
    // ejes
    // y
    g1.draw(new Line2D.Double(mar, mar, mar, height - mar));
    // x
    g1.draw(new Line2D.Double(mar, height - mar, width - mar, height - mar));

    // double x = (double) (width - 2 * mar) / (coordinates.length - 1);
    double scaleY = (double) (height - mar) / getMax();
    double scaleX = (double) (width - 2 * mar) / coordinates.length;
    g1.drawString("" + getMax(), mar / 5, mar);
    System.out.println("min" + getMin());
    g1.drawString("" + getMin(), mar / 5, (int) (height - getMin() * scaleY));

    g1.setPaint(Color.BLUE);

    for (int i = 0; i < coordinates.length - 1; i++) {
      double y0 = height - coordinates[i] * scaleY;
      double x0 = mar + i * scaleX;
      double y1 = height - coordinates[i + 1] * scaleY;
      double x1 = mar + (i + 1) * scaleX;
      // System.out.println(" x0 " + x0 + " y0 " + y0 + " x1 " + x1 + " y1 " + y1);
      g1.draw(new Line2D.Double(x0, y0, x1, y1));
    }

    // g1.setPaint(Color.BLUE);
  }

  private double getMax() {
    double max = -Double.MAX_VALUE;
    for (int i = 0; i < coordinates.length; i++) {
      if (coordinates[i] > max)
        max = coordinates[i];

    }
    return max;
  }

  private double getMin() {
    double min = Double.MAX_VALUE;
    for (int i = 0; i < coordinates.length; i++) {
      if (coordinates[i] < min)
        min = coordinates[i];

    }
    return min;
  }

  public G(double[] coordinates) {
    this.coordinates = coordinates;
  }

  public G() {
    // this.coordinates=coordinates;
  }

  void graficar() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setSize(400, 400);
    frame.setLocation(200, 200);
    frame.setVisible(true);
  }

  // public static void main(String[] args) {
  // new G().graficar();
  // }
}