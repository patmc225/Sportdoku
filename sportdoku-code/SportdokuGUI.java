import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.util.Timer;
import java.util.TimerTask;
import org.w3c.dom.events.MouseEvent;

public class SportdokuGUI
{
    JFrame frame;
    JPanel choices;
    JButton [][] button;
    JPanel buttonPanel; 
    JButton candidate;
    JButton delete;
    JButton undo;
    ArrayList<String[]> attributes;
    ArrayList<Color[]> colorss;
    ArrayList<JButton> B;
    JPanel[][] panel;
    int[][] dBoard;
    Color color;
    JButton newb;
    Color dColor;
    TreeMap<String, String[]> map;
    TreeMap<String, String[]> player;
    Border gold;
    Border border;
    CompoundBorder redgold;
    JLabel[][] names;
    JLabel[][] numbers;
    JLabel[][] teamName;
    JLabel[][] position;
    JTextField[][] textfield;
    ArrayList<String[]> randomnames;
    Border red;
    boolean[][] original;
    int mistakes;
    JLabel errors;
    JLabel timer;
    JLabel text2;
    ArrayList<MouseListener[]> bmouse;
    ArrayList<MouseListener[]> tmouse;
    KeyListener key;
    String clock;
    JLabel text;
    JButton X;
    JPanel win;
    JPanel shade;
    
