/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author estel
 */
public class Persona {
    // se corresponde con nuestra DATA BASE
    private int id_persona;
    private String nombre;
    private String apellido;
    
    public int getId_persona(){
        return id_persona;
    }
    public void setId_persona(int idPersona){
        id_persona=idPersona;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido=apellido;
    }
    
    @Override
    public String toString(){
        return "Persona{ "+"id_persona= "+id_persona+", Nombre ="+nombre+", Apellido= "+apellido+" "+'}';
    }
}
