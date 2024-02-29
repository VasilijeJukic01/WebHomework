import java.util.Random;

public class Assistant extends Thread {

    private final Student student;

    public Assistant(Student student) {
        this.student = student;
    }

    @Override
    public void run() {
        try {
            long dT = System.currentTimeMillis() - Main.startTime;

            student.start();
            student.join();

            int grade = new Random().nextInt(5, 11);
            student.setGrade(grade);

            Main.studentsTotal.addAndGet(1);
            Main.gradesTotal.addAndGet(grade);

            System.out.printf("Thread: %s Arrival: %dms Assistant: %s TTC: %dms:%dms Score: %d\n",
                    student, dT, getName(), student.getTime(), dT, student.getGrade());

            Main.assistantSemaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
