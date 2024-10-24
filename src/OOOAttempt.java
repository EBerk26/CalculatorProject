import java.util.StringTokenizer;

public class OOOAttempt {
    Exception ParenthesesImbalance = new Exception();
    Exception misalignedParentheses = new Exception();



    public OOOAttempt() {
        try {
            String output = evaluate("5 + (6 * 2) - (8 / 4) + 3");
            if(output.charAt(0)=='|'){
                System.out.println("-"+output.substring(1));
            } else {
                System.out.println(output);
            }
        } catch (Exception e) {
            if(e.equals(ParenthesesImbalance)){
                System.out.println("The numbers of open and closed parentheses differ.");
            }
            if(e.equals(misalignedParentheses)){
                System.out.println("Misaligned Parentheses");
            }
            else{
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new OOOAttempt();
    }

    String evaluate(String input) throws Exception {
        String[] temp;
        boolean hasBrackets;
        StringTokenizer st = new StringTokenizer(input, "()", true);
        String[] tokens = new String[st.countTokens()];
        int tokenAdder = 0;
        while (st.hasMoreTokens()) {
            tokens[tokenAdder] = st.nextToken();
            tokenAdder++;
        }
        hasBrackets = countCharacter(input, '(') + countCharacter(input, ')') != 0;
        if (hasBrackets) {
            if (countCharacter(input, '(') != countCharacter(input, ')')) {
                throw ParenthesesImbalance;
            }

            //First, deal with brackets by recursively applying the method on anything inside them and putting the result in the array
            int openBracket = -100;//sets a negative value so we know it hasn't been used yet
            int closeBracket = -100; //same thing
            int numberOfBracketsWeAreInside = 0;
            for (int x = 0; x < tokens.length; x++) {
                if (tokens[x].equals("(")) {
                    numberOfBracketsWeAreInside++;
                    if (openBracket == -100) {
                        openBracket = x;
                    }
                }
                if (tokens[x].equals(")")) {
                    numberOfBracketsWeAreInside--;
                    if (numberOfBracketsWeAreInside == 0 && closeBracket == -100) {
                        closeBracket = x;
                    }
                }
                if (numberOfBracketsWeAreInside < 0) {
                    throw misalignedParentheses;
                }
            }
            String insideParentheses = "";
            for (int x = openBracket + 1; x < closeBracket; x++) {
                insideParentheses = insideParentheses + tokens[x];
            }

            temp = new String[tokens.length - (closeBracket - openBracket)];

            if (openBracket >= 0) {
                System.arraycopy(tokens, 0, temp, 0, openBracket);
                temp[openBracket] = evaluate(insideParentheses);
            }
            if (tokens.length - (closeBracket + 1) > 0) {
                System.arraycopy(tokens, closeBracket + 1, temp, openBracket + 1, temp.length - (openBracket + 1));
            }
            tokens = new String[temp.length];
            System.arraycopy(temp, 0, tokens, 0, temp.length);
        }
        String stringify = "";
        for (int x = 0; x < tokens.length; x++) {
            stringify = stringify + tokens[x];
        }
        hasBrackets = countCharacter(stringify, '(') + countCharacter(stringify, ')') != 0;
        if(hasBrackets){
            return evaluate(stringify);
        } else {
            if((input.contains("*")||input.contains("/"))&&(input.contains("+")||input.contains("-"))){
                return (evaluate(ParenthesizeMultiplication.parenthesize(stringify)));
            } else{
                return LtoROperations.simpletoRPNConverter(stringify);
            }
        }

    }
    int countCharacter (String source,char characterToCount){
        int count = 0;
        for (int x = 0; x < source.length(); x++) {
            if (source.charAt(x) == characterToCount) {
                count++;
            }
        }
        return count;
    }


        /*
        1. Handle parentheses
        2. parenthesize multiplication if there's no parentheses, sending it back through the whole method
        3. if there are no parentheses, evaluate left to right.
         */
}