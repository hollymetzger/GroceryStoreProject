import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Queue<T> {

    // Private fields
    private QueueRecord head; // the head is the "first in" and next in line for service
    private QueueRecord tail; // the tail is the entry point to the queue

    private class QueueRecord {
        public T data;
        public QueueRecord next;
        public QueueRecord(T obj) {
            data = obj;
        }
    }

    // Constructor
    public Queue() {
        head = null;
        tail = null;
    }

    // Public methods
    public void enqueue(T obj) {
        QueueRecord newRecord = new QueueRecord(obj);
        if (head == null) {
            head = newRecord;
            tail = head;
        }
        else {
            tail.next = newRecord;
            tail = newRecord;
        }
        newRecord.next = null;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        else {
            QueueRecord tmp = head;
            head = head.next;
            tmp.next = null; // is this line necessary?
            return tmp.data;
        }
    }

    public void doUnitTests() {
        System.out.println("Running Queue tests");

        try {
            File file = new File("Queue Test Results.csv");
            FileWriter writer = new FileWriter(file);

            // Test 1: Queue initialization
            Queue<String> queue = new Queue<>();
            writer.write("Test 1: Queue initialization, " + queue.isEmpty() + "\n");

            // Test 2: Enqueue operation
            queue.enqueue("item1");
            writer.write("Test 2: Enqueue operation, " + ("item1".equals(queue.head.data)) + "\n");

            // Test 3: Enqueue multiple items
            queue.enqueue("item2");
            writer.write("Test 3: Enqueue multiple items, " + ("item2".equals(queue.head.next.data)) + "\n");

            // Test 4: Dequeue operation
            String dequeuedItem = queue.dequeue();
            writer.write("Test 4: Dequeue operation, " + ("item1".equals(dequeuedItem)) + "\n");

            // Test 5: Dequeue until empty
            queue.dequeue();
            writer.write("Test 5: Dequeue until empty, " + queue.isEmpty() + "\n");

            writer.close();
            System.out.println("File created at " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

}
