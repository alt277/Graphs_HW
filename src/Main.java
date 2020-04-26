import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Graph graph=new Graph(20);
        graph.addVertex("Moscow"); graph.addVertex("B");
        graph.addVertex("M"); graph.addVertex("W"); graph.addVertex("Piter");

        graph.addVertex("S"); graph.addVertex("L");
        graph.addVertex("G"); graph.addVertex("T") ;graph.addVertex("X");

        graph.addEdge("Moscow","B");
        graph.addEdge("Moscow","S");
        graph.addEdge("Moscow","G");
        graph.addEdge("B","M"); graph.addEdge("M","W"); graph.addEdge("W","Piter");
        graph.addEdge("S","L"); graph.addEdge("L","Piter");
        graph.addEdge("G","T"); graph.addEdge("T","X"); graph.addEdge("X","Piter");
//      graph.addEdge("B","Piter");

        Stack<String> path= graph.myShortPath("Moscow","Piter");
        graph.showPath(path);

//     System.out.println(path);
    }
}
