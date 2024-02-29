import java.util.Random;
import java.util.stream.Stream;

public class Professor extends Thread {

    private final Student student1;
    private final Student student2;

    public Professor(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    @Override
    public void run() {
        try {
            long dT = System.currentTimeMillis() - Main.startTime;

            student1.start();
            student2.start();

            student1.join();
            student2.join();

            int grade1 = new Random().nextInt(5, 11);
            int grade2 = new Random().nextInt(5, 11);
            student1.setGrade(grade1);
            student2.setGrade(grade2);

            Main.studentsTotal.addAndGet(2);
            Main.gradesTotal.addAndGet(grade1 + grade2);

            Stream.of(student1, student2).forEach(student -> {
                System.out.printf("Thread: %s Arrival: %dms Professor: %s TTC: %dms:%dms Score: %d\n",
                        student, dT, getName(), student.getTime(), dT, student.getGrade());
            });

            Main.professorSemaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}