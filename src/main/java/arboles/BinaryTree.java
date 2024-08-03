/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.Objects;
import java.util.Stack;

/**
 *
 * @author JAVIER
 */
public class BinaryTree<E> {
    private BinaryNode<E> root;

    public BinaryTree() {
        
    }
    
    public void setLeft(BinaryTree<E> content){
        if(!isEmpty()){
            root.setLeft(content);
        }
    }
    public void setRight(BinaryTree<E> content){
        if(!isEmpty()){
            root.setRight(content);
        }
    }
    public BinaryTree<E> getLeft(){
        if(!isEmpty()){
            return root.getLeft();
        }    
        return null;
    }
    public BinaryTree<E> getRight(){
        if(!isEmpty()){
            return root.getRight();
        }    
        return null;
    }
    
    public BinaryTree(E content) {
        this.root = new BinaryNode<E>(content);
    }

    public BinaryNode<E> getRoot() {
        return root;
    }

    public void setRoot(BinaryNode<E> root) {
        this.root = root;
    }
    public boolean isEmpty(){
        return root == null;
    }
 
    
    public int contarNodos(){
        int suma = 0;
        if(!isEmpty()){
            suma += 1;
            if(this.getRoot().getLeft() != null){
                suma += this.getRoot().getLeft().contarNodos();
            }
            if(this.getRoot().getRight() != null){
                suma += this.getRoot().getRight().contarNodos();
            }
        }
        return suma;
    }
    
    
    
    // TAREA 
    
    //Literal 1)
    // Uso del metodo contarNodos porque todos los nodos son descendientes menos la raiz
    public int countDescendantsRecursive(){
        //Uso del metodo contarNodos porque es la misma logica y se uso recursividad
        if(this.isEmpty() || this.root.isLeaf()) return 0;
        return this.contarNodos() - 1;
    }
    public int countDescendantsIterative(){
        int result = 0;
        if(!isEmpty()){
            Stack<BinaryTree<E>> pila = new Stack<>();
            pila.push(this);
            while(!pila.isEmpty()){
                BinaryTree<E> tree = pila.pop();
                result+=1;
                if(tree.getLeft()!=null){
                    pila.push(tree.getLeft());
                }
                if(tree.getRight()!=null){
                    pila.push(tree.getRight());
                }
            }
            result -= 1;
        }
        return result;
    }

    
    
    
    //Literal 2)
    public BinaryTree<E> findParentRecursive(BinaryNode<E> node){
        if(getLeft()!= null && getLeft().getRoot().equals(node)|| 
            getRight()!= null && getRight().getRoot().equals(node)){
            return this;
        }
        BinaryTree<E> parent = null;
        if(getLeft()!= null && parent == null){
            parent = getLeft().findParentRecursive(node);
        }
        if(getRight()!= null && parent == null){
            parent = getRight().findParentRecursive(node);
        }
        return parent;
    }
    public BinaryTree<E> findParentIterative(BinaryNode<E> node){
        if(!isEmpty() && !root.isLeaf()){
            Stack<BinaryTree<E>> pila = new Stack<>();
            pila.push(this);
            while(!pila.isEmpty()){
                BinaryTree<E> arbol = pila.pop();
                if(arbol.getLeft()!= null){
                    if(arbol.getLeft().getRoot().equals(node)){
                        return arbol;
                    }
                    pila.push(arbol.getLeft());
                }
                if(arbol.getRight()!= null){
                    if(arbol.getRight().getRoot().equals(node)){
                        return arbol;
                    }
                    pila.push(arbol.getRight());
                }
            }
        }
        return null;
    }
    

    
    //Literal 3)
    public int countLevelsRecursive(){
        if(this.isEmpty()) return 0;
        if(this.root.isLeaf()) return 1;
        int sumaDerecha = 0;
        int sumaIzquierda = 0;
        
        if(getLeft()!= null){
            sumaIzquierda += 1 + getLeft().countLevelsRecursive();
        }
        if(getRight()!= null){
            sumaDerecha += 1 + getRight().countLevelsRecursive();
        }
        return Math.max(sumaDerecha, sumaIzquierda);
    }
    public int countLevelIterative(){
         if(!isEmpty()){
             Stack<BinaryTree<E>> pila = new Stack<>();
             pila.push(this);
             int sumaIzquierda = 0;
             int sumaDerecha = 0;
             while(!pila.isEmpty()){
                 BinaryTree<E> tree = pila.pop();
                 if(tree.getLeft()!=null){
                     sumaIzquierda += 1;
                     pila.push(tree.getLeft());
                 }
                 if(tree.getRight()!=null){
                     sumaDerecha += 1;
                     pila.push(tree.getRight());
                 }
             }
             return Math.max(sumaDerecha, sumaIzquierda);
         }
         else if(root.isLeaf()) return 1;
         else return 0;
    }

    
    

