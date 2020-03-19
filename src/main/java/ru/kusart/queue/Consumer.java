package ru.kusart.queue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable
{
    protected BlockingQueue<Object> queue;

    public Consumer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }

    public void run() {
        try
        {
            while (true)
            {
                Object obj = queue.take();
                System.out.println("Consumed resource - Queue size now = "  + queue.size());
                take(obj);
            }
        }
        catch (InterruptedException ex)
        {
            System.out.println("CONSUMER INTERRUPTED");
        }
    }

    void take(Object obj)
    {
        try
        {
            Thread.sleep(100); // simulate time passing
        }
        catch (InterruptedException ex)
        {
            System.out.println("Consumer Read INTERRUPTED");
        }
        System.out.println("Consuming object " + obj);
    }
}
