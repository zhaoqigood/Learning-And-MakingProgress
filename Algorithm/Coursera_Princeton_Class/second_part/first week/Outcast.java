import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
/**
 *
 * @author zhaoq
 */
public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet){
	    if(wordnet == null){
			throw new IllegalArgumentException();
        }
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns){
	    if(nouns == null){
			throw new IllegalArgumentException();
        }
        int maxSum = 0;
        String outcast_noun = nouns[0];
        for(String noun: nouns){
            int sum = 0; // initialize the value of sum
            for(String other_noun: nouns){
                sum = sum + wordnet.distance(noun,other_noun);
            }
            if(sum > maxSum){
                maxSum = sum;
                outcast_noun = noun;
            }
            
        }
        return outcast_noun;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0],args[1]);
        Outcast outcast = new Outcast(wordnet);
        for(int t=2;t<args.length;t++){
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t]+": "+outcast.outcast(nouns));
        }
    }    
}
