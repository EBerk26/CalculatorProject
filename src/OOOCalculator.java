import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class OOOCalculator implements ActionListener {

//Instructions: https://rb.gy/3h4ruo

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
    JButton power;
    Font oneFifthFont;
    Font oneTenthFont;
    String inputString = "";
    JButton openBracket;
    JButton closeBracket;

    public static void main(String[] args) {
        new OOOCalculator();
    }

    public OOOCalculator() {
        frame = new JFrame("Order of Operations Calculator");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel backPanel = new JPanel(new GridLayout(6, 1));
        frame.add(backPanel);

        outputWindow = new JLabel();
        backPanel.add(outputWindow);
        outputWindow.setHorizontalAlignment(SwingConstants.RIGHT);
        outputWindow.setFont(new Font("Plain", Font.PLAIN, frame.getHeight() / 5));

        JPanel[] miniMiddlePanels = new JPanel[3];
        for (int x = 0; x <= 2; x++) {
            miniMiddlePanels[x] = new JPanel(new GridLayout(1, 4));
            backPanel.add(miniMiddlePanels[x]);
        }
        JPanel rowWith0InIt = new JPanel(new GridLayout(1, 4));
        JPanel bottomMiniPanel = new JPanel(new BorderLayout());
        JPanel brackets = new JPanel(new GridLayout(1, 2));
        backPanel.add(rowWith0InIt);
        backPanel.add(bottomMiniPanel);
        numberButtons = new JButton[10];
        for (int x = 0; x <= 9; x++) {
            numberButtons[x] = new JButton(String.valueOf(x));
            numberButtons[x].addActionListener(this);
        }

        for (int x = 0; x <= 2; x++) {
            for (int y = 7 - 3 * x; y <= 9 - 3 * x; y++) {
                miniMiddlePanels[x].add(numberButtons[y]);
            }
        }
        power = new JButton("^");
        power.addActionListener(this);
        openBracket = new JButton("(");
        openBracket.addActionListener(this);
        closeBracket = new JButton(")");
        closeBracket.addActionListener(this);
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
        changeColor(equals);
        changeColor(openBracket);
        changeColor(closeBracket);
        changeColor(decimal);
        changeColor(plus);
        changeColor(minus);
        changeColor(divide);
        changeColor(times);
        changeColor(clear);
        changeColor(power);
        for (int x = 0; x <= 9; x++) {
            changeColor(numberButtons[x]);
        }

        miniMiddlePanels[0].add(divide);
        divide.addActionListener(this);
        miniMiddlePanels[1].add(times);
        miniMiddlePanels[2].add(minus);
        //miniMiddlePanels[1].add(power);
        rowWith0InIt.add(brackets);
        brackets.add(openBracket);
        brackets.add(closeBracket);
        rowWith0InIt.add(numberButtons[0]);
        rowWith0InIt.add(decimal);
        rowWith0InIt.add(plus);
        bottomMiniPanel.add(equals);
        bottomMiniPanel.add(clear, BorderLayout.WEST);
        ChangeFontSizes();
        clear.setPreferredSize(new Dimension(frame.getWidth() * 3 / 4, frame.getHeight() / 5));


        frame.setVisible(true);
        while (true) {
            if (System.currentTimeMillis() % 256 == 0) {
                ChangeFontSizes();
                clear.setPreferredSize(new Dimension(frame.getWidth() * 3 / 4, frame.getHeight() / 5));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==openBracket){
            type("(");
        }
        if(e.getSource()==closeBracket){
            type(")");
        }
        if (e.getSource() == clear) {
            inputString = "";
        }
        for (int x = 0; x <= 9; x++) {
            if (e.getSource() == numberButtons[x]) {
                type(x);
            }
        }
        if (e.getSource() == plus) {
            type("+");
        }
        if (e.getSource() == minus) {
            type("-");
        }
        if (e.getSource() == times) {
            type("*");
        }
        if (e.getSource() == divide) {
            type("/");
        }
        if (e.getSource() == decimal) {
            type(".");
        }
        if (e.getSource() == equals) {
            try {
                String output = OOOEvaluator.evaluate(inputString);
                if(output.charAt(0)=='|'){
                    inputString="-"+output.substring(1);
                } else {
                    inputString = output;
                }
            } catch (Exception exception) {
                outputWindow.setText("Error");
            }
        }
        outputWindow.setText(inputString);
    }

    void ChangeFontSizes() {
        oneFifthFont = new Font("You can put whatever here and it doesn't matter unless it's the name of a font.", Font.PLAIN, frame.getHeight() / 5);
        oneTenthFont = new Font("Cool font name", Font.PLAIN, frame.getHeight() / 10);
        for (int x = 0; x <= 9; x++) {
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
        openBracket.setFont(oneTenthFont);
        closeBracket.setFont(oneTenthFont);
    }

    void changeColor(JButton input) {
        input.setOpaque(true);
        input.setForeground(Color.BLUE);
        input.setBackground(Color.ORANGE);
    }

    void type(String x) {
        inputString = inputString + x;
    }

    void type(int x) {
        inputString = inputString + String.valueOf(x);
    }

    static double parseDouble(String input) {
        if (input.charAt(0) == '|') {
            return -1 * Double.parseDouble(input.substring(1));
        } else {
            return Double.parseDouble(input);
        }
    }
}