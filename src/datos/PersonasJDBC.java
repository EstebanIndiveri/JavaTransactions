/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;
import domain.Persona;
import java.sql.*;
import java.util.*;   
/**
 *
 * @author estel
 */
public class PersonasJDBC {
        
        private java.sql.Connection userConn;
    //Nos apoyamos de la llave primaria autoincrementable de MySQL 
    //Omitimos el campo de Persona_id, se incremetna solo:
    //Se utiliza un prepareStatement, por lo que podemos 
    //utilizar parametros(Signo de ?)
    //Los mismos van a ser sustituídos por el parametro respectivo:
    private final String SQL_INSERT="INSERT INTO persona(nombre, apellido) VALUES(?,?)";
    
    private final String SQL_UPDATE="UPDATE persona SET nombre=?, apellido=? WHERE id_persona=?";
    
    private final String SQL_DELETE="DELETE FROM persona WHERE id_persona=?";
    
    private final String SQL_SELECT="SELECT id_persona, nombre, apellido FROM persona ORDER BY id_persona";
    
    public PersonasJDBC(){}
    
    public PersonasJDBC(Connection conn){
        this.userConn=conn;
    }
    
    public int insert(String nombre, String apellido)throws SQLException{
        Connection conn=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        
        int rows=0;//registros afectados:
        try{
            conn=(this.userConn!=null)?this.userConn:Conection.getConnection();
            stmt=conn.prepareStatement(SQL_INSERT);
            int index=1;//contador de columnas
            stmt.setString(index++,nombre);//param 1=>?;
            stmt.setString(index++,apellido);//param 2)=>?;
            System.out.println("Ejecutando Query: "+SQL_INSERT);
            rows=stmt.executeUpdate();//numero de registros afectados:
            System.out.println("Registros afectados: "+rows);
        /*}catch(SQLException e){ OMITIMOS ESTE CATCH Para que se propage
            e.printStackTrace();*/
        }finally{
            Conection.close(stmt);
            if(this.userConn==null){
                Conection.close(conn);
            }
        }
        return rows;
    }
    public int update(int id_persona, String nombre, String apellido)throws SQLException{
        Connection conn=null;
        PreparedStatement stmt=null;
        int rows=0;
        try{
            conn=(this.userConn!=null)?this.userConn:Conection.getConnection();//OPERADOR TERNARIO
            System.out.println("Ejecutando Query: "+SQL_UPDATE);
            stmt=conn.prepareStatement(SQL_UPDATE);
            int index=1;
            stmt.setString(index++,nombre);
            stmt.setString(index++, apellido);
            stmt.setInt(index,id_persona);
            rows=stmt.executeUpdate();
            System.out.println("Registros actualizados: "+rows);
        }finally{
            Conection.close(stmt);
            if(this.userConn==null){
                Conection.close(conn);
            }
        }
        return rows;
    }
    
    public int delete(int id_persona)throws SQLException{
        Connection conn=null;
        PreparedStatement stmt=null;
        int rows=0;
        try{
            conn=(this.userConn!=null)? this.userConn:Conection.getConnection();
            
            System.out.println("Ejecutando query: "+SQL_DELETE);
            stmt=conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id_persona);
            rows=stmt.executeUpdate();
            System.out.println("Registros eliminados: "+rows);
        }finally{
            Conection.close(stmt);
                if(this.userConn==null){
                    Conection.close(conn);
                }
        }
        return rows;
    }
    //tipo lista, Instanciamos el tipo generico <PERSONA> devuelve objetos de este tipo:
    public List<Persona>select() throws SQLException{
        Connection conn=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        Persona persona=null;
        //Creamos la lista con objetos del tipo persona:
        List<Persona>personas=new ArrayList<Persona>();
        try{
            conn=(this.userConn!=null)?this.userConn:Conection.getConnection();
            stmt=conn.prepareStatement(SQL_SELECT);
            rs=stmt.executeQuery();
            while(rs.next()){
                int id_persona=rs.getInt(1);
                String nombre=rs.getString(2);
                String apellido=rs.getString(3);
                persona=new Persona();
                //OTRA OPCION ERA PONER UN CONSTRUCTOR DE LOS DATOS
                persona.setId_persona(id_persona);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                //Por cada registro agrega uno nuevo.Obteniendo una lista final:
                personas.add(persona);
            }
        }finally{
            Conection.close(rs);
            Conection.close(stmt);
            if(this.userConn==null){
                Conection.close(conn);
            }
        }
        //regresamos la lista de personas:
        return personas;
    }
}
