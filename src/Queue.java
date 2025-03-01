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
        // todo: add tests
    }

}
