package hw15.generator;

public interface Generator<T> {

    public boolean hasNext();

    public T getNext();
}

