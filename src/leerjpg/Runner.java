
package leerjpg;

import java.util.function.Function;

public class Runner implements Runnable {
  Function<Integer, Integer> f;
  int num;

  public void run() {
    f.apply(0);
    // System.out.println(num + " " + Thread.currentThread());

  }

  public Runner(Function f) {
    this.f = f;
    // this.num = num;
  }
}
