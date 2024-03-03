import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Assistant extends Thread {

    private final BlockingQueue<Student> students;

    public Assistant(BlockingQueue<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Student student = students.take();

                long dT = System.currentTimeMillis() - Main.startTime;

                student.start();
                int grade = new Random().nextInt(5, 11);
                student.setGrade(grade);
                student.join();

                Main.studentsTotal.addAndGet(1);
                Main.gradesTotal.addAndGet(grade);

                System.out.printf("Thread: %s Arrival: %dms Assistant: %s TTC: %dms:%dms Score: %d\n",
                        student, dT, getName(), student.getTime(), dT, student.getGrade());

                Main.assistantSemaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
