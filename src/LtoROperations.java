import java.util.StringTokenizer;

public class LtoROperations {
    static Exception operationsMix = new Exception();
    public static void main(String[] args) {
        new LtoROperations();
    }
    public LtoROperations() {
        try {
            System.out.println(simpletoRPNConverter("3*4"));
        } catch (Exception e) {
            if(e.equals(operationsMix))
            e.printStackTrace();
            System.out.println("Operations Mix");

        }
    }
    static String simpletoRPNConverter(String input) throws Exception{
        if((input.contains("*")||input.contains("/"))&&(input.contains("+")||input.contains("-"))){
            throw operationsMix;
        }
        StringTokenizer st = new StringTokenizer(input,"+-*/",true);
        String[] tokens = new String[st.countTokens()];
        for(int x =0;x<tokens.length;x++){
            tokens[x]=st.nextToken();
        }
        String[] numberTokens = new String[0];
        String[] operationTokens = new String[0];

        for(int x =0;x<tokens.length;x++){
            if(tokens[x].equals("+")||tokens[x].equals("-")||tokens[x].equals("*")||tokens[x].equals("/")){
                operationTokens = addItem(tokens[x],operationTokens);
            } else {
                numberTokens = addItem(tokens[x],numberTokens);
            }
        }
        if(numberTokens.length!=operationTokens.length+1){
            return "error";
        } else {
            String output = numberTokens[0];
            for(int x =0;x<operationTokens.length;x++){
                output=output+" "+numberTokens[x+1];
                output = output+operationTokens[x];
            }
            return RPNCalculator.calculateRPN(output);
        }

    }
    static String[] addItem(String itemToAdd, String[] location){
        String[] result = new String[location.length+1];
        System.arraycopy(location,0,result,0,location.length);
        result[location.length] = itemToAdd;
        return result;
    }
}
