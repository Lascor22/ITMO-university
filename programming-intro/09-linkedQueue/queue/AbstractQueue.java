package queue;

abstract public class AbstractQueue implements Queue {
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    abstract protected void enqueueImpl(Object element);

    @Override
    public void enqueue(Object element) {
        assert element != null;
        size++;
        enqueueImpl(element);
    }

    abstract protected Object elementImpl();

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    abstract protected void dequeueImpl();

    @Override
    public Object dequeue() {
        Object temp = element();
        dequeueImpl();
        size--;
        return temp;
    }

    abstract protected void clearImpl();

    @Override
    public void clear() {
        clearImpl();
        size = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size()];
        for (int i = 0; i < size(); i++) {
            temp[i] = dequeue();
            enqueue(temp[i]);
        }
        return temp;
    }

}
