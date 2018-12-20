import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
/**
 *
 * @author zhaoq
 */
public class WordNet {
    private ST<String, Integer> st = new ST<String, Integer>();
    private Digraph graph;
    private String[] array;
    private SAP Sap;
    public WordNet(String synsets, String hypernyms ){
		if(synsets == null || hypernyms == null){
			throw new IllegalArgumentException();
        }
        In in1 = new In(synsets); // get the file name
        In in2 = new In(hypernyms);
        String[] contents1 = in1.readAllLines(); // get the contents of the in1
        String[] contents2 = in2.readAllLines();
        int V = contents1.length; // get the number of vertices
        array = new String[contents1.length];  
        for(int i = 0; i < contents1.length; i++){
            String[] contents3 = contents1[i].split(","); // get the string of each field
            int value = Integer.parseInt(contents3[0]);
            String[] nouns = contents3[1].split(" ");
            for(String noun: nouns){
                st.put(noun,value);
            }
            array[value] = contents3[1]; // get the pair of id and noun
        }
        graph = new Digraph(V);
        for(String line : contents2){
            String[] numbers = line.split(",");// get the numbers of each line;
            for(int j = 1; j < numbers.length;j++){
                graph.addEdge(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[j]));// add the edge v-w. first number is v, the remainded numbers are all w. 
            }
        }
		Sap = new SAP(graph);
       
    }

	public Iterable<String> nouns(){
        Bag<String> things = new Bag<String>();
        for(String line : array){
            String[] anonymous = line.split(" ");
            for(String noun : anonymous){
                things.add(noun);
            }
        }
        return things;
    }
    public boolean isNoun(String word){
		if(word == null){
			throw new IllegalArgumentException();
        }
        return st.contains(word);          
    }
    public int distance(String nounA, String nounB){
		if(nounA == null || nounB == null|| (!this.isNoun(nounA))|| (!this.isNoun(nounB))){
			throw new IllegalArgumentException();
        }
        int vertexA = st.get(nounA);
        int vertexB = st.get(nounB);
        if(vertexA == vertexB){
            return 0;
        }
       return Sap.length(vertexA,vertexB);
    }
    public String sap(String nounA, String nounB){
		if(nounA == null || nounB == null|| (!this.isNoun(nounA))|| (!this.isNoun(nounB))){
			throw new IllegalArgumentException();
        }
        int vertexA = st.get(nounA);
        int vertexB = st.get(nounB);
        if(vertexA == vertexB){
            return array[vertexA];
        }
        int ancestor = Sap.ancestor(vertexA,vertexB);
		if(ancestor == -1){
			return null;
		}
		else{
            return array[ancestor];
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0],args[1]);
        // TODO code application logic here
    
    }
}
