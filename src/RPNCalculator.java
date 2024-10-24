import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class RPNCalculator implements ActionListener{

//Instructions: https://rb.gy/3h4ruo

    JButton[] numberButtons;
    JFrame frame;
    JLabel outputWindow;
    JButton divide;
    JButton times;
    JButton minus;
    JButton plus;
    JButton decimal;
    JButton space;
    JButton equals;
    JButton clear;
    JButton power;
    Font oneFifthFont;
    Font oneTenthFont;
    String inputString ="";

    public static void main(String[] args) {
        //CODE TO GET A LIST OF THE FONTS
        /*String fonts[]
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }*/
       new RPNCalculator();
    }

    public RPNCalculator() {
        frame = new JFrame("RPN Calculator");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel backPanel = new JPanel(new GridLayout(6,1));
        frame.add(backPanel);

        outputWindow = new JLabel();
        backPanel.add(outputWindow);
        outputWindow.setHorizontalAlignment(SwingConstants.RIGHT);
        outputWindow.setFont(new Font ("Plain",Font.PLAIN,frame.getHeight()/5));

        JPanel[] miniMiddlePanels = new JPanel[3];
        for(int x = 0;x<=2;x++){
            miniMiddlePanels[x] = new JPanel(new GridLayout(1,4));
            backPanel.add(miniMiddlePanels[x]);
        }
        JPanel rowWith0InIt = new JPanel(new GridLayout(1,4));
        JPanel bottomMiniPanel = new JPanel(new BorderLayout());
        backPanel.add(rowWith0InIt);
        backPanel.add(bottomMiniPanel);
        numberButtons = new JButton[10];
        for (int x =0;x<=9;x++){
            numberButtons[x] = new JButton(String.valueOf(x));
            numberButtons[x].addActionListener(this);
        }

        for(int x =0;x<=2;x++){
            for(int y=7-3*x;y<=9-3*x;y++){
                miniMiddlePanels[x].add(numberButtons[y]);
            }
        }
        power = new JButton("^");
        power.addActionListener(this);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        divide = new JButton("/");
        times = new JButton("*");
        times.addActionListener(this);
        minus = new JButton("-");
        minus.addActionListener(this);
        plus = new JButton("+");
        plus.addActionListener(this);
        decimal = new JButton(".");
        space = new JButton("");
        space.addActionListener(this);
        decimal.addActionListener(this);
        equals = new JButton("=");
        equals.addActionListener(this);
        changeColor(equals);
        changeColor(space);
        changeColor(decimal);
        changeColor(plus);
        changeColor(minus);
        changeColor(divide);
        changeColor(times);
        changeColor(clear);
        changeColor(power);
        for(int x=0;x<=9;x++){
            changeColor(numberButtons[x]);
        }

        miniMiddlePanels[0].add(divide);
        divide.addActionListener(this);
        miniMiddlePanels[1].add(times);
        miniMiddlePanels[2].add(minus);
        rowWith0InIt.add(space);
        rowWith0InIt.add(numberButtons[0]);
        rowWith0InIt.add(decimal);
        rowWith0InIt.add(plus);

        bottomMiniPanel.add(equals);
        bottomMiniPanel.add(clear,BorderLayout.WEST);
        ChangeFontSizes();
        clear.setPreferredSize(new Dimension(frame.getWidth()*3/4,frame.getHeight()/5));




        frame.setVisible(true);
        while(true){
            if(System.currentTimeMillis()%256==0){
                ChangeFontSizes();
                clear.setPreferredSize(new Dimension(frame.getWidth()*3/4,frame.getHeight()/5));
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==clear){
            inputString="";
        }
        for(int x=0;x<=9;x++) {
            if (e.getSource()==numberButtons[x]){
                type(x);
            }
        }
        if(e.getSource()==plus){
            type("+");
        }
        if(e.getSource()==minus){
            type("-");
        }
        if(e.getSource()==times){
            type("*");
        }
        if(e.getSource()==divide){
            type("/");
        }
        if(e.getSource()==decimal){
            type(".");
        }
        if(e.getSource()==space){
            type(" ");
        }
        if(e.getSource()==equals){
            try {
                inputString = calculateRPN(inputString);
            } catch(Exception exception){
                outputWindow.setText("Error");
            }
        }

        outputWindow.setText(inputString);
    }
    void ChangeFontSizes(){
        oneFifthFont = new Font("You can put whatever here and it doesn't matter unless it's the name of a font.",Font.PLAIN,frame.getHeight()/5);
        oneTenthFont = new Font("Cool font name",Font.PLAIN,frame.getHeight()/10);
        for(int x =0;x<=9;x++){
            numberButtons[x].setFont(oneTenthFont);
        }
        outputWindow.setFont(oneTenthFont);
        divide.setFont(oneTenthFont);
        times.setFont(oneTenthFont);
        minus.setFont(oneTenthFont);
        plus.setFont(oneTenthFont);
        decimal.setFont(oneTenthFont);
        equals.setFont(oneTenthFont);
        clear.setFont(oneTenthFont);
        power.setFont(oneTenthFont);
    }
    void changeColor(JButton input){
        input.setOpaque(true);
        input.setForeground(Color.BLUE);
        input.setBackground(Color.ORANGE);
    }
    void type(String x){
        inputString=inputString+x;
    }
    void type(int x){
        inputString=inputString+String.valueOf(x);
    }
    static String calculateRPN(String equation){
        //evaluates the string using RPN
        StringTokenizer st = new StringTokenizer(equation,"+/*- ",true);
        String[] tokens = new String[st.countTokens()];
        int x =0;
        while(st.hasMoreTokens()){
            tokens[x]=st.nextToken();
            x++;
        }
        x=0;
        //copy over tokens excluding spaces
        String[] temp = new String[tokens.length];
        int tempLength =0;
        for(int y=0;y<tokens.length;y++){
            if(!tokens[y].equals(" ")){
                temp[tempLength] = tokens[y];
                tempLength++;
            }
        }
        tokens = new String[tempLength];
        System.arraycopy(temp,0,tokens,0,tempLength);

        while(tokens.length>1){
            if(tokens[x].equals("+")){
                temp=new String[tokens.length-2];
                for(int y=0;y<=x-3;y++){
                    temp[y]=tokens[y];
                }
                temp[x-2]=String.valueOf(parseDouble(tokens[x-1])+parseDouble(tokens[x-2]));
                if(temp[x-2].charAt(0)=='-'){
                    temp[x-2]="|"+temp[x-2].substring(1);
                }
                if(x<tokens.length-1) {
                    for (int y = x - 1; y < tokens.length-2; y++) {
                        temp[y] = tokens[y + 2];
                    }
                }
                x=0;
                tokens = new String[temp.length];
                System.arraycopy(temp,0,tokens,0,temp.length);

            } else if (tokens[x].equals("*")){
                temp=new String[tokens.length-2];
                for(int y=0;y<=x-3;y++){
                    temp[y]=tokens[y];
                }
                temp[x-2]=String.valueOf(parseDouble(tokens[x-1])*parseDouble(tokens[x-2]));
                if(temp[x-2].charAt(0)=='-'){
                    temp[x-2]="|"+temp[x-2].substring(1);
                }
                if(x<tokens.length-1) {
                    for (int y = x - 1; y < tokens.length-2; y++) {
                        temp[y] = tokens[y + 2];
                    }
                }
                x=0;
                tokens = new String[temp.length];
                System.arraycopy(temp,0,tokens,0,temp.length);
            } else if(tokens[x].equals("/")){
                temp=new String[tokens.length-2];
                for(int y=0;y<=x-3;y++){
                    temp[y]=tokens[y];
                }
                temp[x-2]=String.valueOf(parseDouble(tokens[x-2])/parseDouble(tokens[x-1]));
                if(temp[x-2].charAt(0)=='-'){
                    temp[x-2]="|"+temp[x-2].substring(1);
                }
                if(x<tokens.length-1) {
                    for (int y = x - 1; y < tokens.length-2; y++) {
                        temp[y] = tokens[y + 2];
                    }
                }
                x=0;
                tokens = new String[temp.length];
                System.arraycopy(temp,0,tokens,0,temp.length);
            } else if (tokens[x].equals("-")){
                temp=new String[tokens.length-2];
                for(int y=0;y<=x-3;y++){
                    temp[y]=tokens[y];
                }
                temp[x-2]=String.valueOf(parseDouble(tokens[x-2])-parseDouble(tokens[x-1]));
                if(temp[x-2].charAt(0)=='-'){
                    temp[x-2]="|"+temp[x-2].substring(1);
                }
                if(x<tokens.length-1) {
                    for (int y = x - 1; y < tokens.length-2; y++) {
                        temp[y] = tokens[y + 2];
                    }
                }
                x=0;
                tokens = new String[temp.length];
                System.arraycopy(temp,0,tokens,0,temp.length);
            } else {
                //x will be the place we are in the stack
                x++;
            }
        }
        return tokens[0];
    }
    static double parseDouble(String input){
        if(input.charAt(0)=='|'){
            return -1*Double.parseDouble(input.substring(1));
        } else{
            return Double.parseDouble(input);
        }
    }
}