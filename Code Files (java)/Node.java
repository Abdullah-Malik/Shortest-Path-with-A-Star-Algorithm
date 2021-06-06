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
public class Node {
    Cord postion;
    Cord Parent;
    double gStar;
    double hStar;
    double fStar;

    public Cord getPostion() {
        return postion;
    }

    public void setPostion(Cord postion) {
        this.postion = postion;
    }

    public Cord getParent() {
        return Parent;
    }

    public void setParent(Cord Parent) {
        this.Parent = Parent;
    }

    public double getgStar() {
        return gStar;
    }

    public void setgStar(float gStar) {
        this.gStar = gStar;
    }

    public double gethStar() {
        return hStar;
    }

    public void sethStar(float hStar) {
        this.hStar = hStar;
    }

    public double getfStar() {
        return fStar;
    }

    public void setfStar(float fStar) {
        this.fStar = fStar;
    }
    
    
    Node()
    {
        postion = new Cord();
        Parent = new Cord();
    }
}