    //Literal 4)
    public boolean isLeftyRecursive(){
        if(isEmpty() || root.isLeaf()){
            return true;
        }
        if(getLeft()== null) {
            return false;
        }
        boolean izqLefty = getLeft().isLeftyRecursive();
        if(getRight()==null && izqLefty){
            return true;
        } 
        else if(!izqLefty){
            return false;
        }
        return (izqLefty && getRight().isLeftyRecursive()) && (getLeft().contarNodos()> getRight().contarNodos());
    }
    public boolean isLeftyIterative(){
        if(!isEmpty()){
            if(root.isLeaf()) return true;
            Stack<BinaryTree<E>> pila = new Stack<>();
            pila.push(this);
            while(!pila.isEmpty()){
                BinaryTree<E> tree = pila.pop();
                if(tree.getLeft() == null && tree.getRight() != null){
                    return false;
                }
                if((tree.getRight()!=null && tree.getLeft()!=null) && (tree.getLeft().contarNodos() <= tree.getRight().contarNodos())){
                    return false;
                }
                if(tree.getLeft()!=null){
                    pila.push(tree.getLeft());
                }
                if(tree.getRight()!=null){
                    pila.push(tree.getRight());
                }
            }
        }
        return true;
    }
    
    
    
    
    //Literal 5)
    public boolean isIdenticalRecursive(BinaryTree<E> tree){
        if(tree == null || isEmpty()) return false;
        if(!this.getRoot().getContent().equals(tree.getRoot().getContent())){
            return false;
        }
        boolean bool = true;
        if(getLeft()!=null && tree.getLeft()==null) return false;
        if(getLeft()==null && tree.getLeft()!=null) return false;
        
        if(getRight()==null && tree.getRight()!=null) return false;
        if(getRight()!=null && tree.getRight()==null) return false;

        if(getLeft()!=null){
            bool = getLeft().isIdenticalRecursive(tree.getLeft());
        }
        if(getRight()!=null){
            bool = getRight().isIdenticalRecursive(tree.getRight());
        }        
        return bool;
        
    }
    
    public boolean isIdenticalIterative(BinaryTree<E> arbol){
        if(!isEmpty() && arbol != null){
            Stack<BinaryTree<E>> pila = new Stack<>();
            Stack<BinaryTree<E>> pila2 = new Stack<>();
            pila.push(this);
            pila2.push(arbol);
            
            while(!pila.isEmpty() || !pila2.isEmpty()){
                if(pila.isEmpty() && !pila2.isEmpty()) return false;
                if(!pila.isEmpty() && pila2.isEmpty()) return false;

                BinaryTree<E> tree = pila.pop();
                BinaryTree<E> tree2 = pila2.pop();
                
                if(!tree.getRoot().getContent().equals(tree2.getRoot().getContent())){
                    return false;
                }
                
                if(tree.getLeft()!=null) {
                    pila.push(tree.getLeft());
                }
                if(tree.getRight()!=null) {
                    pila.push(tree.getRight());
                }                
                if (tree2.getLeft()!=null) {
                    pila2.push(tree2.getLeft());
                }
                if (tree2.getRight()!=null) {
                    pila2.push(tree2.getRight());
                }
            }
        }
        return true;
    }
    
    

