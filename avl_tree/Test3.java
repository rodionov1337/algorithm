
public class Test3 {
    public static void main(String[] args){
        AVL<Integer, Integer> tree = new AVL<>();
        tree.add(10, 11);
        tree.add(7, 22);
        tree.add(17, 33);
        tree.add(4, 44);
        tree.add(9, 55);
        tree.add(15, 66);
        tree.add(20, 77);
        tree.add(1, 88);
        tree.add(6, 99);

        System.out.println();
        tree.print();
        System.out.println("-----------------");

        System.out.println(tree.get(60));
        System.out.println("-----------------");

        System.out.println(tree.get(17));
        System.out.println("-----------------");

        tree.delete(7);
        tree.print();

        System.out.println(tree.containsKey(9));
        System.out.println("-----------------");

        System.out.println("Пусто?: " + tree.Empty());
        System.out.println("------------------");

        tree.clear();
        System.out.println("Tree now \n -----------------");
        tree.print();


        System.out.println("Пусто?: " + tree.Empty());
        System.out.println("------------------");
    }
}
