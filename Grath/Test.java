package lab1;

public class Test {


    public static void main(String[] args) {

        int INF = Integer.MAX_VALUE / 2;
        int[][] Adjmatrix = {
                {0,1,1,0,0,0,0},    //1
                {1,0,0,1,1,0,0},    //2
                {1,0,0,1,0,1,1},    //3
                {0,1,1,0,1,0,0},    //4
                {0,1,0,1,0,0,0},    //5
                {0,0,1,0,0,0,0},    //6
                {0,0,1,0,0,0,0}     //7
        };
        int[][] Weightmatrix = {
                {0,2,1,4,0,6,0},    //1
                {2,0,0,1,3,0,0},    //2
                {1,0,0,2,0,4,0},    //3
                {4,1,2,0,0,0,1},    //4
                {0,3,0,0,0,0,4},    //5
                {6,0,4,0,0,0,7},    //6
                {0,0,0,1,4,7,0}     //7
        };
        int[][] Weightmatrix2 = {
                {0,1,2,4,INF,INF,INF},      //1
                {1,0,INF,2,5,INF,INF},      //2
                {2,INF,0,7,INF,4,1},        //3
                {4,2,7,0,1,INF,1},          //4
                {INF,5,INF,1,0,INF,INF},    //5
                {INF,INF,4,INF,INF,0,INF},  //6
                {INF,INF,1,1,INF,INF,0}     //7
        };

        System.out.println("Поиск в глубину : ");
        Grath.dfs dfs = new Grath.dfs(Adjmatrix);
        System.out.println();
        System.out.println("Поиск в ширину : ");
        Grath.bfs bfs = new Grath.bfs(Adjmatrix);
        System.out.println();
        System.out.println("Алгоритм Дейкстры : ");
        Grath.dijk dijk = new Grath.dijk((Weightmatrix));
        System.out.println();
        System.out.println("Алгоритм Флойда - Уоршелла : ");
        Grath.floyd_warshall floyd_warshall = new Grath.floyd_warshall((Weightmatrix2));
        System.out.println();
        System.out.println("Алгоритм Прима : ");
        Grath.prima prima = new Grath.prima();
        System.out.println();
        System.out.println("Алгоритм Краскала : ");
        Grath.kraskal kruskalAlg = new Grath.kraskal();
        kruskalAlg.Run();
    }

}
