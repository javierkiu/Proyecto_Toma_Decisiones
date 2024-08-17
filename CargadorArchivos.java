/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyectosegundop;

import java.io.*;
import java.util.*;

/**
 *
 * @author Steven Morocho
 */
public class CargadorArchivos {
    public static List<String> cargarPreguntas(String archivo) throws IOException {
        List<String> preguntas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = reader.readLine()) != null) {
            preguntas.add(linea);
        }
        reader.close();
        return preguntas;
    }

    public static Map<String, List<String>> cargarRespuestas(String archivo) throws IOException {
        Map<String, List<String>> respuestas = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(" ");
            String animal = partes[0];
            List<String> resp = Arrays.asList(partes).subList(1, partes.length);
            respuestas.put(animal, resp);
        }
        reader.close();
        return respuestas;
    }
    
    public static ArbolDesicion<String> construirArbol(List<String> preguntas, Map<String, List<String>> respuestas) {
        ArbolDesicion arbol = new ArbolDesicion();
        Nodo raiz = new Nodo(preguntas.get(0));
        arbol.setRoot(raiz);

        for (Map.Entry<String, List<String>> entrada : respuestas.entrySet()) {
            String animal = entrada.getKey();
            List<String> resp = entrada.getValue();
            Nodo actual = raiz;

            for (int i = 0; i < resp.size(); i++) {
                boolean respuesta = resp.get(i).equals("si");
                if (i == resp.size() - 1) {
                    if (respuesta) {
                        actual.setSi(new Nodo(animal));
                    } 
                    else {
                        actual.setNo(new Nodo(animal));
                    }
                } 
                else {
                    if (respuesta) {
                        if (actual.getSi() == null) {
                            actual.setSi(new Nodo(preguntas.get(i + 1)));
                        }
                        actual = actual.getSi();
                    } else {
                        if (actual.getNo() == null) {
                            actual.setNo(new Nodo(preguntas.get(i + 1)));
                        }
                        actual = actual.getNo();
                    }
                }
            }
        }
        return arbol;
    }
    
}
