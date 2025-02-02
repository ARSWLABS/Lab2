package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private final PrimeFinderThread[] pft;
    private final Object lock = new Object(); // Monitor de sincronización

    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, lock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1, lock);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (PrimeFinderThread thread : pft) {
            thread.start();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);

                // Pausar todos los hilos
                for (PrimeFinderThread thread : pft) {
                    thread.pauseThread();
                }

                // Mostrar cantidad de primos encontrados
                int totalPrimes = 0;
                for (PrimeFinderThread thread : pft) {
                    totalPrimes += thread.getPrimes().size();
                }
                System.out.println("\nPrimos encontrados hasta ahora: " + totalPrimes);
                System.out.println("Presione ENTER para continuar...");

                scanner.nextLine(); // Espera la entrada del usuario

                // Reanudar todos los hilos
                for (PrimeFinderThread thread : pft) {
                    thread.resumeThread();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
