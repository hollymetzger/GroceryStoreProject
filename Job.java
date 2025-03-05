import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Job {
    private double serviceTime;

    public Job(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public static void doUnitTests() {
        System.out.println("Running Job tests");

        try {
            File file = new File("Job Test Results.csv");
            FileWriter writer = new FileWriter(file);

            // Test 1: Job creation
            Job job = new Job(2.0);
            writer.write("Test 1: Job creation, " + "\n");

            // Test 2: setServiceTime
            job.setServiceTime(3.0);
            writer.write("Test 2: setServiceTime, " + "\n");

            writer.close();
            System.out.println("File created at " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
