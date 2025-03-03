import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Simulation {
    private double currentTime;
    private CustomQueue<SingleServerQueue> queues;
    private ArrivalProcess arrivalGenerator;
    private CustomQueue<Job> completedJobs;

    public Simulation(ArrivalProcess arrivalGenerator, CustomQueue<SingleServerQueue> queues) {
        this.currentTime = 0.0;
        this.queues = queues;
        this.arrivalGenerator = arrivalGenerator;
        this.completedJobs = new CustomQueue<>();
    }

    public void run(double simulationTime) {
        while (currentTime < simulationTime) {
            doLoop();
        }
    }

    private void doLoop() {
        // Determine the next event (arrival or service completion)
        double nextArrivalTime = arrivalGenerator.getNextArrivalTime();
        double nextServiceCompletionTime = Double.POSITIVE_INFINITY;
        SingleServerQueue nextQueueToComplete = null;

        Node<SingleServerQueue> current = queues.getHead();
        while (current != null) {
            SingleServerQueue queue = current.getData();
            double endServiceTime = queue.getEndServiceTime();
            if (endServiceTime < nextServiceCompletionTime) {
                nextServiceCompletionTime = endServiceTime;
                nextQueueToComplete = queue;
            }
            current = current.getNext();
        }

        // Process the next event
        if (nextArrivalTime < nextServiceCompletionTime) {
            // Process arrival
            currentTime = nextArrivalTime;
            Job newJob = arrivalGenerator.nextJob();
            queues.getHead().getData().add(newJob, currentTime); // Add to the first queue for simplicity
        } else {
            // Process service completion
            currentTime = nextServiceCompletionTime;
            nextQueueToComplete.complete(currentTime);
        }
    }

public static void doUnitTests() {
            System.out.println("Running Simulation tests");

            // Mock objects for testing
            ArrivalProcess mockArrivalGenerator = new ArrivalProcess() {
                private double nextArrivalTime = 1.0;

                @Override
                public double getNextArrivalTime() {
                    return nextArrivalTime;
                }

                @Override
                public Job nextJob() {
                    nextArrivalTime += 1.0; // Increment for simplicity
                    return new Job();
                }
            };

            SingleServerQueue queue1 = new SingleServerQueue(new RandomDistribution(1.0));
            SingleServerQueue queue2 = new SingleServerQueue(new RandomDistribution(1.0));
            CustomQueue<SingleServerQueue> queues = new CustomQueue<>();
            queues.enqueue(queue1);
            queues.enqueue(queue2);

            Simulation simulation = new Simulation(mockArrivalGenerator, queues);

            // Create a log file for test results
            try {
                File file = new File("Simulation Test Results.csv");
                FileWriter writer = new FileWriter(file);

                // Test 1: Initial state
                writer.write("Test 1: Initial state, " + (simulation.currentTime == 0.0) + "\n");

                // Test 2: Run simulation for a short period
                simulation.run(3.0); // Run for 3 time units
                writer.write("Test 2: Run simulation for 3.0 time units, " + (simulation.currentTime >= 3.0) + "\n");

                // Test 3: Verify job completion and queue state
                writer.write("Test 3: Verify job completion and queue state, " + (queue1.getEndServiceTime() != Double.POSITIVE_INFINITY || queue2.getEndServiceTime() != Double.POSITIVE_INFINITY) + "\n");

                writer.close();
                System.out.println("File created at " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
