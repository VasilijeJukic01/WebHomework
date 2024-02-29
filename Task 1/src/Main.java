import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static Semaphore assistantSemaphore = new Semaphore(1);
    public static Semaphore professorSemaphore = new Semaphore(1);
    public static AtomicInteger gradesTotal = new AtomicInteger(0);
    public static AtomicInteger studentsTotal = new AtomicInteger(0);

    public static long startTime;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int students = scanner.nextInt();

        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(students);
        Queue<Student> professorStudents = new LinkedList<>();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            try {
                Main.professorSemaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Professor professor = new Professor(professorStudents.poll(), professorStudents.poll());
            professor.start();
            try {
                professor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        startTime = System.currentTimeMillis();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Students: " + studentsTotal.get() + " Total grade average: " + (double) gradesTotal.get() / studentsTotal.get());
                System.exit(0);
            }
        }, 5000);

        for (int i = 0; i < students;) {
            if (new Random().nextBoolean()) {
                if (professorStudents.size() < 2) {
                    i += 1;
                    professorStudents.add(new Student());
                    executorServiceScheduled.schedule(() -> {
                        try {
                            cyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }, (int) (new Random().nextDouble() * 1000), TimeUnit.MILLISECONDS);
                }
            }
            else {
                if (Main.assistantSemaphore.tryAcquire()) {
                    Assistant assistant = new Assistant(new Student());
                    i += 1;
                    executorServiceScheduled.schedule(assistant, (int) (new Random().nextDouble() * 1000), TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}
