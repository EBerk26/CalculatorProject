import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Instructions: https://rb.gy/3h4ruo

public class Calculator implements ActionListener {
    JButton[] numberButtons;
    JFrame frame;
    JLabel output;
    JButton divide;
    JButton times;
    JButton minus;
    JButton plus;
    JButton decimal;
    JButton equals;
    JButton clear;
    Font oneFifthFont;
    Font oneTenthFont;

    public static void main(String[] args) {
        String fonts[]
                = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }
        new Calculator();
    }

    public Calculator () {
        frame = new JFrame("Calculator");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel backPanel = new JPanel(new GridLayout(6,1));
        frame.add(backPanel);

        output = new JLabel();
        backPanel.add(output);
        output.setHorizontalAlignment(SwingConstants.RIGHT);
        output.setFont(new Font ("Plain",Font.PLAIN,frame.getHeight()/5));

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
        }

        for(int x =0;x<=2;x++){
            for(int y=7-3*x;y<=9-3*x;y++){
                miniMiddlePanels[x].add(numberButtons[y]);
            }
        }
        clear = new JButton("Clear");
        divide = new JButton("/");
        times = new JButton("*");
        minus = new JButton("-");
        plus = new JButton("+");
        decimal = new JButton(".");
        equals = new JButton("=");

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
        clear.setPreferredSize(new Dimension(frame.getWidth()*3/4,frame.getHeight()/5));




        frame.setVisible(true);
        int x=0;
        while(true){
            if(System.currentTimeMillis()%500==0){
                ChangeFontSizes();
                clear.setPreferredSize(new Dimension(frame.getWidth()*3/4,frame.getHeight()/5));
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    void ChangeFontSizes(){
        oneFifthFont = new Font("Arial",Font.PLAIN,frame.getHeight()/5);
        oneTenthFont = new Font("I'm pretty sure this input doesn't matter",Font.PLAIN,frame.getHeight()/10);
        for(int x =0;x<=9;x++){
            numberButtons[x].setFont(oneTenthFont);
        }
        output.setFont(oneTenthFont);
        divide.setFont(oneTenthFont);
        times.setFont(oneTenthFont);
        minus.setFont(oneTenthFont);
        plus.setFont(oneTenthFont);
        decimal.setFont(oneTenthFont);
        equals.setFont(oneTenthFont);
        clear.setFont(oneTenthFont);
    }

}