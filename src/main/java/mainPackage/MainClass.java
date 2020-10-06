package mainPackage;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author chris
 */


public class MainClass implements ActionListener, KeyListener{
    
    private JFrame frame;
    private JButton button, button2, enterButton, finishedRegister;
    private JLabel label, label2;
    private JPanel panel, panel2, panel3, panel4;
    private JTextField startNumberEnter;
    JScrollPane pane, pane2;
    
    int count = 0, numberEntered = 0;
    ArrayList persons = new ArrayList<Person>();
    ArrayList participants = new ArrayList<Participant>();
    
    public static void main(String[] args){
        MainClass mc = new MainClass();
        mc.initGUI();
    }

    public void importExcel(){
        try{
            ReadExcelFile read = new ReadExcelFile();
            ArrayList<Map> partpants = read.readFile();
            for(int i = 1; i<partpants.size(); i++){
                addPerson(partpants.get(i).get("Name").toString(), partpants.get(i).get("Adress").toString(), 
                    partpants.get(i).get("Birthdate").toString(), (int)Double.parseDouble(partpants.get(i).get("Startnumber").toString()));
                addParticipant((int)Double.parseDouble(partpants.get(i).get("Startnumber").toString()), 
                    (int)Double.parseDouble(partpants.get(i).get("Starttime").toString()), 0, 0);
            }
            //System.out.println(partpants.get(1).get("Starttime"));
        }catch(IOException e){
            System.out.println("Excel import FAILED!!!");
        }
    }
    
    public void addPerson(String name, String adress, String birthDate, int startnumber){
        String[] bDates = birthDate.split("-");
        int BDay = Integer.parseInt(bDates[0]);
        int BMonth = Integer.parseInt(bDates[1]);
        int BYear  = Integer.parseInt(bDates[2]);
        Person p = new Person(name, adress, BDay, BMonth, BYear, startnumber);
        persons.add(p);
    }
    public void addParticipant(int startnumber, int starttime, int endtime, int totaltime){
        Participant part = new Participant(startnumber, starttime, endtime, totaltime);
        participants.add(part);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("You have pressed "+e.getKeyCode());
        if(e.getKeyCode() == 10){
            startNumberEnter.setFocusable(true);
            //System.out.println("Enter was pressed");
            numberEntered++;
            label2.setText("Personer som har kommet i mål, men som ikke er registrert: "+numberEntered);
        }
    }
    public void initGUI(){
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1600,1000));
        //Line 1 with buttons
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        button = new JButton("Importere fra Excel");
        button.addActionListener((ActionEvent e) -> {
            importExcel();  
        });
        button2 = new JButton("Start Klokke");
        button2.addActionListener((ActionEvent e) -> {
             
        });
        button.setFocusable(false);
        button2.setFocusable(false);
        panel.add(button);
        panel.add(button2);
        
        //Line 2 with starting participants
        GridLayout gl = new GridLayout(0,5);
        setUpPanel2(gl);
        
        //Line 3 with finished participants
        setUpPanel3(gl);
        
        //Line 4 for entering the startnumber on the finished participant
        setUpPanel4();
        
        //The Frame
        JPanel middle = new JPanel();
        //middle.setPreferredSize(new Dimension(1600,300));
        middle.add(pane, BorderLayout.NORTH);
        middle.add(pane2, BorderLayout.SOUTH);
        
        
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(middle, BorderLayout.CENTER);
        frame.add(panel4, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Timer");
        frame.pack();
        frame.setVisible(true);
    }
    public void setUpPanel2(GridLayout g){
        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel2.setLayout(g);
        panel2.add(new JLabel("Startnummer"));
        panel2.add(new JLabel("Navn"));
        panel2.add(new JLabel("Starttid"));
        panel2.add(new JLabel("Sluttid"));
        panel2.add(new JLabel("Totaltid"));
        pane = new JScrollPane(panel2);
        pane.setPreferredSize(new Dimension(1600,400));
    }
    public void setUpPanel3(GridLayout g){
        panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel3.setLayout(g);
        panel3.add(new JLabel("Live Long and Prosper"));
        pane2 = new JScrollPane(panel3);
        pane2.setPreferredSize(new Dimension(1600,400));
    }
    public void setUpPanel4(){
        panel4 = new JPanel();
        startNumberEnter = new JTextField();
        startNumberEnter.setPreferredSize(new Dimension(200, 24));
        label2 = new JLabel("Personer som har kommet i mål, men som ikke er registrert: 0");
        enterButton = new JButton("Registrer i målgang");
        enterButton.setFocusable(false);
        enterButton.addActionListener((ActionEvent e) -> {
            if(startNumberEnter.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Husk å skrive inn startnummeret", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
            }else{
                try{
                    int d = Integer.parseInt(startNumberEnter.getText());
                    startNumberEnter.setText("");
                    numberEntered--;
                    label2.setText("Personer som har kommet i mål, men som ikke er registrert: "+numberEntered);
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Dette er ikke et nummer", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        finishedRegister = new JButton("Ferdig med Registrering");
        finishedRegister.setFocusable(false);
        finishedRegister.addActionListener((ActionEvent e) -> {
            startNumberEnter.setFocusable(false);
        });
        panel4.add(startNumberEnter);
        panel4.add(enterButton);
        panel4.add(finishedRegister);
        panel4.add(label2);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {}
}
