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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author chris
 */


public class MainClass implements ActionListener, KeyListener{
    
    private JFrame frame;
    private JButton button, button2, enterButton, finishedRegister, insertButton, stopButton, createPDF;
    private JLabel label, label2, theTimer, excelImportStatus;
    private JPanel panel, panel2, panel3, panel4, middle;
    private JTextField startNumberEnter;
    JScrollPane pane, pane2;
    GridLayout gl = new GridLayout(0,5);
    GridLayout g2 = new GridLayout(0,3);
  
    int count = 0, numberEntered = 0; long startTime = 0;
    ArrayList participants = new ArrayList<Participant>();
    ArrayList income = new ArrayList<Long>();
    ArrayList finishedParticipants = new ArrayList<Participant>();
    
    ActionListener updateClock = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            long elapsedTime = System.currentTimeMillis()-startTime;
            long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
            elapsedTime -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
            elapsedTime -= TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
            theTimer.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
        }
    };
    Timer timer = new Timer(100,updateClock);
    
    public static void main(String[] args){
        MainClass mc = new MainClass();
        mc.initGUI();
    }

    public void importExcel(){
        try{
            ReadExcelFile read = new ReadExcelFile();
            ArrayList<Map> partpants = read.readFile();
            for(int i = 1; i<partpants.size(); i++){
                addParticipant((int)Double.parseDouble(partpants.get(i).get("Startnumber").toString()), 
                    partpants.get(i).get("Starttime").toString(), "00:00:00", "00:00:00", partpants.get(i).get("Name").toString());
            }
            //System.out.println(partpants.get(1).get("Starttime"));
            //System.out.println(participants.get(1));
            excelImportStatus.setText("Excel import SUCSESS :D");
        }catch(IOException e){
            System.out.println("Excel import FAILED!!!");
            excelImportStatus.setText("Excel import FAILED!!!");
        }
    }
    public void addParticipant(int startnumber, String starttime, String endtime, String totaltime, String name){
        String[] theStarttime = starttime.split("n");
        Participant part = new Participant(startnumber, theStarttime[1], endtime, totaltime, name);
        participants.add(part);
        
        
    }
    public void startTimer(){
        LocalTime start = LocalTime.now();
        for(int i = 0; i < participants.size(); i++){
            Participant part2 = (Participant) participants.get(i);
            part2.setStartime(String.format("%02d:%02d:%02d",start.getHour(), start.getMinute(), start.getSecond()));
        }
        addToList();
        startTime = System.currentTimeMillis();
        timer.start();
    }
    public String convertTime(long time){
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        //return hours+":"+minutes+":"+seconds;
        return String.format("%02d:%02d:%02d",hours, minutes, seconds);
    }
    public void addToList(){
        middle.remove(0);
        panel2.revalidate();
        panel2.repaint();
        setUpPanel2(g2);
        for(int i = 0; i < participants.size(); i++){
            Participant part2 = (Participant) participants.get(i);
            panel2.add(new JLabel(part2.getStartnumber()+""));
            panel2.add(new JLabel(part2.getName()));
            panel2.add(new JLabel(part2.getStartime()));
        }
        middle.add(pane, BorderLayout.NORTH, 0);
        frame.pack();
        frame.setVisible(true);
    }
    public void addToList2(){
        middle.remove(1);
        panel3.revalidate();
        panel3.repaint();
        setUpPanel3(gl);
        for(int i = 0; i < finishedParticipants.size(); i++){
            Participant part2 = (Participant) finishedParticipants.get(i);
            panel3.add(new JLabel(part2.getStartnumber()+""));
            panel3.add(new JLabel(part2.getName()));
            panel3.add(new JLabel(part2.getStartime()));
            panel3.add(new JLabel(part2.getEndtime()));
            panel3.add(new JLabel(part2.getTotaltime()));
        }
        middle.add(pane2, BorderLayout.NORTH, 0);
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("You have pressed "+e.getKeyCode());
        if(e.getKeyCode() == 10){
            startNumberEnter.setFocusable(true);
            //System.out.println("Enter was pressed");
            numberEntered++;
            label2.setText("Ikke registrert: "+numberEntered);
            long time = System.currentTimeMillis();
            System.out.println(time);
            System.out.println(convertTime(time));
            income.add(time);
        }
    }
    public void initGUI(){
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1600,1000));
        //Line 1 with buttons
        panel = new JPanel(gl);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        button = new JButton("Importere fra Excel");
        button.addActionListener((ActionEvent e) -> {
            importExcel();  
        });
        button2 = new JButton("Start Klokke");
        button2.addActionListener((ActionEvent e) -> {
            startTimer();
        });
        insertButton = new JButton("Sett inn i liste");
        insertButton.addActionListener((ActionEvent e) -> {
            addToList();   
        });
        theTimer = new JLabel("00:00:00", JLabel.CENTER);
        stopButton = new JButton("Stop");
        stopButton.addActionListener((ActionEvent e) -> {
            timer.stop();
        });
        excelImportStatus = new JLabel();
        button.setFocusable(false);
        button2.setFocusable(false);
        insertButton.setFocusable(false);
        stopButton.setFocusable(false);
        panel.add(button);
        panel.add(button2);
        panel.add(insertButton);
        panel.add(theTimer);
        panel.add(stopButton);
        panel.add(excelImportStatus);
        
        //Line 2 with starting participants
        
        setUpPanel2(gl);
        
        //Line 3 with finished participants
        setUpPanel3(gl);
        
        //Line 4 for entering the startnumber on the finished participant
        setUpPanel4();
        
        //The Frame
        middle = new JPanel();
        //middle.setPreferredSize(new Dimension(1600,300));
        middle.add(pane, BorderLayout.NORTH);
        middle.add(pane2, BorderLayout.SOUTH);
        frame.add(middle, BorderLayout.CENTER);
        
        
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel4, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Timer");
        frame.pack();
        frame.setVisible(true);
    }
    public void setUpPanel2(GridLayout g){
        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel2.setLayout(g2);
        panel2.add(new JLabel("Startnummer"));
        panel2.add(new JLabel("Navn"));
        panel2.add(new JLabel("Starttid"));
        pane = new JScrollPane(panel2);
        pane.setPreferredSize(new Dimension(1600,400));
    }
    public void setUpPanel3(GridLayout g){
        panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel3.setLayout(g);
        panel3.add(new JLabel("Startnummer"));
        panel3.add(new JLabel("Navn"));
        panel3.add(new JLabel("Starttid"));
        panel3.add(new JLabel("I Mål"));
        panel3.add(new JLabel("Tid Brukt"));
        pane2 = new JScrollPane(panel3);
        pane2.setPreferredSize(new Dimension(1600,400));
    }
    public void setUpPanel4(){
        panel4 = new JPanel(gl);
        startNumberEnter = new JTextField();
        startNumberEnter.setPreferredSize(new Dimension(100, 24));
        label2 = new JLabel("Ikke registrert: 0", JLabel.CENTER);
        enterButton = new JButton("Registrer i mål");
        enterButton.setFocusable(false);
        enterButton.addActionListener((ActionEvent e) -> {
            if(startNumberEnter.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Husk å skrive inn startnummeret", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
                startNumberEnter.setText("");
            }else{
                try{
                    int d = Integer.parseInt(startNumberEnter.getText());
                    long finishTime = (long)income.get(0)+7200000;
                    long endTime = finishTime-startTime-7200000;
                    startNumberEnter.setText("");
                    numberEntered--;
                    label2.setText("Ikke registrert: "+numberEntered);
                    for (int i = 0; i < participants.size(); i++) {
                        Participant part2 = (Participant) participants.get(i);
                        if(part2.getStartnumber() == d){
                            part2.setTotaltime(convertTime(endTime));
                            part2.setEndtime(convertTime(finishTime));
                            finishedParticipants.add(part2);
                            participants.remove(i);
                        }
                    }
                    addToList();
                    addToList2();
                    income.remove(0);
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Dette er ikke et nummer", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
                    startNumberEnter.setText("");
                }
            }
        });
        finishedRegister = new JButton("Ferdig med Registrering");
        finishedRegister.setFocusable(false);
        finishedRegister.addActionListener((ActionEvent e) -> {
            startNumberEnter.setFocusable(false);
        });
        createPDF = new JButton("Send til PDF");
        createPDF.setFocusable(false);
        createPDF.addActionListener((ActionEvent e) -> {
           PrintToPDF p = new PrintToPDF(finishedParticipants); 
        });
        panel4.add(startNumberEnter);
        panel4.add(enterButton);
        panel4.add(finishedRegister);
        panel4.add(label2);
        panel4.add(createPDF);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {}
}
