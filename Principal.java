/*
 * Universidade do Sul de Santa Catarina - UFSC
 * Programa do Curso de Sistemas de Informa��o
 * Disciplina: Programa��o Linear e Grafos
 * Prof Osmar de Oliveira Braz Junior
 *
 * Baseado nas aulas e c�digo do Github do Professor Osmar 
 *
 * P�gina 459 Thomas H. Cormen 3a Ed
 *
 * �rvore Geradora M�nima(MST) com o Algoritmo de Kruskal
 */

/**
 * @author John de Lara
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Principal {        
  
    //Vetor dos pais de um v�rtice
    static int[] pi;         
    //d[x] armazena o instante de descoberta de x.
    //Cada n� x possui um ?posto? rank[x] que �e um limitante superior para a altura de x
    //Equivale a d[x]
    static int[] rank;

    /**
     * Encontra a raiz de um conjunto.
     * 
     * P�gina 415 Thomas H. Cormen 3a Ed
     * 
     * @param x Elemento a ser procurado.
     * @return a raiz do conjunto
     */
    public static int findSet(int x) {          
        int p = pi[x];
        if (x != p) {            
            p = findSet(p);
        }
        return p;
    }    

    /**
     * Link realiza a liga��o de dois elementos a serem unidos.
     * 
     * P�gina 415 Thomas H. Cormen 3a Ed
     * 
     * @param x Primeiro elemento
     * @param y Segundo elemento
     */
    public static void link(int x, int y){        
        if (rank[x] > rank[y]) {            
            pi[y] = x;
        } else  {            
            pi[x] = y;
            if (rank[y] == rank[x]) {                
                rank[y] = rank[y] + 1;
            }
        }
    }
    
    /**
     * Realiza a uni�o de dois elementos.
     * 
     * P�gina 415 Thomas H. Cormen 3a Ed
     * 
     * @param x Primeiro elemento
     * @param y Segundo elemento
     */
    public static void union(int x, int y) {        
        link(findSet(x),findSet(y));        
    }
              
    /**
     * Cria o conjunto unit�rio de x.
     * 
     * P�gina 415 Thomas H. Cormen 3a Ed
     * 
     * @param x conjunto a ser criado
     */
    public static void makeSet(int x){
        pi[x] = x;
        rank[x] = 0;
    }
 
    /**
     * Troca um n�mero que representa a posi��o pela v�rtice do grafo.
     *
     * @param i Posi��o da letra
     * @return Uma String com a letra da posi��o i
     */
    public static String trocar(int i) {
        String letras = "abcde";        
        if ((i >=0) && (i<=letras.length())) {
            return letras.charAt(i) + "";
        } else {
            return "-";
        }
    }

    /**
     * Troca a letra pela posi��o na matriz de adjac�cia
     *
     * @param v Letra a ser troca pela posi��o
     * @return Um inteiro com a posi��o da letra no grafo
     */
    public static int destrocar(char v) {
        String letras = "abcde";
        int pos = -1;
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == v) {
                pos = i;
            }
        }
        return pos;
    }

     /**
     * Exibe a lista de arestas do menor custo
     *
     * @param Lista das arestas a ser exibido o caminho e o custo
     */
    public static void mostrarCaminho(int[][] A, int n) {
        //Guarda o custo do caminho
        int custo = 0;
        //Percorre os as arestas de A
        //O lado come�a em 0 e desconta um elemento pois ele se repete
        for (int v = 0; v < n-1; v++) {           
           System.out.println(trocar(A[v][0]) + " -> " + trocar(A[v][1]) + " custo: " + A[v][2]);                              
           custo = custo + A[v][2];        }
        System.out.println("Custo Total:" + custo);
    }

    /**
     * Gera um vetor de arestas e pesos.
     * @param G Matriz de adjac�ncia do grafo
     * @return Um vetor de arestas e pesos.
     */   
    public static List getMatrizVertices(int[][] G){
        int n = G.length;
        List vertices = new LinkedList();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //Somente para o tri�ngulo superior
                if ((i<j)&&(G[i][j]!=0)){
                    //Cria um vetor de 3 elementos para conter                     
                    //[0]=u(origem), [1]=v(destino), [2]=w(peso)
                    vertices.add(new int[]{i,j,G[i][j]});
                }            
            }            
        }
        return vertices;
    }    
    
    /**
     * Executa o algoritmo de Kruskal para �rvore geradora M�nima.
     * 
     * Complexidade do algoritmo � O(E log E) = O(E log V)
     * 
     * @param G Matriz de indic�ncia da �rvore      
     * @return Vetor com a lista das arestas de menor custo
     */ 
    public static int[][] algoritmoKruskal(int[][] G) {
        //Vetor das Arestas de retorno
        int[][] A;
         
        //Converte a matriz em uma lista de v�rtices
        List arestas = getMatrizVertices(G); 
        
        //Quantidade de arestas da lista
        int n = arestas.size();
                                     
        //Inicializa os vetores
        A = new int[n][]; //Vetor de retorno
        pi = new int[n];
        rank = new int[n];
        //Cria a �rvore para cada v�rtice
        for (int v = 0; v < n; v++) {
            makeSet(v);                
        }
        //Ordene as arestas em ordem n�o-decrescente de peso
        Collections.sort(arestas, (int[] e1, int[] e2) -> {
            if (e1[2] < e2[2]) return -1;
            if (e1[2] > e2[2]) return 1;
            return 0;
        });                                                 // O(E lg E)
        
        int linha = 0;
        //Para cada aresta na ordem anterior
        for(int i=0; i<arestas.size();i++){            
            int[] vertice = (int[])arestas.get(i);
            int u = findSet(vertice[0]);
            int v = findSet(vertice[1]);
            if (u != v) { //Est�o em �rvores diferentes                
                A[linha] = vertice;
                linha = linha + 1;                
                union(u,v);//Unir as duas �rvores
            }
        }
        return A;        
     }    

    public static void main(String args[]) {
        //Matriz de incid�ncia para um grafo direcionado
        // Teste JOHN___________________________________________________________
        int G[][] =
               //a   b   c   d   e  
               {{0, 55, 56, 80, 75}, //a
                {0, 0,  50, 60, 78}, //b
                {0, 0,  0,  70, 58}, //c
                {0, 0,  0,  0,  50}, //d
                {0, 0,  0,  0,  0}}; //e
                

        //Quantidade de v�rtices
        int n = G.length;
        
        System.out.println(">>> �rvore Geradora M�nima - Algoritmo de Kruskal <<<");

        //Executa o algoritmo
        int[][] g = algoritmoKruskal(G);

        //Mostra o menor custo
        mostrarCaminho(g, n);
    }
}