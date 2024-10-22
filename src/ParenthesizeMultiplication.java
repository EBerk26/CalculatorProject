import java.util.StringTokenizer;

public class ParenthesizeMultiplication {
    static Exception containsParentheses = new Exception();
    public static void main(String[] args) {
        new ParenthesizeMultiplication();
    }
    public ParenthesizeMultiplication() {
        try {
            System.out.println(parenthesize("2*3+3"));
            System.out.println(parenthesize("1+2*3"));
            System.out.println(parenthesize("2*3+3*4"));
        } catch (Exception e) {
            if(e.equals(containsParentheses)){
                System.out.println("Simple operation should not contain parentheses");
            } else {
                e.printStackTrace();
            }
        }
    }
    static String parenthesize(String input) throws Exception{
        if(input.contains("(")||input.contains(")")){
            throw containsParentheses;
        }
        StringTokenizer st = new StringTokenizer(input,"*/+-",true);
        String[] tokens = new String[st.countTokens()];
        int x=0;
        while(st.hasMoreTokens()){
            tokens[x]=st.nextToken();
            x++;
        }
        x=0;
        while(x<tokens.length){
            if(x< tokens.length-1){
                if((tokens[x+1].equals("*")||tokens[x+1].equals("/"))){
                    tokens[x] = "("+tokens[x];
                }
            }
            if(x>0) {
                if ((tokens[x - 1].equals("*") || tokens[x - 1].equals("/"))) {
                    tokens[x] = tokens[x] + ")";
                }
            }
            x++;
        }
        String stringify = "";
        for (int y = 0; y < tokens.length; y++) {
            stringify = stringify + tokens[y];
        }
        return stringify;
    }
}
