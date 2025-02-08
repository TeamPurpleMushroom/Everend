package net.purplemushroom.neverend.util.datastructures;

public class CircularQueue<T> {
    private T[] data;
    private int currentIndex = 0;

    public CircularQueue(int size) {
        data = (T[]) new Object[size];
    }

    public void enqueue(T value) {
        data[currentIndex] = value;
        currentIndex = (currentIndex + 1) % data.length;
    }

    public T dequeue() {
        currentIndex = getPreviousIndex();
        T val = data[currentIndex];
        if (val != null) data[currentIndex] = null;
        return val;
    }

    public T peek() {
        return data[getPreviousIndex()];
    }

    public void clear() {
        data = (T[]) new Object[data.length];
    }

    public boolean isEmpty() {
        return data[getPreviousIndex()] == null;
    }

    private int getPreviousIndex() {
        return currentIndex == 0 ? data.length - 1 : currentIndex - 1;
    }
}
