
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

public class Grath {

    public static class dfs {
        private int[][] connectMatrix;
        private boolean[] isVisited;
        private int startPoint = 0;

        public dfs(int[][] weightMatrix) {
            connectMatrix = weightMatrix;
            isVisited = new boolean[weightMatrix.length];
            run(startPoint);
        }

        private void run(int startPoint) {
            System.out.print((startPoint + 1) + " ");
            isVisited[startPoint] = true;
            for (int nextPoint = 0; nextPoint < connectMatrix.length; nextPoint++) {
                if ((connectMatrix[startPoint][nextPoint] != 0) && (!isVisited[nextPoint]))
                    run(nextPoint);
            }
        }
    }

    public static class bfs {
        private int startPoint = 0;
        private int[][] connectMatrix;
        private boolean[] isVisited;
        int node;
        Queue<Integer> queue = new LinkedList<>();

        public bfs(int[][] weightMatrix) {
            connectMatrix = weightMatrix;
            isVisited = new boolean[weightMatrix.length];
            run(startPoint);
        }

        private void run(int startPoint) {
            queue.add(startPoint);
            while (!queue.isEmpty()) {
                node = queue.poll();
                if (isVisited[node]) continue;
                else isVisited[node] = true;
                System.out.print(node + 1 + " ");
                for (int i = 0; i < connectMatrix.length; i++) {
                    if ((connectMatrix[node][i] != 0) && (!isVisited[i]))
                        queue.add(i);
                }
            }
        }
    }

    public static class dijk {
        private static int INF = Integer.MAX_VALUE / 2;
        private boolean[] isVisited;
        private int startPoint = 3;
        private int[] mass;
        private int minimal;

        public dijk(int[][] weightMatrix) {
            mass = new int[weightMatrix.length];
            isVisited = new boolean[weightMatrix.length];
            for (int i = 0; i < weightMatrix.length; i++) mass[i] = INF;
            mass[startPoint] = 0;
            int index = 0;
            for (int i = 0; i < weightMatrix.length-1; i++) {
                minimal = INF;
                for (int j = 0; j < weightMatrix.length; j++)
                    if (!isVisited[j] && mass[j] <= minimal) {
                        minimal = mass[j];
                        index = j;
                    }
                int u = index;
                isVisited[u] = true;
                for (int k = 0; k < weightMatrix.length; k++) {
                    if (!isVisited[k] && (weightMatrix[u][k] > 0) && (mass[u] != INF) && mass[u] + weightMatrix[u][k] < mass[k])
                        mass[k] = mass[u] + weightMatrix[u][k];
                }
            }
            for (int i = 0; i < 7; i++) {
                System.out.print(mass[i]+ " ");
            }
        }
    }
    public static class floyd_warshall {
        public floyd_warshall(int[][] weightMatrix) {
            for (int k = 0; k < 7; k++) {
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (weightMatrix[i][k] != 0 && weightMatrix[k][j] != 0 && i != j)
                            if (weightMatrix[i][k] + weightMatrix[k][j] < weightMatrix[i][j] || weightMatrix[i][j] == 0)
                                weightMatrix[i][j] = weightMatrix[i][k] + weightMatrix[k][j];
                    }
                }
            }
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    System.out.print(weightMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
    public static class prima {
        private static int INF = Integer.MAX_VALUE / 2;
        private int[][] Weightmatrix = {
                {0,2,1,3,INF,6,INF},    //1
                {2,0,INF,1,3,INF,INF},    //2
                {1,INF,0,2,INF,3,INF},    //3
                {3,1,2,0,INF,INF,1},    //4
                {INF,3,INF,INF,0,INF,4},    //5
                {6,INF,3,INF,INF,0,7},    //6
                {INF,INF,INF,INF,4,7,0}     //7
        };
        public prima() {
            boolean[] Visit = new boolean [Weightmatrix.length];
            int[] D = new int [Weightmatrix.length];
            fill(D, INF);
            D[0] = 0;
            while(true) {
                int v = -1;
                for (int n = 0; n < Weightmatrix.length; n++)
                    if (!Visit[n] && D[n] < INF && (v == -1 || D[v] > D[n])) v = n;
                if (v == -1) break;
                Visit[v] = true;
                for (int nv = 0; nv < Weightmatrix.length; nv++)
                    if (!Visit[nv] && Weightmatrix[v][nv] < INF) D[nv] = min(D[nv], Weightmatrix[v][nv]);
                }
            int ret = 0;
            for (int v = 0; v < Weightmatrix.length; v++)
            {
                ret += D[v];
                System.out.print(D[v] + " ");
            }
            System.out.println("\n" +"MST : " + ret);
        }
    }
    public static class kraskal {
        private int vertexNum;
        private int uNum;
        private int[][] tree;
        private int[] sets;
        private final int MAX = 10;
        private ArrayList<Edge> edges;
        private double cost;

        private class Edge implements Comparable<Edge> {
            int u;
            int v;
            double weight;

            // Конструктор
            Edge(int u, int v, int w) {
                this.u = u;
                this.v = v;
                this.weight = w;
            }

            // Компаратор
            @Override
            public int compareTo(Edge edge) {
                if (weight != edge.weight) return weight < edge.weight ? -1 : 1;
                return 0;
            }
        }

        kraskal() {
            int uNumA = 6;
            int vNumA = 9;
            int matrix[][] = {
                    {1, 2, 5},
                    {2, 3, 14},
                    {2, 4, 19},
                    {3, 4, 15},
                    {3, 5, 10},
                    {4, 5, 11},
                    {5, 6, 2},
                    {6, 7, 6},
                    {7, 4, 9},
            };
            tree = new int[MAX][3];
            sets = new int[MAX];
            uNum = uNumA;
            vertexNum = vNumA;
            edges = new ArrayList<>();
            edges.add(new Edge(0, 0, 0));

            for (int i = 0; i < vertexNum; i++) {
                edges.add(new Edge(matrix[i][0], matrix[i][1], matrix[i][2]));
            }

            for (int i = 1; i <= uNum; i++) {
                sets[i] = i;
            }
            Collections.sort(edges);
        }

        private int Find(int vertex) {
            return sets[vertex];
        }

        private void Join(int v1, int v2) {
            if (v1 < v2) {
                for (int i = 0; i < sets.length; i++) {
                    if (sets[i] == v2)
                        sets[i] = v1;
                }
            } else {
                for (int i = 0; i < sets.length; i++) {
                    if (sets[i] == v1)
                        sets[i] = v2;
                }
            }
        }

        private void BuildingTree() {
            int k = uNum;
            cost = 0;
            int i, t = 1;
            for (i = 1; i <= k; i++) {
                if (Find(edges.get(i).u) != Find(edges.get(i).v)) {
                    tree[t][1] = edges.get(i).u;
                    tree[t][2] = edges.get(i).v;
                    cost += edges.get(i).weight;
                    Join(edges.get(t).u, edges.get(t).v);
                    t++;
                }
            }
        }

        public void Run() {
            BuildingTree();
            System.out.println("Вес: " + cost);
            System.out.println("Ребра минимального остовного дерева:");
            for (int i = 1; i < uNum; i++) {
                System.out.println(tree[i][1] + " - " + tree[i][2]);
            }
            System.out.println();
        }
    }
}
