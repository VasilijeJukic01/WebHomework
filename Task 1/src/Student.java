import java.util.Random;
import java.util.function.Supplier;

public class Student extends Thread {

    private int grade;

    private static final Supplier<Long> SLEEP_TIME_SUPPLIER = () -> {
        Random rand = new Random();
        double value = rand.nextDouble() * 0.5 + 0.5;
        return (long) (value * 1000);
    };

    @Override
    public void run() {
        try {
            Thread.sleep(SLEEP_TIME_SUPPLIER.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Student " + super.getName();
    }

    // Getters and setters

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getTime() {
        return SLEEP_TIME_SUPPLIER.get();
    }

}
