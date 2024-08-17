/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyectosegundop;

/**
 *
 * @author Steven Morocho
 */
public class Nodo<E> {
    private String pregunta;
    private Nodo<E> si;
    private Nodo<E> no;
    
    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.si = null;
        this.no = null;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Nodo<E> getSi() {
        return si;
    }

    public void setSi(Nodo<E> si) {
        this.si = si;
    }

    public Nodo<E> getNo() {
        return no;
    }

    public void setNo(Nodo<E> no) {
        this.no = no;
    }
    
    
}
