/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sera;

import javax.swing.JOptionPane;

/**
 *
 * @author tomea
 */
public class HashTable {

    Entry inicio;
    long tamaño=0;

    long search(String username) {
        Entry temp = inicio;
        long position = 0;
        
        while (temp != null) {
            if (temp.username.equals(username)) {
                return position;
                //return temp.posicion;
            }
            position++;
            temp = temp.siguiente;
        }
        return -1;
    }

    void add(String username, long pos) {
        System.out.println("entro");
        Entry entry = new Entry(username, pos);
        
        if (search(username) != -1) {
            System.out.println("Se halló el nombre");
            
            return; //verificó que si existe el usuario
        }

        if (inicio == null) {
            System.out.println("puntero en 0");
            inicio = entry;
            tamaño++;
            return;

        }
        
        Entry tmp = inicio;
        System.out.println("se esta agregando");
        
        while (tmp.siguiente != null) {
            tmp = tmp.siguiente;
            System.out.println("user: "+tmp.username);
        }
        
        tamaño++;
        tmp.siguiente = entry;
    }

    //ESTA MALO
    void remove(String username) {
        if (inicio == null) {
            return;
        }

        if (inicio.username.equals(username)) {
            inicio = inicio.siguiente;
            return;
        }
        
    }
    
    

}
