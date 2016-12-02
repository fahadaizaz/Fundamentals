package com.aizaz.samples;

/**
 * Created by faizaz on 02.12.16.
 */
public class A implements TestCallback {

    public void doSomething() {
        System.out.println("A: I must ask B to do something for me");
        new Thread(new B(this)).start();
        System.out.println("A: while B is working, I must take rest (1 sec)");
        System.out.println("A: Yawning ... ");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("A: I am done here");
    }

    @Override
    public void callMeBack() {
        System.out.println("A: Yippie! someone called me back");
    }
}
