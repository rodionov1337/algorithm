
public class Test2 {
    public static void main(String[] args) {
        Priority_Queue<String> classQueue1 = new Priority_Queue<>(); //
        classQueue1.add_elem(3, "9 - 8");
        classQueue1.add_elem(2, "7 - 3");
        classQueue1.add_elem(7, "22 - 11");
        classQueue1.add_elem(5, "3 - 5");
        System.out.println("Очередь по приоритетам после добавления ");
        classQueue1.print();
        classQueue1.remove_elem();
        System.out.println("Очередь по приоритетам после удаления ");
        System.out.println("Пуста ли очередь? \n" + classQueue1.isEmpty());
        System.out.println("Максимальный элемент в очереди: \n" +classQueue1.getMax());
    }
}