    public SportdokuGUI() throws IOException
    {
        frame = new JFrame("Sportdoku");
        frame.setLayout(null);

        border = BorderFactory.createLineBorder(Color.black,2);

        shade = new JPanel();
        shade.setBackground(new Color(0,0,0,100));
        shade.setBounds(0,0,1900,1000);

        win = new JPanel();
        win.setBorder(border);
        win.setBounds(550,260, 400, 400);

        text = new JLabel("Congratulations, you solved the puzzle!");
        text.setFont(new Font("College", Font.BOLD, 15));
        text.setForeground(Color.black);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setBounds(20,50,360, 50);  
        text2 = new JLabel();
        text2.setFont(new Font("College", Font.BOLD, 15));
        text2.setForeground(Color.black);
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setBounds(20,100,360, 50);

        X = new JButton("X");
        X.setForeground(Color.black);
        X.setOpaque(true);
        X.setBackground(null);
        X.setBorder(border);
        X.setFont(new Font("College", Font.BOLD, 10));
        X.setPreferredSize(new Dimension(30,30));
        X.addMouseListener(new MouseListener(){ public void mouseClicked(java.awt.event.MouseEvent e){} public void mouseEntered(java.awt.event.MouseEvent e){} public void mouseExited(java.awt.event.MouseEvent e){}
        public void mousePressed(java.awt.event.MouseEvent e)
        {
            X.setBackground(Color.darkGray);
            for(int r=0;r<6;r++)for(int c=0;c<6;c++)
            {
                button[r][c].setBorder(border);
                button[r][c].addKeyListener(key);
                for(MouseListener tm:bmouse.get((r*6)+c)) button[r][c].addMouseListener(tm);
                for(MouseListener tm:tmouse.get((r*6)+c)) textfield[r][c].addMouseListener(tm);
            }
            shade.setVisible(false);
        }
        public void mouseReleased(java.awt.event.MouseEvent e){X.setBackground(null);} });
        X.setBounds(370,0,30,30);
        
        win.add(X);
        win.add(text);
        win.add(text2);
        shade.add(win);
        frame.add(shade);
        shade.setVisible(false);


        BufferedReader colors = new BufferedReader(new FileReader("Color.txt"));
        map = new TreeMap<String, String[]>();

        String n = colors.readLine();
        while(n!=null)
        {
            String[] m = n.split(" ");
            String[] colo = new String[6];
            for(int i = 0; i<6; i++) colo[i] = m[i+1];
            map.put(m[0], colo);
            n = colors.readLine();
        }
        colors.close();

        BufferedReader players = new BufferedReader(new FileReader("Players.txt"));
        player = new TreeMap<String, String[]>();
        randomnames = new ArrayList<String[]>();

        String pl = players.readLine() +  players.readLine();
        while(pl!=null)
        {
            String[] m = pl.split(" "); 

            if(m.length<2) break;
            String[] p = new String[] { m[0].toLowerCase(), m[1].toLowerCase(), m[0].toLowerCase() + " " + m[1].toLowerCase() };
            randomnames.add(p);
            String[] info = new String[] {m[2],m[3],m[5],m[4]};
            player.put(p[2], info);
            //System.out.println(m[0]+ " "+m[1]+" "+ info[0]+" "+info[1]+" "+info[2]);
            players.readLine();
            pl = players.readLine() + players.readLine();
        }
        players.close();

        // ArrayList<String> checker = new ArrayList<String>();
        // BufferedReader check = new BufferedReader(new FileReader("Checker.txt"));
        // String ch = check.readLine();
        // while(ch!=null)
        // {
        //     String[] m = ch.split(" "); 
        //     if(m.length<2) break;
        //     checker.add(m[0].toLowerCase() + " " + m[1].toLowerCase());
        //     check.readLine();
        //     check.readLine();
        //     ch = check.readLine();
        // }
        // check.close();

        
        // for(String[] r: randomnames)
        // {
        //     int count = 0;
        //     for(String c: checker)
        //     {
        //         if(c.equals(r[2])) 
        //         {
        //             count++;
        //             break;
        //         }
        //     }
        //    if(count==0) System.out.println(r[2]);
        // }


        gold = BorderFactory.createLineBorder(Color.yellow, 2);
        red = BorderFactory.createLineBorder(Color.red, 2);
        redgold = BorderFactory.createCompoundBorder(red, gold);

        buttonPanel = new JPanel();
		button = new JButton[6][6];
        panel = new JPanel[2][2];
        B = new ArrayList<>();
        attributes = new ArrayList<>();
        colorss = new ArrayList<>();
        names = new JLabel[6][6];
        numbers = new JLabel[6][6];
        teamName = new JLabel[6][6];
        position = new JLabel[6][6];
        textfield = new JTextField[6][6];
        original = new boolean[6][6];
        bmouse = new ArrayList<MouseListener[]>();
        tmouse = new ArrayList<MouseListener[]>();
        errors = new JLabel();
        timer = new JLabel();
        mistakes = 0;

        buttonPanel.setLayout(new GridLayout(2,2));
        
        for(int j=0;j<2;j++) for(int i =0; i<2; i++)
        {
            panel[i][j] = new JPanel();
            panel[i][j].setLayout(new GridLayout(3,3));
            panel[i][j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 2), BorderFactory.createLineBorder(Color.black, 2)));
        }
      
        color = new Color(200, 235, 255);
        dColor = new Color(170, 205, 225);
        Font f = new Font("Arial", Font.BOLD, 25);


		for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) 
        {
            button[r][c] = new JButton(); 
            button[r][c].setLayout(new GridBagLayout());
            //button[r][c].addKeyListener(new key());
            panel[r/3][c/3].add(button[r][c]);
            key = new key();
            button[r][c].addKeyListener(key);
            original[r][c] = false;

            /*JLabel[] p = new JLabel[6];
            for(int i = 1; i<10; i++) 
            {
                p[i-1] = new JLabel();
                p[i-1].setText(i+"");
                p[i-1].setOpaque(false);
                p[i-1].setBackground(Color.white);
                p[i-1].setForeground(Color.darkGray);
                button[r][c].add(p[i-1]);
            }*/

            names[r][c] = new JLabel();
            names[r][c].setFont(new Font("College", Font.BOLD, 10));
            GridBagConstraints gbcName = new GridBagConstraints();
            gbcName.gridx = 0;
            gbcName.gridy = 0;
            gbcName.weightx = 1;
            gbcName.weighty = 0;
            gbcName.fill = GridBagConstraints.BOTH;
            gbcName.insets = new Insets(10, 0, 0, 0);
            gbcName.anchor = GridBagConstraints.PAGE_START;
            names[r][c].setHorizontalAlignment(JLabel.CENTER);

            numbers[r][c] = new JLabel();
            numbers[r][c].setFont(new Font("College", Font.BOLD, 35));
            GridBagConstraints gbcNumber = new GridBagConstraints();
            gbcNumber.gridx = 0;
            gbcNumber.gridy = 1;
            gbcNumber.weightx = 1;
            gbcNumber.weighty = 1;
            gbcNumber.fill = GridBagConstraints.BOTH;
            gbcNumber.insets = new Insets(4, 0, 10, 0);
            gbcNumber.anchor = GridBagConstraints.CENTER;
            numbers[r][c].setHorizontalAlignment(JLabel.CENTER);

            teamName[r][c] = new JLabel();
            teamName[r][c].setFont(new Font("College", Font.BOLD, 10));
            GridBagConstraints gbcTeam = new GridBagConstraints();
            gbcTeam.gridx = 0;
            gbcTeam.gridy = 3;
            gbcTeam.weightx = 1;
            gbcTeam.weighty = 2;
            gbcTeam.fill = GridBagConstraints.BOTH;
            gbcTeam.insets = new Insets(0, 10, 0, 0);
            gbcTeam.anchor = GridBagConstraints.BASELINE;

            position[r][c] = new JLabel();
            position[r][c].setFont(new Font("College", Font.BOLD, 10));
            GridBagConstraints gbcPosition = new GridBagConstraints();
            gbcPosition.gridx = 0;
            gbcPosition.gridy = 3;
            gbcPosition.weightx = 1;
            gbcPosition.weighty = 2;
            gbcPosition.fill = GridBagConstraints.BOTH;
            gbcPosition.insets = new Insets(0, 80, 0, 0);
            gbcPosition.anchor = GridBagConstraints.BASELINE_LEADING;

            textfield[r][c] = new JTextField();
            textfield[r][c].setOpaque(true);
            textfield[r][c].setBackground(null);
            GridBagConstraints gbcTF = new GridBagConstraints();
            gbcTF.gridx = 0;
            gbcTF.gridy = 1;
            gbcTF.weightx = 1;
            gbcTF.weighty = 1;
            gbcTF.fill = GridBagConstraints.BOTH;
            gbcTF.insets = new Insets(0, 0, 10, 0);
            gbcTF.anchor = GridBagConstraints.CENTER;
            textfield[r][c].setHorizontalAlignment(JLabel.CENTER);

            button[r][c].add(textfield[r][c],gbcTF);
            button[r][c].add(names[r][c],gbcName);
            button[r][c].add(numbers[r][c],gbcNumber);
            button[r][c].add(teamName[r][c],gbcTeam);
            button[r][c].add(position[r][c],gbcPosition);

            final int row = r;
            final int col = c;

            button[r][c].addMouseListener(new MouseListener() {
                public void mousePressed(java.awt.event.MouseEvent e) {

                    for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if(r!=row||c!=col) if(button[r][c].getBorder()!=red) 
                    {
                        if(button[r][c].getBorder()==redgold) button[r][c].setBorder(red);
                        else button[r][c].setBorder(border);

                        if(button[row][col].getBorder() == red ||button[row][col].getBorder() == redgold) button[row][col].setBorder(redgold);
                        else button[row][col].setBorder(gold);
                    }
                }
                public void mouseClicked(java.awt.event.MouseEvent e) {}
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                public void mouseEntered(java.awt.event.MouseEvent e) {}
                public void mouseExited(java.awt.event.MouseEvent e) {}
            });

            textfield[r][c].addMouseListener(new MouseListener() {
                public void mousePressed(java.awt.event.MouseEvent e) {
                    for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if(r!=row||c!=col) if(button[r][c].getBorder()!=red) 
                    {
                        if(button[r][c].getBorder()==redgold) button[r][c].setBorder(red);
                        else button[r][c].setBorder(border);
                        
                        if(button[row][col].getBorder() == red ||button[row][col].getBorder() == redgold) button[row][col].setBorder(redgold);
                        else button[row][col].setBorder(gold);
                    }
                }
                public void mouseClicked(java.awt.event.MouseEvent e) {}
                public void mouseReleased(java.awt.event.MouseEvent e) {}
                public void mouseEntered(java.awt.event.MouseEvent e) {}
                public void mouseExited(java.awt.event.MouseEvent e) {}
            });

            textfield[r][c].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {   
                    if(textfield[row][col].getText()!="")
                    {
                        String[] stuff = new String[] {button[row][col].getText(), teamName[row][col].getText(), names[row][col].getText(), numbers[row][col].getText(), position[row][col].getText()};
                        attributes.add(stuff);
                        B.add(button[row][col]);
                        Color[] color = new Color[]{button[row][col].getBackground(), button[row][col].getForeground()};
                        colorss.add(color);
                            
                        
                        String input = textfield[row][col].getText().toLowerCase();
                        ArrayList<String> arr = new ArrayList<String>();
                        for(String[] n: randomnames) for(String name:n) if(input.equals(name)) if(player.get(n[2])[2].equals(button[row][col].getText())) 
                        {
                            arr.add(n[1]);
                            arr.add(player.get(n[2])[1]);
                            arr.add(player.get(n[2])[2]);
                            arr.add(player.get(n[2])[0]);
                        }
                        
                        if(arr.size()<5 && arr.size()>1)
                        {
                            String[] colors = new String[3];
                            colors = map.get(button[row][col].getText());
                            button[row][col].setBackground(new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
                            names[row][col].setText(arr.get(0).toUpperCase());
                            numbers[row][col].setText(arr.get(1));
                            teamName[row][col].setText(arr.get(2));
                            position[row][col].setText(arr.get(3));
                            button[row][col].setText("");
                            button[row][col].remove(textfield[row][col]);
                        }
                        if(!checkBoard()) 
                        {
                            mistakes++;
                            errors.setText("Errors: " + mistakes);
                        }

                        boolean notdone = false;
                        for ( int r=0 ; r < button.length ; ++r )
                        { 
                            for ( int c=0 ; c < button.length ; ++c ) if(position[r][c].getText()=="" || button[r][c].getBorder()==red || button[r][c].getBorder()==redgold)
                            {
                                notdone = true;
                                break;
                            }
                            if(notdone) break;
                        }
                        if(!notdone)
                        {
                            for(int r=0;r<6;r++)for(int c=0;c<6;c++)
                            {
                                shade.setVisible(true);
                                text2.setText("Time: " + clock + "        Errors: " + mistakes);
                                button[r][c].setBorder(border);
                                bmouse.add(button[r][c].getMouseListeners());
                                tmouse.add(textfield[r][c].getMouseListeners());
                                for(MouseListener tm:bmouse.get((r*6)+c)) button[r][c].removeMouseListener(tm);
                                for(MouseListener tm:tmouse.get((r*6)+c)) textfield[r][c].removeMouseListener(tm);
                                KeyListener[] k = button[r][c].getKeyListeners();
                                for(MouseListener l: delete.getMouseListeners()) delete.removeMouseListener(l);
                                for(MouseListener l: undo.getMouseListeners()) undo.removeMouseListener(l);
                                button[r][c].removeKeyListener(k[0]);

                            }  
                        }
                    }
                }      
            });
        }
        newBoard();


        // adds each tile to the panel          
        for ( int r=0 ; r < 2 ; ++r ) for ( int c=0 ; c < 2 ; ++c ) buttonPanel.add(panel[r][c]);

        // creating choices panel
        /* choices = new JPanel();
        choices.setLayout(new GridLayout(3,3));
        for(int i = 1; i<10; i++)
        {
            int num = i;
            JButton J = new JButton();
            J.setText(i+"");
            J.setBorder(BorderFactory.createLineBorder(Color.black,3));
            J.setOpaque(true);
            J.setBackground(Color.white);
            Font ff = new Font("Arial", Font.BOLD, 50);
            J.setFont(ff);

            J.addKeyListener(new key());

            // actions for when each choice is clicked
            J.addMouseListener(new MouseListener() { public void mouseEntered(MouseEvent e){} public void mouseExited(MouseEvent e){} public void mouseClicked(MouseEvent e){}
                public void mousePressed(MouseEvent e) 
                {
                    // flashes the choice gray when pressed
                    J.setBackground(Color.gray);

                    // finding the selected tile
                    for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(button[ro][co].getBackground()==color)
                    {
                        // putting former values in variables for the undo button
                        text.add(button[ro][co].getText());                      
                        B.add(button[ro][co]);
                        ArrayList<Integer> arr = new ArrayList<>();
                        for(int i = 0; i<6; i++) if(button[ro][co].getComponent(i).isVisible()) arr.add(i);
                        canText.add(arr);

                        // editing tile in normal mode
                        if(candidate.getBackground()==Color.white)
                        {
                            for(int i = 0; i<6; i++) button[ro][co].getComponent(i).setVisible(false);
                            button[ro][co].setText(J.getText());
                        }

                        // editing tile in candidate mode
                        else
                        {                          
                            if(button[ro][co].getComponent(num-1).isVisible()) button[ro][co].getComponent(num-1).setVisible(false);
                            else button[ro][co].getComponent(num-1).setVisible(true);
                            button[ro][co].setText("");
                        }

                        // checking the board
                        checkBoard();
                    }
                }     
                public void mouseReleased(MouseEvent e) {
                    if(candidate.getBackground()==Color.black) J.setBackground(color);
                    else J.setBackground(Color.white);                   
                }          
            });

            // adding each choice to the table
            choices.add(J);
        } 
        
        // creating the candidate button
        candidate = new JButton();
        candidate.setText("Candidate Mode");
        candidate.setBorder(BorderFactory.createLineBorder(Color.black, 3)); 
        candidate.setOpaque(true);
        candidate.setBackground(Color.white);

        //changing the candidate button's color when pressed
        candidate.addActionListener(new ActionListener() {
            boolean action = false;
            public void actionPerformed(ActionEvent e)
            {
                action = !action;
                if(action) 
                {
                    candidate.setBackground(Color.black);
                    candidate.setForeground(Color.white);
                    for(Component c: choices.getComponents()) c.setBackground(color);
                }
                else
                {
                    candidate.setBackground(Color.white);
                    candidate.setForeground(Color.black);
                    for(Component c: choices.getComponents()) c.setBackground(Color.white);
                }

            }
        });

        // creating the delete button
        delete = new JButton();
        delete.setText("Delete");
        delete.setBorder(BorderFactory.createLineBorder(Color.black, 3)); 
        delete.setOpaque(true);
        delete.setBackground(Color.white);

        // creating action for when the delete button is pressed
        delete.addMouseListener(new MouseListener() { public void mouseEntered(MouseEvent e){} public void mouseExited(MouseEvent e){} public void mouseClicked(MouseEvent e){}
            public void mousePressed(MouseEvent e) 
            {
                // flashing the delete button gray when pressed
                delete.setBackground(Color.gray);  

                // finding the selected tile
                for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c )	if(button[r][c].getBackground()==color)
                {
                    // putting former values in variables for the undo button
                    text.add(button[r][c].getText());
                    B.add(button[r][c]);
                    ArrayList<Integer> arr = new ArrayList<>();
                    for(int i = 0; i<6; i++) if(button[r][c].getComponent(i).isVisible()) arr.add(i);
                    canText.add(arr);

                    // editing tile
                    for(int i = 0; i<6; i++) button[r][c].getComponent(i).setVisible(false);
                    button[r][c].setText("");

                    // checking the board
                    checkBoard();
                }       
            }     
            public void mouseReleased(MouseEvent e) {
                delete.setBackground(Color.white);  }       
        });

        // creating the undo button
        undo = new JButton();
        undo.setText("Undo");
        undo.setBorder(BorderFactory.createLineBorder(Color.black, 3)); 
        undo.setOpaque(true);
        undo.setBackground(Color.white);

        // creating action for when undo button is pressed
        undo.addMouseListener(new MouseListener() { public void mouseClicked(MouseEvent e){} public void mouseEntered(MouseEvent e){} public void mouseExited(MouseEvent e){}
            public void mousePressed(MouseEvent e) 
            {
                // flashing undo button gray when pressed
                undo.setBackground(Color.gray);  
                
                // editing tile to former state
                if(B.size()>0 && canText.size()>0 && text.size()>0)
                {
                    String t = text.get(text.size()-1);
                    JButton b = B.get(B.size()-1);
                    ArrayList<Integer> c = canText.get(canText.size()-1);      

                    b.setText(t);
                    for(int i = 0; i<6; i++) b.getComponent(i).setVisible(false);
                    for(int i:c) b.getComponent(i).setVisible(true);

                    B.remove(B.size()-1);
                    text.remove(text.size()-1);
                    canText.remove(canText.size()-1);
                }

                //checking the board
                checkBoard();
            }     
            public void mouseReleased(MouseEvent e) { undo.setBackground(Color.white); }          
        });

        newb = new JButton("New");
        newb.setOpaque(true);
        newb.setBackground(Color.white);
        newb.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        newb.addMouseListener(new MouseListener() { public void mouseClicked(MouseEvent e){} public void mouseEntered(MouseEvent e){} public void mouseExited(MouseEvent e){}
            public void mousePressed(MouseEvent e) 
            {
                // flashing undo button gray when pressed
                newb.setBackground(Color.gray);  
                
                // generates a sudoku board and puts it in an 2D int array
                newBoard();

                //checking the board
                checkBoard();
            }     
            public void mouseReleased(MouseEvent e) {
                newb.setBackground(Color.white);  }          
        });


        // sizing each component and adding it to the frame

        newb.setBounds(995, 500, 100, 50);
        //frame.add(newb);

        undo.setBounds(995, 15, 75, 50);
        //frame.add(undo);

        delete.setBounds(990, 15, 75, 50);
        //frame.add(delete);

        candidate.setBounds(790, 15, 150, 50);
        //frame.add(candidate);
        
        choices.setBounds(995,80,400,400);
        //frame.add(choices);*/

        undo = new JButton("Undo");
        undo.setOpaque(true);
        undo.addMouseListener(new MouseListener(){ public void mouseClicked(java.awt.event.MouseEvent e){} public void mouseEntered(java.awt.event.MouseEvent e){} public void mouseExited(java.awt.event.MouseEvent e){}
           public void mousePressed(java.awt.event.MouseEvent e)
           {
                undo.setBackground(Color.darkGray);

                if(B.size()>0 && attributes.size()>0 && colorss.size()>0) for(int r=0; r<button.length; r++) for(int c=0; c<button.length; c++) if(button[r][c].equals(B.get(B.size()-1)))
                {
                    button[r][c].setText(attributes.get(attributes.size()-1)[0]);
                    teamName[r][c].setText(attributes.get(attributes.size()-1)[1]);
                    names[r][c].setText(attributes.get(attributes.size()-1)[2]);
                    numbers[r][c].setText(attributes.get(attributes.size()-1)[3]);
                    position[r][c].setText(attributes.get(attributes.size()-1)[4]);

                    button[r][c].setBackground(colorss.get(colorss.size()-1)[0]);
                    button[r][c].setForeground(colorss.get(colorss.size()-1)[1]);

                    button[r][c].setBorder(gold);

                    if(position[r][c].getText()=="") 
                    {
                        GridBagConstraints gbcTF = new GridBagConstraints();
                        gbcTF.gridx = 0;
                        gbcTF.gridy = 1;
                        gbcTF.weightx = 1;
                        gbcTF.weighty = 1;
                        gbcTF.fill = GridBagConstraints.BOTH;
                        gbcTF.insets = new Insets(0, 0, 10, 0);
                        gbcTF.anchor = GridBagConstraints.CENTER;
                        button[r][c].add(textfield[r][c],gbcTF);
                        textfield[r][c].setText("");
                    }
                    else button[r][c].remove(textfield[r][c]);

                    checkBoard();

                    for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=r||co!=c) if(button[ro][co].getBorder()!=red) 
                    {
                        if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                        else button[ro][co].setBorder(border);

                        if(button[r][c].getBorder() == red || button[r][c].getBorder() == redgold) button[r][c].setBorder(redgold);
                        else button[r][c].setBorder(gold);
                    }
                } 
                if(B.size()>0 && attributes.size()>0 && colorss.size()>0)
                {
                    B.remove(B.size()-1);
                    attributes.remove(attributes.size()-1);
                    colorss.remove(colorss.size()-1);
                }
           }

           public void mouseReleased(java.awt.event.MouseEvent e)
           {
                undo.setBackground(null);
           }
            
        });
        undo.setForeground(Color.black);
        undo.setBorder(border);
        undo.setBounds(1200, 150, 100, 100);
        frame.add(undo);

        delete = new JButton("Delete");
        delete.setOpaque(true);
        delete.setForeground(Color.black);
        delete.setBorder(border);
        delete.setBounds(1200,300, 100, 100);
        delete.addMouseListener(new MouseListener(){public void mouseClicked(java.awt.event.MouseEvent e){} public void mouseEntered(java.awt.event.MouseEvent e){} public void mouseExited(java.awt.event.MouseEvent e){}
            public void mousePressed(java.awt.event.MouseEvent e)
            {
                delete.setBackground(Color.gray);
                
                for(int r=0;r<button.length;r++) for(int c=0;c<button.length;c++) if(button[r][c].getBorder()==gold||button[r][c].getBorder()==redgold) if(!original[r][c]) if(position[r][c].getText()!="")
                {
                    String[] stuff = new String[] {button[r][c].getText(), teamName[r][c].getText(), names[r][c].getText(), numbers[r][c].getText(), position[r][c].getText()};
                    attributes.add(stuff);
                    Color[] color = new Color[]{button[r][c].getBackground(), button[r][c].getForeground()};
                    colorss.add(color);
                    B.add(button[r][c]);

                    button[r][c].setText(teamName[r][c].getText());
                    teamName[r][c].setText("");
                    position[r][c].setText("");
                    numbers[r][c].setText("");
                    names[r][c].setText("");
                    button[r][c].setForeground(Color.black);
                    button[r][c].setBackground(null);

                    GridBagConstraints gbcTF = new GridBagConstraints();
                    gbcTF.gridx = 0;
                    gbcTF.gridy = 1;
                    gbcTF.weightx = 1;
                    gbcTF.weighty = 1;
                    gbcTF.fill = GridBagConstraints.BOTH;
                    gbcTF.insets = new Insets(0, 0, 10, 0);
                    gbcTF.anchor = GridBagConstraints.CENTER;
                    button[r][c].add(textfield[r][c],gbcTF);
                    textfield[r][c].setText("");

                    checkBoard();

                    for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=r||co!=c) if(button[ro][co].getBorder()!=red) 
                    {
                        if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                        else button[ro][co].setBorder(border);

                        if(button[r][c].getBorder() == red || button[r][c].getBorder() == redgold) button[r][c].setBorder(redgold);
                        else button[r][c].setBorder(gold);
                    }
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent e) 
            {
                delete.setBackground(null);
            }          
        });
        frame.add(delete);

        errors.setForeground(Color.black);
        errors.setText("Errors: " + mistakes);
        errors.setBounds(1200,50, 100, 100);
        frame.add(errors);
 

        JLabel timer = new JLabel("0:00");
        timer.setForeground(Color.black);
        timer.setFont(new Font("College", Font.BOLD, 25));
        timer.setBounds(220, 0,200, 200);
        frame.add(timer);

        Timer time = new Timer();
        TimerTask task = new TimerTask() 
        {
            int t = 0;
            int min = 0;
            int sec = 0;
            public void run() 
            {
                if(shade.isVisible()) time.cancel();

                else if(sec<10) 
                {
                    timer.setText(min + ":0" + sec);
                    clock = min + ":0" + sec;
                }
                else 
                {
                    timer.setText(min + ":" + sec);
                    clock = min + ":" + sec;
                }
                t++;
                min = t/60;
                sec = t - (min*60);
            }
        };
        time.scheduleAtFixedRate(task, 0, 1000);

        JLabel title = new JLabel("SPORTDOKU");
        title.setForeground(Color.black);
        title.setFont(new Font("College", Font.BOLD, 40));
        title.setBackground(null);
        title.setBounds(620, 0,700, 100);
        frame.add(title);

        buttonPanel.setBounds(400,110, 700,700);
        frame.add(buttonPanel);

        frame.setSize( 1900,1000);
		frame.setVisible( true );
        
        //win.setVisible(false);
    }
    
    public void newBoard()
    {
        Sudoku.loadBoard();
        dBoard = Sudoku.displayBoard();

        // text = new ArrayList<String>();
        // B = new ArrayList<JButton>();
        // canText = new ArrayList<ArrayList<Integer>>();

        ArrayList<String> already = new ArrayList<String>();
        String[] teams = new String[10];
        for(int i = 1; i<10; i++) 
        {      
            Object[] keys = map.keySet().toArray();
            Random rand = new Random();
            int randomIndex = rand.nextInt(keys.length);
            Object team = keys[randomIndex];
            if(!already.contains(team))
                teams[i] = team + "";
            else
                i--;
            already.add(team+"");
        }

        // looping through each sudoku tile
        for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) 
        {	 
            button[r][c].setOpaque(true);
            button[r][c].setBorder(border);
            button[r][c].setFont(new Font("Arial", Font.BOLD, 10));

            //for(int i = 0; i<6; i++) button[r][c].getComponent(i).setVisible(false);

            final int row = r;
            final int col = c;

            String[] colors = new String[3];

            if(teams[Sudoku.board[r][c]]!=null) 
            {
                colors = map.get(teams[Sudoku.board[r][c]]);
                Color main = new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]), Integer.parseInt(colors[2]) );
                Color alt = new Color(Integer.parseInt(colors[3]),Integer.parseInt(colors[4]), Integer.parseInt(colors[5]) );
            
                button[r][c].setBackground(null);
                button[r][c].setForeground(Color.black);
                names[r][c].setBackground(main);
                names[r][c].setForeground(alt);
                numbers[r][c].setBackground(main);
                numbers[r][c].setForeground(alt);
                teamName[r][c].setBackground(main);
                teamName[r][c].setForeground(alt);
                position[r][c].setBackground(main);
                position[r][c].setForeground(alt);

                button[r][c].setText(teams[Sudoku.board[r][c]]);
                button[r][c].setVerticalAlignment(SwingConstants.BOTTOM);
                //teamName[r][c].setText(teams[Sudoku.board[r][c]]);
            }            
        } 

        int empties = 0;
        while (empties<30)
        {
            int roww = (int)(Math.random()*6)+0;
            int coll = (int)(Math.random()*6)+0;
            while (roww == 0 && coll == 0)
            {
                roww = (int)(Math.random()*6)+0;
                coll = (int)(Math.random()*6)+0;
            }
            int count = 0;
            boolean check = false;
            int overall = 80;
            while(!check)
            {
                if(count>10) overall = 70;
                String team = button[roww][coll].getText();
                String[] play = new String[] {"bob","mob", "fart"};
                String[] info = new String[] {"bob","mob", "fart", "81"};
                while(!info[2].equals(team)|| info[2].equals("fart") || info[0].equals("LS") || Integer.parseInt(info[3])<overall || info[0].equals("P"))
                {
                    int randindex = (int) (Math.random()*randomnames.size());
                    play = randomnames.get(randindex);
                    info = player.get(play[2]);
                    if(info==null) System.out.println(play[2]);
                }
                button[roww][coll].remove(textfield[roww][coll]);
                String[] colors = new String[3];
                colors = map.get(teams[Sudoku.board[roww][coll]]);
                button[roww][coll].setBackground(new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
                names[roww][coll].setText(play[1].toUpperCase());
                numbers[roww][coll].setText(info[1]);
                teamName[roww][coll].setText(info[2]);
                position[roww][coll].setText(info[0]);
               // button[roww][coll].setText("");
                check = checkBoard();
                count++;
            }
            // button[roww][coll].setText(name[1] + "\n" + player.get(play+"")[1] + "\n" + player.get(play+"")[0] + "\n" + player.get(play+"")[2]);
            empties++;
            original[roww][coll] = true;
        }
        
        for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) 
        {
            if(numbers[r][c].getText()!="")
            {
                button[r][c].setText("");
                button[r][c].setBorder(border);
            }
        }

        // int loop = 0;
        // if(!checkBoard()) 
        // {
        //     loop++;
        //     System.out.println("Looping: " + loop);
        //     newBoard();
        // }
    }

    // editing tile when the delete key is pressed
    public class key implements KeyListener
    {
        public void keyPressed(KeyEvent e) 
        { 
            if(e.getKeyCode() == 8) for( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if(button[r][c].getBorder()==gold || button[r][c].getBorder()==redgold) if(!original[r][c]) if(position[r][c].getText()!="")
            {
                String[] stuff = new String[] {button[r][c].getText(), teamName[r][c].getText(), names[r][c].getText(), numbers[r][c].getText(), position[r][c].getText()};
                attributes.add(stuff);
                Color[] color = new Color[]{button[r][c].getBackground(), button[r][c].getForeground()};
                colorss.add(color);
                B.add(button[r][c]);

                button[r][c].setText(teamName[r][c].getText());   
                button[r][c].setBackground(null);

                GridBagConstraints gbcTF = new GridBagConstraints();
                gbcTF.gridx = 0;
                gbcTF.gridy = 1;
                gbcTF.weightx = 1;
                gbcTF.weighty = 1;
                gbcTF.fill = GridBagConstraints.BOTH;
                gbcTF.insets = new Insets(0, 0, 10, 0);
                gbcTF.anchor = GridBagConstraints.CENTER;
                button[r][c].add(textfield[r][c],gbcTF);

                teamName[r][c].setText("");
                names[r][c].setText("");
                numbers[r][c].setText("");
                position[r][c].setText("");  
                button[r][c].setBorder(border);
                textfield[r][c].setText("");

                checkBoard(); 

                for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=r||co!=c) if(button[ro][co].getBorder()!=red) 
                {
                    if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                    else button[ro][co].setBorder(border);

                    if(button[r][c].getBorder() == red || button[r][c].getBorder() == redgold) button[r][c].setBorder(redgold);
                    else button[r][c].setBorder(gold);
                }         
            
                checkBoard();
                
            }

            if(e.getKeyCode()==37) for ( int r=0 ; r < button.length ; ++r ) for ( int c=1 ; c < button.length ; ++c ) if (button[r][c].getBorder()==gold||button[r][c].getBorder()==redgold)
            {
                JButton JB = button[r][c-1];

                for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=r||co!=(c-1)) if(button[ro][co].getBorder()!=red) 
                {
                    if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                    else button[ro][co].setBorder(border);

                    if(JB.getBorder() == red || JB.getBorder() == redgold) JB.setBorder(redgold);
                    else JB.setBorder(gold);
                }     

                return;
            }

            if(e.getKeyCode()==38) for ( int r=1 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if (button[r][c].getBorder()==gold||button[r][c].getBorder()==redgold)
            {
                JButton JB = button[r-1][c];
                for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=(r-1)||co!=c) if(button[ro][co].getBorder()!=red) 
                {
                    if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                    else button[ro][co].setBorder(border);

                    if(JB.getBorder() == red || JB.getBorder() == redgold) JB.setBorder(redgold);
                    else JB.setBorder(gold);
                }     
                return;
            }

            if(e.getKeyCode()==39) for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length-1 ; ++c ) if (button[r][c].getBorder()==gold||button[r][c].getBorder()==redgold)
            {
                JButton JB = button[r][c+1];
                for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=r||co!=(c+1)) if(button[ro][co].getBorder()!=red) 
                {
                    if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                    else button[ro][co].setBorder(border);

                    if(JB.getBorder() == red || JB.getBorder() == redgold) JB.setBorder(redgold);
                    else JB.setBorder(gold);
                }     
                return;
            }

            if(e.getKeyCode()==40) for ( int r=0 ; r < button.length-1 ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if(button[r][c].getBorder()==gold||button[r][c].getBorder()==redgold)
            {
                JButton JB = button[r+1][c];
                for ( int ro=0 ; ro < button.length ; ++ro ) for ( int co=0 ; co < button.length ; ++co ) if(ro!=(r+1)||co!=(c)) if(button[ro][co].getBorder()!=red) 
                {
                    if(button[ro][co].getBorder()==redgold) button[ro][co].setBorder(red);
                    else button[ro][co].setBorder(border);

                    if(JB.getBorder() == red || JB.getBorder() == redgold) JB.setBorder(redgold);
                    else JB.setBorder(gold);
                }     
                return;
            }
        } 
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }

    public boolean checkBoard()
    {
        boolean check = true;
        for ( int rob=0 ; rob < 6 ; ++rob ) for ( int cob=0 ; cob < 6 ; ++cob ) if(position[rob][cob]!=null) if(position[rob][cob].getText()!="")
        {
            int redd = 0;
            for ( int r=0 ; r < 6 ; ++r ) for ( int c=0 ; c < 6 ; ++c ) if((r/3==rob/3 && c/3==cob/3) && (rob!=r && cob!=c))
            {
                if(position[rob][cob].getText().equals(position[r][c].getText()))
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red); 
                    redd++;
                    check = false;
                    break;
                }
                if(numbers[rob][cob].getText().equals(numbers[r][c].getText()))
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red);
                    redd++;
                    check = false;
                    break;
                }
            }
               
            if(redd>0) continue;
            for(int num = 0; num<6; num++)
            {
                if(position[rob][cob].getText().equals(position[rob][num].getText()) && cob!=num)
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red);
                    redd++;
                    check = false;
                    break;
                }                                                                                                          
                if(position[rob][cob].getText().equals(position[num][cob].getText()) && rob!=num) 
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red);
                    redd++;
                    check = false;
                    break;
                }   
                if(numbers[rob][cob].getText().equals(numbers[rob][num].getText()) && cob!=num)
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red);   
                    redd++;
                    check = false;
                    break;
                }    
                if(numbers[rob][cob].getText().equals(numbers[num][cob].getText()) && rob!=num) 
                {
                    if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                    else button[rob][cob].setBorder(red);
                    redd++;
                    check = false;
                    break;
                }      
            
                if(redd>0) continue;
                for ( int r=0 ; r < button.length ; ++r ) for ( int c=0 ; c < button.length ; ++c ) if(r!=rob || c!= cob)
                {
                    if(numbers[rob][cob].getText().equals(numbers[r][c].getText()) && teamName[rob][cob].getText().equals(teamName[r][c].getText()))
                    {
                        if(button[rob][cob].getBorder()==gold) button[rob][cob].setBorder(redgold); 
                        else button[rob][cob].setBorder(red);
                        redd++;
                        check = false;
                        break;
                    }   
                }                                    
            }
            if(redd>0) continue;
            if(button[rob][cob].getBorder()==redgold || button[rob][cob].getBorder()==gold ) button[rob][cob].setBorder(gold);
            else button[rob][cob].setBorder(border);
        }  
        else 
        {
            if(button[rob][cob].getBorder()==redgold || button[rob][cob].getBorder()==gold ) button[rob][cob].setBorder(gold);
            else button[rob][cob].setBorder(border); 
        }     
        return check;                                                  
    }   


    public static void main(String[] args) throws IOException 
    {
        new SportdokuGUI();
    }
}