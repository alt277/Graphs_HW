import java.util.*;

/**
 * Граф
 */
public class Graph {

    private List<Vertex> vertexList;  //лист вершин
    private boolean[][] conectionArr;      //смежная матрица
    private int size;               //  размер графа на данный момент
   private int[] pointers;
    //конструктор
    public Graph(int vertexCount){
        this.vertexList = new ArrayList<>(vertexCount);
        this.conectionArr = new boolean[vertexCount][vertexCount];
        this.pointers= new int[vertexCount];
    }

    // добавляем вершину
    public void addVertex(String value){
        vertexList.add(new Vertex(value));   // добавляем   в лист вершин
        size++;                              // увеличиваем размер графа
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    //получить индекс вершины
    private int indexOf(String value){        // идем по листу вершин сравниваем:
        for(int i = 0; i < size; i++){        // значение i эл-та  с заданным значением
            if(vertexList.get(i).getValue().equals(value)){
                return i;
            }
        }
        return  -1;
    }

    private int indexByVertex (Vertex vertex){        // идем по листу вершин сравниваем:
        for(int i = 0; i < size; i++){        // значение i эл-та  с заданным значением
            if(vertexList.get(i).getValue().equals(vertex.getValue())){
                return i;
            }
        }
        return  -1;
    }



    //добавить ребро
    public void addEdge(String v1, String v2){
        int v1Index = indexOf(v1);           // получаем номер вершины в списке
        int v2Index = indexOf(v2);

        if((v1Index == -1) || (v2Index == -1)){
            throw new IllegalArgumentException("Invalid value for vertex");
        }
                                     //  устанавливаем связь
        conectionArr[v1Index][v2Index] = true;
        conectionArr[v2Index][v1Index] = true;
    }

    //вывод графа на консоль
    public void show(){
        for(int i = 0; i < size; i++){
            System.out.println(vertexList.get(i).getValue());
            for(int j = 0; j < size; j++){
                if(conectionArr[i][j]){
                    System.out.println(" -> " + vertexList.get(j).getValue());
                }
            }
            System.out.println();
        }
    }

                                   //снять посещения со всех вершин
    private void dropVertexState(){
        for (int i = 0; i < size; i++){
            vertexList.get(i).setVisited(false);
        }
    }

                             // положить в стек      и отметить как посещенную
    private void visitVertex(Stack<Vertex> stack, Vertex vertex){
        stack.push(vertex);
        vertex.setVisited(true);
    }
                         //  добавить в конец очереди   и отметить как посещенную
    private void visitVertex(Queue<Vertex> queue, Vertex vertex){
        queue.add(vertex);
        vertex.setVisited(true);
    }
                   // вершина- номер строки идем по столбцам в поиске смежных ( с TRUE)
                   // а можно и наоборот - по строкам
    private Vertex getNearUnvisitedVertex(Vertex vertex){
        int vertexIndex = vertexList.indexOf(vertex);
        for(int i = 0; i < size; i++){
            if(conectionArr[vertexIndex][i] && !vertexList.get(i).isVisited()){
                return vertexList.get(i);
            }
        }
        return null;
    }

                    // найти короткий путь ( то же что и BFS но с путем до заданной вершины)
    public Stack<String> myShortPath(String v1, String v2){
        int v1Index = indexOf(v1);
        int v2Index = indexOf(v2);

        if(v1Index == -1){
            throw new IllegalArgumentException("Invalid vertex1: " + v1);
        }

        if(v2Index == -1){
            throw new IllegalArgumentException("Invalid vertex2: " + v2);
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(v1Index);
        visitVertex(queue, vertex);
        while(!queue.isEmpty()){                     //q.peek выдает первый эл-нт очереди не иивлекая его
            vertex = getNearUnvisitedVertex(queue.peek());  //найти у первого смежные,непосещенные вершины
            if(vertex != null){
                visitVertex(queue, vertex);       //  добавить в конец очереди  и отметить как посещенную
                    int current=indexByVertex(vertex) ;
                    int previous=indexByVertex(queue.peek());

                    pointers[current]=previous;            // поставить родителя как предыдущую

                if(vertex.getValue().equals(v2)){
                                                            // если это искомая вернуть все предыдущие
                    return myPath(vertex.getValue());
                }
            }else{
                queue.remove();
            }
        }
        dropVertexState();     // снять посещения со всех вершин
        return null;
    }

    public Stack<String> myPath(String vert ) {

        Stack<String> stack = new Stack<>();

        int current=indexOf(vert);

        while(current != 0){

            stack.push(vertexList.get(current).getValue());           //    вершину в стек
            current=pointers[current];         //    перейти к предыдущей
        }
        stack.push(vertexList.get(current).getValue());
        return stack;
    }

     public void showPath(Stack<String> path) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        int lenght = 0;

        while ( !path.isEmpty() ) {
            if (!isFirst) {
                sb.append(" -> ");
            }
            isFirst = false;
            sb.append(path.pop());
            lenght++;
        }
        System.out.println(sb);
        System.out.println("path length: " + lenght);
    }
}
