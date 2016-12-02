package com.aizaz.samples;

public class B implements Runnable {

    private TestCallback testCallback;

    public B(TestCallback testCallback) {
        this.testCallback = testCallback;
    }

    public void doSomething() {
        System.out.println("B: I work and then sleep for a while (3 secs)");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("B: ahaa! work done. Let's make a callback");
        testCallback.callMeBack();
        System.out.println("B: I am done here");

    }

    @Override
    public void run() {
        doSomething();
    }
}
