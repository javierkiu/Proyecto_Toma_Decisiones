/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyectosegundop;

/**
 *
 * @author Steven Morocho
 */
public class ArbolDecision {
    private Nodo<String> raiz;

    public ArbolDecision() {
        this.raiz = null;
    }

    void insertar(String pregunta, boolean respuesta, Nodo<String> padre) {
        Nodo<String> nuevoNodo = new Nodo(pregunta);
        if (respuesta) {
            padre.setSi(nuevoNodo);
        } else {
            padre.setNo(nuevoNodo);
        }
    }

    public Nodo<String> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<String> raiz) {
        this.raiz = raiz;
    }
    
    
}
