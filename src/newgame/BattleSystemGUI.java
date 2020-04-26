/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgame;

import java.awt.Font;
import static newgame.Main.currEnemy;
import static newgame.Main.moveableChar;
import static newgame.Main.LUCIA_INSTANCE;
import static newgame.Main.ROURKE_INSTANCE;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Jaren Taylor
 */
public class BattleSystemGUI extends JFrame {

    JLabel LSig1, LSig2, LSig3, LSig4, RSig1, RSig2, RSig3, RSig4, basicAtk, basicDef, basicRun;
    CastMembers target;
    JButton Run, Attack, Defend, Signature;//basic commands buttons
    JButton Lucretia, Sacrifice, RushDown, Perpetuation;//Lucias Signatures
    JButton Solemn, Expulsion, Cast, TheEnd;//Rourkes Signatures;

    public BattleSystemGUI(String title) {
        super(title);
        setSize(708, 434);
        setLayout(null);

        //Creating the components, windows and labels and stuff.
        LSig1 = new JLabel("RushDown");
        LSig2 = new JLabel("Lucretia");
        LSig3 = new JLabel("Perpetuation");
        LSig4 = new JLabel("Sacrifice");
        RSig1 = new JLabel("Solemn");
        RSig2 = new JLabel("Expulsion");
        RSig3 = new JLabel("Cast");
        RSig4 = new JLabel("The End");
        basicAtk = new JLabel("Attack");
        basicRun = new JLabel("Run");
        basicDef = new JLabel("Defend");
        
        //basic command Buttons
        Attack = new JButton("Attack");
        Defend=new JButton("Defend");
        Run= new JButton("Run");
        Signature = new JButton("Signature");

        //Lucias Buttons
        Lucretia = new JButton("Lucretia");
        Sacrifice = new JButton("Sacrifice");
        RushDown = new JButton("RushDown");
        Perpetuation = new JButton("Perpetuation");

        //Rourkes Buttons
        Solemn = new JButton("Solemn");
        Expulsion = new JButton("Expulsion");
        Cast = new JButton("Cast");
        TheEnd = new JButton("TheEnd");
        
        //Specifications for the window
        Attack.setBounds(0, 0, 75, 15);
        Attack.setFont(new Font("Times New Roman", Font.BOLD, 12));
        Defend.setBounds(100, 0, 150, 15);
        Defend.setFont(new Font("Times New Roman", Font.BOLD, 12));
        Run.setBounds(300, 0, 75, 15);
        Run.setFont(new Font("Times New Roman", Font.BOLD, 12));
        Signature.setBounds(400, 0, 75, 15);
        Signature.setFont(new Font("Times New Roman", Font.BOLD, 12));
        Attack.setToolTipText("Standard Physical attack");
        Defend.setToolTipText("Boosts Defenses for one turn");
        Run.setToolTipText("Escapes the battle");
        Signature.setToolTipText("'s special moves");
        

        //setting locations and stuff
        //Basic Commands
        //Lucretia Signature Moves event code
        Attack.addActionListener(new AttackClickHandler());
        Defend.addActionListener(new DefendClickHandler());
        Run.addActionListener(new RunClickHandler());
        Signature.addActionListener(new SignatureClickHandler());

        //Lucretia Signature Moves event code
        RushDown.addActionListener(new RushDownClickHandler());
        Lucretia.addActionListener(new LucretiaClickHandler());
        Perpetuation.addActionListener(new PerpetuationClickHandler());
        Sacrifice.addActionListener(new SacrificeClickHandler());

        //Rourke Signature Moves event code
        Solemn.addActionListener(new SolemnClickHandler());
        Expulsion.addActionListener(new ExpulsionClickHandler());
        Cast.addActionListener(new CastClickHandler());
        TheEnd.addActionListener(new TheEndClickHandler());

        //Add the these pieces to the actual code to run
        add(RushDown);
        add(Lucretia);
        add(Perpetuation);
        add(Sacrifice);
        add(Solemn);
        add(Expulsion);
        add(Cast);
        add(TheEnd);
        add(Attack);
        add(Defend);
        add(Run);
        add(Signature);
    }
    //classes for attack Listeners

    public class AttackClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            CastMembers.physAtk(moveableChar, currEnemy);

        }
    }

    public class DefendClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.defend(moveableChar);

        }
    }

    public class RunClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    public class SignatureClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    public class RushDownClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.rushDown(target);

        }

    }

    public class LucretiaClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.Lucretia(target);

        }
    }

    public class PerpetuationClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.Perpetuation(target);

        }
    }

    public class SacrificeClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.Sacrifice(target);

        }
    }

    public class SolemnClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.Solemn(target);
        }
    }

    public class ExpulsionClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //implement a function that will pass a chosen, clicked target as a parameter, so it needs to return the Name field of a CastMembers object
            CastMembers.Expulsion(target, target);

        }
    }

    public class CastClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.cast(target);

        }
    }

    public class TheEndClickHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CastMembers.TheEnd(target);
        }
    }

}
