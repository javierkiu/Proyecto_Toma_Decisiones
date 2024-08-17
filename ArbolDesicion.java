/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyectosegundop;

/**
 *
 * @author Steven Morocho
 */
public class ArbolDesicion<E> {
    private Nodo<E> root;
    
    public ArbolDesicion() {
        this.root=null;
    }
    
    public ArbolDesicion(Nodo<E> root) {
        this.root=root;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public Nodo<E> getRoot() {
        return root;
    }

    public void setRoot(Nodo<E> root) {
        this.root = root;
    }
}
