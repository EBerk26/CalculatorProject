import java.util.StringTokenizer;

public class StringTokenizerExperimenting {
    public static void main(String[] args) {
        new StringTokenizerExperimenting();
    }
    public StringTokenizerExperimenting() {
        StringTokenizer st = new StringTokenizer("11-(12+13)/5*6","()+/*-",true);
        while(st.hasMoreTokens()) {
            System.out.println(st.nextToken());
            // 11 12 13 plus 5 divide 4 times minus
        }
    }
}
