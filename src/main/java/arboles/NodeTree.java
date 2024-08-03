/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author JAVIER
 */
public class NodeTree<E> {
    private E content;
    private LinkedList<Tree<E>> childrens;

    public NodeTree() {
    }

    public NodeTree(E content) {
        this.content = content;
        this.childrens = new LinkedList<>();
    }

    public E getContent() {
        return content;
    }

    public LinkedList<Tree<E>> getChildrens() {
        return childrens;
    }

    public void setChildrens(LinkedList<Tree<E>> childrens) {
        this.childrens = childrens;
    }
    
    
}
