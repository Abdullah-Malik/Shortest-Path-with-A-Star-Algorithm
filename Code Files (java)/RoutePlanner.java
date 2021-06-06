/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author Abdullah
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RoutePlanner extends javax.swing.JFrame {
    
    // to choose start, goat and hurdles
    int size = 20;
    int rows=size, cols = size;
    private int arr[][] = new int[size][size];
    int i = 0;
    
    int startX = -1;
    int startY = -1;
    int endX = -1;
    int endY = -1;
    
    public JButton button[][] = new JButton[size][size];
    int selections = 0;
    ArrayList<Node> openlist = new ArrayList<>(); //frnge
    ArrayList<Node> closelist = new ArrayList<>();
    Node start = new Node();
    Node goal= new Node();
    /**
     * Creates new form RoutePlanner
     */
    public RoutePlanner() {
        initComponents();
        JPanel Panel=new JPanel((new FlowLayout(FlowLayout.CENTER, 0, 0)));  
        Panel.setBounds(20,20,500,500); 
        for(int i =0 ; i < size ; i++)
        {
            for(int j=0 ; j < size ; j++)
            {
                arr[i][j] = 1;
                button[i][j] = new JButton();
                
                button[i][j].addMouseListener(new MouseHandler(i, j));
                button[i][j].setBackground(Color.white);
                button[i][j].setPreferredSize(new Dimension(25, 25));
                //blocks[i][j].setSize(jPanel1.getWidth()/wid, jPanel1.getHeight()/hei);
                Panel.add(button[i][j]);
            }
        }
        Random rand = new Random();
        for(int i = 0 ; i < 100 ; i++)
        {
            int x = rand.nextInt(20);
            int y= rand.nextInt(20);
            arr[x][y] = -1;
            button[x][y].setBackground(Color.black);
            
        }
        
        
        this.setSize(650, 600);
        Panel.setBackground(Color.black);  

        this.add(Panel);
    }

    double calculateHStar(int x1, int y1, int x2, int y2){
        double deltaX = (x1 - x2)*(x1 - x2);
        double deltaY = (y1 - y2)*(y1 - y2);
        return sqrt(deltaX + deltaY);
    }
    
    void colorButtons(Node node)
    {
        
        while(true){
            if(node.getPostion().x == start.getPostion().x && node.getPostion().y == start.getPostion().y)
                break;
            else
            {
                int xCordParent = node.getParent().x;
                int yCordParent = node.getParent().y;
                System.out.println(xCordParent + " "+ yCordParent);
                button[xCordParent][yCordParent].setBackground(Color.yellow);

                for(int i =0 ; i < closelist.size() ; i++){
                    if(closelist.get(i).getPostion().x == xCordParent && closelist.get(i).getPostion().y == yCordParent ){
                        node = closelist.get(i);
                        break;
                    }

                }
            }
        
        }
    }
    public void findroute(){
        if (openlist.isEmpty())
            return;
        else
        {
            Node node = openlist.remove(0);
            
            if (node.getPostion().x == goal.getPostion().x && node.getPostion().y == goal.getPostion().y){
                System.out.println("Path found");
                colorButtons(node);
                return ;
            }
            else{
                boolean found = false;
                for(int l = 0 ; l < closelist.size(); l++){
                   if(closelist.get(l).getPostion().x == node.getPostion().x && closelist.get(l).getPostion().y == node.getPostion().y){
                         found = true;
                    }   
                }
                if(found == false)
                {
                    button[node.postion.x][node.postion.y].setBackground(Color.red);
                    closelist.add(node);
                    findChildrenNodes(node);
                }
                 findroute();
            
            }
            
        }
    
        
    }
    
    public void findRoute(Node start, Node end){
        
        openlist.add(start);
        findroute();
    }
    
    void sortNodes(){
      for (int i = 0; i < openlist.size()-1; i++){  
            for (int j = 0; j < openlist.size()-i-1; j++)  {
                if (openlist.get(j).getfStar() > openlist.get(j+1).getfStar()){
                    
                    Node temp = openlist.get(j);
                    openlist.set(j, openlist.get(j+1)) ;
                    openlist.set(j+1, temp) ;
                     
                }

            } 
      }
      
    }
    
    boolean validArrayIndex(int i, int j)
    {
        if(i >= 0 && i < size && j >=0 && j < size)
        {
           return true;
        }
        return false;
    }
    boolean validPath(int i, int j)
    {
        if(validArrayIndex(i-1, j) && validArrayIndex(i, j-1))
        {
            if(arr[i-1][j] == -1 && arr[i][j-1] == -1)
                return false;
        }
        if(validArrayIndex(i, j+1) && validArrayIndex(i-1, j))
        {
            if(arr[i][j+1] == -1 && arr[i-1][j] == -1)
                return false;
        }
        if(validArrayIndex(i+1, j) && validArrayIndex(i, j-1))
        {
            if(arr[i+1][j] == -1 && arr[i][j-1] == -1)
                return false;
        }
        if(validArrayIndex(i+1, j) && validArrayIndex(i, j+1))
        {
            if(arr[i+1][j] == -1 && arr[i][j+1] == -1)
                return false;
        }
        return true;
    }
    void findChildrenNodes(Node node){
        
        for(int i = -1 ; i <= 1 ; i++){
            for(int j = -1 ; j <= 1 ; j++){
                if(node.getPostion().x+i >= 0 && node.getPostion().y + j >= 0 && node.getPostion().x+i < rows && node.getPostion().y+j < cols  ){
                    
                    if(i == j && i == 0)
                    {
                        //skip for same node
                    }
                    /*else if(!validPath(node.getPostion().x+i, node.getPostion().y + j))
                    {
                        ;
                    }*/
                    else{                   
                    Node temp = new Node();
                    temp.postion.x = node.getPostion().x+i;
                    temp.postion.y = node.getPostion().y+j;
                    System.out.println(temp.postion.x + "  " + temp.postion.y);
                    if( arr[temp.getPostion().x][temp.getPostion().y] != -1  ){
                        boolean found = false;
                        for(int l = 0 ; l < closelist.size(); l++){
                           if(closelist.get(l).getPostion().x == temp.getPostion().x && closelist.get(l).getPostion().y == temp.getPostion().y){
                                 found = true;
                            }   
                        }
                        if(found == false){
                        Node child = new Node();
                        Cord childPostion = new Cord();
                        childPostion.x= temp.getPostion().x;
                        childPostion.y = temp.getPostion().y;
                        child.Parent = node.postion;
                        child.postion= childPostion;
                        child.hStar =  calculateHStar(childPostion.x, childPostion.y, goal.postion.x, goal.postion.y);
                        child.gStar = calculateHStar(childPostion.x, childPostion.y, start.postion.x, start.postion.y);;
                        child.fStar=child.hStar+child.gStar;
                        openlist.add(child);
                        sortNodes();
   
                    }
                    }
                    }

                }

            }
        }
    }
    
    private class MouseHandler extends MouseAdapter {

        public int r, c; // instance variables
        
        public MouseHandler(int r, int c) {
            this.r = r;
            this.c = c;
        }
        
        public void mouseClicked(MouseEvent e) {
            System.out.println(r+" "+c);
            if(selections == 0){
                arr[r][c] = 0;
                if(startX == -1 && startY == -1)
                {
                    start.postion.x = r;
                    start.postion.y = c;
                    startX =r;
                    startY =c;
                    button[r][c].setBackground(Color.cyan);
                }
                else
                {
                    button[startX][startY].setBackground(Color.white);
                    start.postion.x = r;
                    start.postion.y = c;
                    startX =r;
                    startY =c;
                    button[r][c].setBackground(Color.cyan);
                }
                
            
            }
            else if(selections == 1){
                arr[r][c] = 0;
                if(endX == -1 && endY == -1)
                {
                    goal.postion.x = r;
                    goal.postion.y = c;
                    endX =r;
                    endY =c;
                    button[r][c].setBackground(Color.orange);
                }
                else
                {
                    button[endX][endY].setBackground(Color.white);
                    goal.postion.x = r;
                    goal.postion.y = c;
                    endX =r;
                    endY =c;
                    button[r][c].setBackground(Color.orange);
                }
                
               
            }
            else{
                //squares[r][c].setEnabled(false);
                arr[r][c] = -1;
                button[r][c].setBackground(Color.black);
                //selections++;
            }          
        }  
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Goal = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Goal.setText("Set Goal");
        Goal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GoalMouseClicked(evt);
            }
        });

        jButton1.setText("Set Start");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Find Path");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Obstacles");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(511, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Goal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(Goal)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GoalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GoalMouseClicked
        // TODO add your handling code here:
        selections = 1;
    }//GEN-LAST:event_GoalMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        selections = 0;
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        openlist.clear();
        closelist.clear();
        findRoute(start , goal);
        /*for(int i = 0 ; i < closelist.size();i++){
            System.out.println(closelist.get(i).current.x + " " + closelist.get(i).current.y);
        }*/
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        selections = -1;
    }//GEN-LAST:event_jButton3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoutePlanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoutePlanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoutePlanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoutePlanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoutePlanner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Goal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}
