import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class ArrivalProcess {
    private ExponentialDistribution distribution;
    private double nextArrivalTime;

    public ArrivalProcess(ExponentialDistribution distribution) {
        this.distribution = distribution;
        this.nextArrivalTime = 0.0;
    }

    public Job nextJob() {
        double interArrivalTime = distribution.sample();
        nextArrivalTime += interArrivalTime;
        return new Job(interArrivalTime);
    }

    public double getNextArrivalTime() {
        return nextArrivalTime;
    }


        public static void doUnitTests() {
            System.out.println("Running Arrival Process tests");

            // Mock object for testing
            ExponentialDistribution mockDistribution = new ExponentialDistribution(1.0) {
                @Override
                public double sample() {
                    return 1.0; // Fixed value for simplicity
                }
            };

            ArrivalProcess arrivalProcess = new ArrivalProcess(mockDistribution);

            // Create a log file for test results
            try {
                File file = new File("Arrival Process Test Results.csv");
                FileWriter writer = new FileWriter(file);

                // Test 1: Initial state
                writer.write("Test 1: Initial state, " + (arrivalProcess.getNextArrivalTime() == 0.0) + "\n");

                // Test 2: Generate first job
                arrivalProcess.nextJob();
                writer.write("Test 2: Generate first job, " + (arrivalProcess.getNextArrivalTime() == 1.0) + "\n");

                // Test 3: Generate second job
                arrivalProcess.nextJob();
                writer.write("Test 3: Generate second job, " + (arrivalProcess.getNextArrivalTime() == 2.0) + "\n");

                writer.close();
                System.out.println("File created at " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();

        }
    }

}