    //Literal 6)
    public void largestValueOfEachLevel(){
        if(isEmpty()) return;
        if(getLeft()!=null && getRight()!=null){
            System.out.println(Math.max((Integer)getLeft().getRoot().getContent(), (Integer)getLeft().getRoot().getContent()));
        }else if(getLeft()!=null && getRight()==null){   
            System.out.println(getLeft().getRoot().getContent());
        }else if(getLeft()==null && getRight()!=null){
            System.out.println(getRight().getRoot().getContent());
        }
        if(getLeft()!=null){
            getLeft().largestValueOfEachLevel();
        }
        if(getRight()!=null){
            getRight().largestValueOfEachLevel();
        }
        
    }
    public void largestValueOfEachLeverIterative(){
        if(!isEmpty()){
            Stack<BinaryTree<E>> pila = new Stack<>();
            pila.push(this);
            while(!pila.isEmpty()){
                BinaryTree<E> tree = pila.pop();
                System.out.println(tree.getRoot().getContent());
                if(tree.getLeft()!=null){
                    pila.push(tree.getLeft());
                }
                if(tree.getRight()!=null){
                    pila.push(tree.getRight());
                }
            }
        }
    }
    
    
    
    
    //Literal 7)
    public int countNodesWithOnlyChildRecursive(){
        int result = 0;
        if(!isEmpty()){
            if((getLeft() != null && getRight() == null) || (getLeft() == null && getRight() != null)){
                result+=1;
                System.out.println("Nodo: "+getRoot().getContent());
            }
            if(getLeft()!=null){
                result += getLeft().countNodesWithOnlyChildRecursive();
            }
            if(getRight()!=null){
                result += getRight().countNodesWithOnlyChildRecursive();
            }
        }
        return result;
    }
    public int countNodesWithOnlyChildIterativa(){
        int result = 0;
        if(!isEmpty()){
            Stack<BinaryTree<E>> pila = new Stack<>();
            pila.push(this);
            while(!pila.isEmpty()){
                BinaryTree<E> tree = pila.pop();
                if((tree.getLeft() != null && tree.getRight() == null) || (tree.getLeft() == null && tree.getRight() != null)){
                    System.out.println("Nodo: " + tree.getRoot().getContent());
                    result += 1;
                }
                if(tree.getLeft()!=null){
                   pila.push(tree.getLeft());
                }
                if(tree.getRight()!=null){
                    pila.push(tree.getRight());
                }
            }
        }
        return result;
    }

    
    
    //Literal 8)
    public boolean isHeightBalancedRecursive(){
        if(isEmpty()) return true;
        if(getLeft()!=null && getRight()==null){
            return getLeft().countLevelsRecursive() < 2;
        }
        if(getRight()!=null && getLeft()==null){
            return getRight().countLevelsRecursive() < 2;
        }
        int leftHeight = getLeft().countLevelsRecursive();
        int rightHeight = getRight().countLevelsRecursive();
        
        return Math.abs(leftHeight - rightHeight) < 2;
    }
    public boolean isHeightBalancedIterative(){
        if(isEmpty()) return true;
        if(getLeft()!=null && getRight()==null){
            return getLeft().countLevelIterative() < 2;
        }
        if(getRight()!=null && getLeft()==null){
            return getRight().countLevelIterative() < 2;
        }
        int leftHeight = getLeft().countLevelIterative();
        int rightHeight = getRight().countLevelIterative();
        
        return Math.abs(leftHeight - rightHeight) < 2;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    public int contarHojasRecursive(){
        int suma = 0;
        if(!isEmpty()){
            if(this.getRoot().isLeaf()){
                suma += 1;
            }
            if(this.getRoot().getLeft() != null){
                suma += this.getRoot().getLeft().contarHojasRecursive();
            }
            if(this.getRoot().getRight() != null){
                suma += this.getRoot().getRight().contarHojasRecursive();
            }
        }
        return suma;
    }
    
    public int contarHojasIterativa(){
        int suma = 0;
        Stack<BinaryTree<E>> s = new Stack<>();
        if(!this.isEmpty()){
            s.push(this);
        }
        while(!s.isEmpty()){
            BinaryTree<E> arbol = s.pop();
            if(arbol.getRoot().isLeaf()) suma++;
            if(arbol.getRoot().getLeft() != null){
                s.push(arbol.getRoot().getLeft());
            }
            if(arbol.getRoot().getRight() != null){
                s.push(arbol.getRoot().getRight());
            }
        }
        return suma;
    }
   //raiz , subraiz izquierda , subraiz derecha
    public void recorrerPreOrden(){
        if(!isEmpty()){
            System.out.println(this.root.getContent());
            if(this.getRoot().getLeft() != null){
                this.getRoot().getLeft().recorrerPreOrden();
            }
            if(this.getRoot().getRight() != null){
                 this.getRoot().getRight().recorrerPreOrden();
            }
        }
    }
    //subraiz izquierda , raiz , subraiz derecha
    public void recorrerEnOrden(){
        if(!isEmpty()){
            if(this.getRoot().getLeft() != null){
                this.getRoot().getLeft().recorrerEnOrden();
            }
            System.out.println(this.root.getContent());
            if(this.getRoot().getRight() != null){
                 this.getRoot().getRight().recorrerEnOrden();
            }
        }
    }
    //subraiz izquierda , subraiz derecha, raiz 
    public void recorrerPostOrden(){
        if(!isEmpty()){
            if(this.getRoot().getLeft() != null){
                this.getRoot().getLeft().recorrerPostOrden();
            }
            if(this.getRoot().getRight() != null){
                 this.getRoot().getRight().recorrerPostOrden();
            }
            System.out.println(this.root.getContent());
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinaryTree<?> other = (BinaryTree<?>) obj;
        return Objects.equals(this.root, other.root);
    }
    
    
}

