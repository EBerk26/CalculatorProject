import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;

//Instructions: https://rb.gy/3h4ruo

public class Calculator implements ActionListener {
    JButton[] numberButtons;
    JFrame frame;
    JLabel outputWindow;
    JButton divide;
    JButton times;
    JButton minus;
    JButton plus;
    JButton decimal;
    JButton equals;
    JButton clear;
    Font oneFifthFont;
    Font oneTenthFont;
    double[] input = new double[3];
    double output;
    int pastDecimal=-1;
    int currentInput = 1;
    String operation ="";
    String operationAtFront ="";

    public static void main(String[] args) {
        //CODE TO GET A LIST OF THE FONTS
        /*String fonts[]
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }*/
        new Calculator();
    }

    public Calculator () {
        frame = new JFrame("Calculator");
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
        JPanel rowWith0InIt = new JPanel(new GridLayout(1,2));
        JPanel bottomMiniPanel = new JPanel(new BorderLayout());
        JPanel rowWith0InItRightHalf = new JPanel (new GridLayout(1,2));
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
        decimal.addActionListener(this);
        equals = new JButton("=");
        equals.addActionListener(this);

        miniMiddlePanels[0].add(divide);
        divide.addActionListener(this);
        miniMiddlePanels[1].add(times);
        miniMiddlePanels[2].add(minus);
        rowWith0InItRightHalf.add(decimal);
        rowWith0InItRightHalf.add(plus);

        rowWith0InIt.add(numberButtons[0]);
        rowWith0InIt.add(rowWith0InItRightHalf);
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
        if(operation.equals("plus")){
            operationAtFront = "+";
        }
        if(operation.equals("minus")){
            operationAtFront = "-";
        }
        if(operation.equals("times")){
            operationAtFront = "*";
        }
        if(operation.equals("divide")){
            operationAtFront = "/";
        }
        if(e.getSource()==clear){
            input[1]=0;
            input[2]=0;
            output=0;
            pastDecimal = -1;
            outputWindow.setText("");
            currentInput = 1;
            operation = "";
            operationAtFront="";
        }
        for(int x =0;x<=9;x++){
            if(e.getSource()==numberButtons[x]){
                if(pastDecimal==-1) {
                    input[currentInput] = input[currentInput] * 10 + x;
                    outputWindow.setText(operationAtFront+String.valueOf((int)input[currentInput]));
                }else{
                    input[currentInput] = input[currentInput]+Math.pow(0.1,(pastDecimal))*x;
                    pastDecimal++;
                    input[currentInput]=Math.round(input[currentInput]*Math.pow(10,pastDecimal))/Math.pow(10,pastDecimal); //This line avoids an issue where Java doesn't have enough space in a double to fit the number - eg typing 0.3 would give 0.299999999
                    outputWindow.setText(operationAtFront+String.valueOf(input[currentInput]));
                }
            }
        }
        if(e.getSource()==decimal&&pastDecimal==-1){
            pastDecimal=1;
        }
        if(e.getSource()==plus){
            currentInput=2;
            outputWindow.setText("+");
            operation = "plus";
            pastDecimal=-1;
        }
        if(e.getSource()==minus){
            currentInput=2;
            outputWindow.setText("-");
            operation = "minus";
            pastDecimal=-1;
        }
        if(e.getSource()==times){
            currentInput=2;
            outputWindow.setText("*");
            operation = "times";
            pastDecimal=-1;
        }
        if(e.getSource()==divide){
            currentInput=2;
            outputWindow.setText("/");
            operation = "divide";
            pastDecimal=-1;
        }
        if(e.getSource()==equals){
            if(operation.equals("plus")){
                output=input[1]+input[2];
            }
            if(operation.equals("minus")){
                output=input[1]-input[2];
            }
            if(operation.equals("times")){
                if(outputWindow.getText().equals("*")){
                    output=input[1]*input[1];
                } else {
                    output = input[1] * input[2];
                }
            }
            if(operation.equals("divide")){
                if(outputWindow.getText().equals("/")){
                    output=Math.sqrt(input[1]);
                } else {
                    output = input[1] / input[2];
                }
            }
            output=Math.round(output*Math.pow(10,12))/Math.pow(10,12);

            outputWindow.setText(String.valueOf(output));
            input[1]=output;
            input[2]=0;
            currentInput=1;
            operation="";
            operationAtFront="";
        }

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
    }
}