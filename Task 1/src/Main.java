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
        BlockingQueue<Student[]> professorStudentPairs = new LinkedBlockingQueue<>();
        Professor professor = new Professor(professorStudentPairs);
        professor.start();

        BlockingQueue<Student> assistantStudents = new LinkedBlockingQueue<>();
        Assistant assistant = new Assistant(assistantStudents);
        assistant.start();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            try {
                Main.professorSemaphore.acquire();
                professorStudentPairs.add(new Student[]{professorStudents.poll(), professorStudents.poll()});
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
                    i += 1;
                    executorServiceScheduled.schedule(() -> assistantStudents.add(new Student()),
                            (int) (new Random().nextDouble() * 1000), TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}
