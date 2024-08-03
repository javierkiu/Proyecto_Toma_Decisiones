/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import arboles.BinaryNode;
import arboles.BinaryTree;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author JAVIER
 */
public class Utilidades {
    public static List<String> leerTxt(String filename){
        List<String> list = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = bf.readLine())!=null){
                list.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public static <E> BinaryTree<E> crearArbol(List<E> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        BinaryTree<E> tree = new BinaryTree<>();
        tree.setRoot(buildTreeRecursive(elements, 0)); 
        return tree;
    }

    private static <E> BinaryNode<E> buildTreeRecursive(List<E> elements, int level) {
        if (level >= elements.size()) {
            return null;
        }
        BinaryNode<E> node = new BinaryNode<>(elements.get(level));
        node.setLeft(new BinaryTree<>(elements.get(level)));
        node.setRight(new BinaryTree<>(elements.get(level)));
        node.getLeft().setRoot(buildTreeRecursive(elements, level + 1));
        node.getRight().setRoot(buildTreeRecursive(elements, level + 1));
        return node;
    }
}
