package leerjpg;

import java.util.function.Function;

class Mythread extends Thread {
  Function<Integer, Integer> mypredicate;

  public Mythread(Function<Integer, Integer> mypredicate) {
    super();
    this.mypredicate = mypredicate;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    super.run();
    this.mypredicate.apply(0);
  }
}