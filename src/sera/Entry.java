/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sera;

/**
 * Crear una clase llamada Entry que tiene como atributo una String para guardar
 * el username de un registro, un long para guardar la posición donde se
 * encuentra el registro de ese usuario en un archivo y un atributo para que
 * apunte al siguiente elemento de una lista. En el constructor se inicializan
 * por parámetro el código y la posición. Por default el siguiente esta en null.
 * 5%
 */

//LISTO
public class Entry {

    String username;
    long posicion;//para guardar la posicion donde se encuentra el registro de ese usuario en un archivo
    Entry siguiente;

    public Entry(String username, long posicion) {
        this.username = username;
        this.posicion = posicion;
        this.siguiente = null;
    }

}
