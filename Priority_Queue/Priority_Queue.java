
import java.util.ArrayList;

public class Priority_Queue <E extends Comparable<E>> {
    private ArrayList<E> array = new ArrayList<>(); //используемый контейнер

    public  void add_elem(int priority, E data){
        if(data == null)
            throw new NullPointerException();
        int index = array.size();

        array.add((E) new QueueObject<>(priority,data));

        while (hasParent(index) && parent(index).compareTo(array.get(index)) < 0) {
            E tmp1 = array.get(parentIndex(index));
            array.set(parentIndex(index),array.get(index));
            array.set(index,tmp1);
            index = parentIndex(index);
        }
    }

    public void remove_elem() {
        array.set(0, array.get(array.size() - 1));
        array.remove(array.size() - 1);
        int index = 0;
        while (hasLeftChild(index)) {
            int largestChild = leftIndex(index);
            if (hasRightChild(index) && array.get(leftIndex(index)).compareTo(array.get(rightIndex(index))) < 0) {
                largestChild = rightIndex(index);
            }
            if (array.get(index).compareTo(array.get(largestChild)) < 0) {
                E tmp2 = array.get(largestChild);
                array.set(largestChild,array.get(index));
                array.set(index,tmp2);
            } else {
                break;
            }
            index = largestChild;
        }
    }

    public E getMax() {
        QueueObject<E> maxArray = (QueueObject) array.get(0);
        return maxArray.data;
    }

    public boolean isEmpty() {
        return array.size() == 0;
    }

    private boolean hasParent(int i) {
        return i >= 1;
    }

    private int leftIndex(int i) {
        return i * 2 + 1;
    }

    private int rightIndex(int i) {
        return i * 2 + 2;
    }

    private boolean hasLeftChild(int i) {
        return leftIndex(i) < array.size();
    }

    private boolean hasRightChild(int i) {
        return rightIndex(i) < array.size();
    }

    private E parent(int i) {
        return array.get(parentIndex(i));
    }

    private int parentIndex(int i) {
        return i / 2;
    }

    public void print() {
        for (int i = 0; i < array.size(); i++) {
            QueueObject anArray = (QueueObject) array.get(i);
            System.out.print(anArray.data.toString() + " ");
        }
        System.out.println();
    }

    public class QueueObject<E> implements Comparable<QueueObject> {
        private int priority;
        E data;

        QueueObject(int priority, E data) {
            this.priority = priority;
            this.data = data;
        }

        @Override
        public int compareTo(QueueObject queueObject) {
            if (priority != queueObject.priority) return priority < queueObject.priority ? -1 : 1;
            return 0;
        }
    }

}
