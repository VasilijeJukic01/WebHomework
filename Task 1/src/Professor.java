import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Professor extends Thread {

    private final BlockingQueue<Student[]> studentPairs;
    private final ExecutorService executorService;

    public Professor(BlockingQueue<Student[]> studentPairs) {
        this.studentPairs = studentPairs;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Student[] students = studentPairs.take();
                Student student1 = students[0];
                Student student2 = students[1];

                long dT = System.currentTimeMillis() - Main.startTime;

                CompletableFuture<Void> future1 = CompletableFuture.runAsync(student1, executorService);
                CompletableFuture<Void> future2 = CompletableFuture.runAsync(student2, executorService);

                int grade1 = new Random().nextInt(5, 11);
                int grade2 = new Random().nextInt(5, 11);
                student1.setGrade(grade1);
                student2.setGrade(grade2);

                CompletableFuture.allOf(future1, future2).join();

                Main.studentsTotal.addAndGet(2);
                Main.gradesTotal.addAndGet(grade1 + grade2);

                Stream.of(student1, student2).forEach(student -> {
                    System.out.printf("Thread: %s Arrival: %dms Professor: %s TTC: %dms:%dms Score: %d\n",
                            student, dT, getName(), student.getTime(), dT, student.getGrade());
                });

                Main.professorSemaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}