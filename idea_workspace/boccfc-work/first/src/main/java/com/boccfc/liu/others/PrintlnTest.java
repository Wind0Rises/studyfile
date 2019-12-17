package com.boccfc.liu.others;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class PrintlnTest {

    public static void main(String[] args) throws InterruptedException {
        NumberPrintln numberPrintln = new PrintlnTest().new NumberPrintln();
        LetterPrintln letterPrintln = new PrintlnTest().new LetterPrintln();

        Thread numberThread = new Thread(numberPrintln, "number");
        Thread letterThread = new Thread(letterPrintln, "letter");

        numberPrintln.setLetterPrivate(letterThread);
        letterPrintln.setNumberPrivate(numberThread);

        numberThread.start();
        TimeUnit.SECONDS.sleep(1);
        letterThread.start();
    }

    class NumberPrintln implements Runnable {

        private Thread letterPrivate;

        public Thread getLetterPrivate() {
            return letterPrivate;
        }

        public void setLetterPrivate(Thread letterPrivate) {
            this.letterPrivate = letterPrivate;
        }

        @Override
        public void run() {
            for (int i = 1; i < 53; i = i+2) {
                System.out.print(i + " " + (i + 1) + " ");
                LockSupport.unpark(letterPrivate);
                LockSupport.park();
            }
            System.out.println("Number");
        }
    }

    class LetterPrintln implements Runnable {

        private Thread numberPrivate;

        public Thread getNumberPrivate() {
            return numberPrivate;
        }

        public void setNumberPrivate(Thread numberPrivate) {
            this.numberPrivate = numberPrivate;
        }

        @Override
        public void run() {
            for (int i = 0; i < 26; i++) {
                char ch = (char) ('a' + i);
                System.out.print(ch + " ");
                System.out.println("");
                LockSupport.unpark(numberPrivate);
                LockSupport.park();
            }
            System.out.println("");
            System.out.println("Letter");
            LockSupport.unpark(numberPrivate);
        }
    }


}
