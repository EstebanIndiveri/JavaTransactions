/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejopersonas;

import datos.PersonasJDBC;
import domain.Persona;
import java.util.List;
import datos.Conection;
import java.sql.*;
/**
 *
 * @author estel
 */
public class ManejoPersonas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PersonasJDBC personasJDBC=new PersonasJDBC();
        Connection conn=null;
                
        try{
            conn=Conection.getConnection();
            if(conn.getAutoCommit());{
            conn.setAutoCommit(false);
            }
            //Creamos el objeto PersonasJDBC
            //Proporcionamos la conexión creada:
            PersonasJDBC personas=new PersonasJDBC(conn);

            //Empezamos a ejecutar sentencias
            //Una transaccion agrupa varias Sentencias SQL
            //Si algo falla no se realizan los cambios en la BD
            //De otra forma los cambios son correctos:
            personas.update(2,"Regreso2","Regreso");
            //DEBE EMITIR POR CONSOLA EL ERROR EXCEPTION.MYSQLDATATRUNCATION:DATA TRUNCATION: DATA TOO LONG
            //YA QUE SUPERAMOS LOS 47 CHAR QUE INSTANCIAMOS EN LA BASE DE DATOS PARA ESTA COLUMNA "APELLIDO".
            personas.insert("Nombre","Test12345678910121314151617181920212223242526272829304041424344454647484950");
            
            //Guardamos cambios
            conn.commit();
        }catch(SQLException e){
            //ROLLBACK EN ERROR
            try{
                System.out.println("Entramos al rollback");
                //imprimirmos la excepcion a la consola
                e.printStackTrace(System.out);
                //HacemosRollBack:
                conn.rollback();
            }catch(SQLException e1){
                e1.printStackTrace(System.out);
            }
        }
        
        //Esta clase es la que contiene los metodos para operar con la tabla de personas:
        //*PersonasJDBC personasJDBC=new PersonasJDBC();
        //prueba del metodo insert:
        //*personasJDBC.insert("Alberto","Juarez");/*Comentar para no obtener un nuevo insert*/
        
        //Prueba del metodo Update:
        //*personasJDBC.update(2,"Update","Java");/*Comentar para no tener un update reiterado*/
        
        //Prueba del metodo Delete:
        //*personasJDBC.delete(7);/*comentar para no borrar el mismo dato siempre*/
        
        //Prueba del metodo Select:
        //Uso del objeto Persona para encapsular la información
        //de un regristo de base de datos:
        //*List<Persona>personas=personasJDBC.select();
        //*for(Persona persona:personas){
            //Recordar que el metodo toString esta modificado
            //Por lo cual se puede llamar sin problemas y no traera solamente el
            //espacio en la memoria:
            //*System.out.println(persona);
            //*System.out.println("");
    }
}
   
