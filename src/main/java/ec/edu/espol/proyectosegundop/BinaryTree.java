/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyectosegundop;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 *
 * @author Steven Morocho
 */
public class BinaryTree<E> {
    private NodeBinaryTree<E> root;

    public BinaryTree() {
        this.root=null;
    }
    
    public BinaryTree(NodeBinaryTree<E> root) {
        this.root=root;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public NodeBinaryTree<E> getRoot() {
        return root;
    }

    public void setRoot(NodeBinaryTree<E> root) {
        this.root = root;
    }
    
    public void recorrerPreorden(){
        if (!this.isEmpty()) {   
            // 1. Imprimir a la raiz
            System.out.println(this.root.getContent());
            
            // 2. recorrer preorden en hijo izquierdo
            if (root.getLeft()!=null) {
                root.getLeft().recorrerPreorden(); 
            }
        
            // 3. recorrer preorden en hijo derecho
            if (root.getRight()!=null) {
                root.getRight().recorrerPreorden(); 
            }
        }
    }
    
    public void recorrerEnorden(){
        
        if (!this.isEmpty()){
            
            // 1. recorrer enorden en hijo izquierdo
            if (root.getLeft()!=null){
                root.getLeft().recorrerEnorden(); 
            }
        
            // 2. Imprimir a la raiz
            System.out.println(this.root.getContent());
            
            // 3. recorrer enorden en hijo derecho
            if (root.getRight()!=null){
                root.getRight().recorrerEnorden(); 
            }
            
        }
    }
    
    public void recorrerPostorden(){
        
        if (!this.isEmpty()){
            
            // 1. recorrer postorden en hijo izquierdo
            if (root.getLeft()!=null){
                root.getLeft().recorrerPostorden(); 
            }
        
            // 2. recorrer postorden en hijo derecho
            if (root.getRight()!=null){
                root.getRight().recorrerPostorden(); 
            }
            
            // 3. Imprimir a la raiz
            System.out.println(this.root.getContent());
        }
    }
    
    public boolean isLeaf(){
        if (!this.isEmpty()){
            return root.getLeft() == null && root.getRight() == null;
        }
        return false;
    }
    
    public int countLeavesRecursive(){
        // Caso base 1: Árbol vacío
        if(this.isEmpty()){
            return 0;
        }
        // Caso base 2: El árbol es una hoja
        else if (this.isLeaf()){
            return 1;
        }
        else {
            int leftLeaves = 0;
            int rightLeaves=0;
            
            if (this.root.getLeft() != null){
                leftLeaves = this.root.getLeft().countLeavesRecursive();
            }
            
            if (this.root.getRight() != null){
                rightLeaves = this.root.getRight().countLeavesRecursive();
            }         
            return leftLeaves + rightLeaves;
        }  
    }
    
    public int countLeavesIterative() {
        int leaves=0;
        // Pila que almacenará los elementos del árbol que no han sido visitados
        Stack<BinaryTree<E>> s = new Stack();       
        if (!this.isEmpty()){
            s.push(this);     
            while (!s.isEmpty()){                
                BinaryTree<E> tree = s.pop();             
                if (tree.isLeaf()){
                    leaves++;
                }               
                if (tree.getRoot().getLeft() != null){
                    s.push(tree.getRoot().getLeft());
                }
                if (tree.getRoot().getRight() != null){
                    s.push(tree.getRoot().getRight());
                }    
            }   
        }
        return leaves;
    }
    
    //Implementacion recursiva
    public int countDescendantsRecursive() {
        if (this.isEmpty()) {
            return 0;
        }
        return countDescendants(root) - 1; // Restar 1 para no contar la raíz
    }
    private int countDescendants(NodeBinaryTree<E> node) {
        if (node == null) {
            return 0;
        }
        int leftCount = 0;
        int rightCount = 0;

        if (node.getLeft() != null) {
            leftCount = countDescendants(node.getLeft().getRoot());
        }
        if (node.getRight() != null) {
            rightCount = countDescendants(node.getRight().getRoot());
        }
        return 1 + leftCount + rightCount;
    }
    
    
    public NodeBinaryTree<E> findParentRecursive(NodeBinaryTree<E> nodo){
        if(isEmpty()||nodo==root)
            return null;
        return findParent(root,nodo);
    }
    private NodeBinaryTree<E>findParent(NodeBinaryTree<E> actual, NodeBinaryTree<E> objetivo){
        if(actual==null)
            return null;
        if((actual.getLeft()!=null && actual.getLeft().getRoot()==objetivo)||(actual.getRight()!=null && actual.getRight().getRoot()==objetivo))
            return actual;
        NodeBinaryTree<E> respuesta= findParent(actual.getLeft()!=null? actual.getLeft().getRoot() : null, objetivo);
        if(respuesta==null)
            respuesta=findParent(actual.getRight() != null ? actual.getRight().getRoot() : null, objetivo);
        return respuesta;
    }
    
    
    public int countLevelsRecursive(){
        if(this.isEmpty())
            return 0;
        int leftLevel=0;
        if(this.getRoot().getLeft()!=null)
            leftLevel=this.getRoot().getLeft().countLevelsRecursive();
        int rightLevel=0;
        if(this.getRoot().getRight()!=null)
            rightLevel=this.getRoot().getRight().countLevelsRecursive();
        return 1+Math.max(leftLevel, rightLevel);
    }
    
    
    public boolean isLeftyRecursive(){
        if(isEmpty()||isLeaf())
            return true;
        BinaryTree<E> izquierda=this.getRoot().getLeft();
        BinaryTree<E> derecha=this.getRoot().getRight();
        return (izquierda.isLeftyRecursive()&& derecha.isLeftyRecursive())&&(izquierda.countDescendantsRecursive()>derecha.countDescendantsRecursive());
    }
    
    
    public boolean isIdenticalRecursive(BinaryTree<E> segundo){
        if(this.isEmpty() && segundo.isEmpty())
            return true;
        else if((this.isLeaf() && segundo.isLeaf())&&(this.getRoot().getContent().equals(segundo.getRoot().getContent())))
            return true;
        else if(this.getRoot().getContent().equals(segundo.getRoot().getContent())){
            if((this.getRoot().getLeft()!=null && segundo.getRoot().getLeft()!=null)&&(this.getRoot().getRight()!=null && segundo.getRoot().getRight()!=null)){
            boolean left=this.getRoot().getLeft().isIdenticalRecursive(segundo.getRoot().getLeft());
            boolean right=this.getRoot().getRight().isIdenticalRecursive(segundo.getRoot().getRight());
            return left && right;
            }
        }
        return false;
    }
   
    
    public void largestValueOfEachLevelRecursive() {
        List<Integer> maxValues = new ArrayList<>();
        largestValueOfEachLevelHelper(this.root, 0, maxValues);
        for (int value : maxValues) {
            System.out.print(value);
            System.out.print(" ");
        }
    }
    private void largestValueOfEachLevelHelper(NodeBinaryTree<E> node, int level, List<Integer> maxValues) {
        if (node == null) {
            return;
        }
        if (level == maxValues.size()) {
            maxValues.add((Integer) node.getContent());
        } else {
            maxValues.set(level, Math.max(maxValues.get(level), (Integer) node.getContent()));
        }
        largestValueOfEachLevelHelper(node.getLeft() != null ? node.getLeft().getRoot() : null, level + 1, maxValues);
        largestValueOfEachLevelHelper(node.getRight() != null ? node.getRight().getRoot() : null, level + 1, maxValues);
    }
    
    
    public int countNodesWithOnlyChildRecursive() {
        if (this.isEmpty()) {
            return 0;
        }
        int count = 0;
        if ((this.getRoot().getLeft() == null && this.getRoot().getRight() != null) || (this.getRoot().getLeft() != null && this.getRoot().getRight() == null)) {
            count = 1;
        }
        int leftCount = 0;
        if (this.getRoot().getLeft() != null) {
            leftCount = this.getRoot().getLeft().countNodesWithOnlyChildRecursive();
        }
        int rightCount = 0;
        if (this.getRoot().getRight() != null) {
            rightCount = this.getRoot().getRight().countNodesWithOnlyChildRecursive();
        }
        return count + leftCount + rightCount;
    }
    
    
    public boolean isHeightBalancedRecursive() {
        return isHeightBalancedHelper(this.root) != -1;
    }
    private int isHeightBalancedHelper(NodeBinaryTree<E> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = isHeightBalancedHelper(node.getLeft() != null ? node.getLeft().getRoot() : null);
        int rightHeight = isHeightBalancedHelper(node.getRight() != null ? node.getRight().getRoot() : null);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // No está balanceado
        }
        return Math.max(leftHeight, rightHeight) + 1;  // Retorna la altura del árbol
    }
}
