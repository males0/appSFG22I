package modelo;

import db.conectar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class usuarios {
    private Integer idUsuario;
    private Integer idEmpleado;
    private String usuUsuario;
    private String usuClave;
    private String paUsuario;
    private String usuFechaCreacion;
    
    //Constructores

    public usuarios(Integer idUsuario, Integer idEmpleado, String usuUsuario, String usuClave, String paUsuario, String usuFechaCreacion) {
        this.idUsuario = idUsuario;
        this.idEmpleado = idEmpleado;
        this.usuUsuario = usuUsuario;
        this.usuClave = usuClave;
        this.paUsuario = paUsuario;
        this.usuFechaCreacion = usuFechaCreacion;
    }

    public usuarios(Integer idEmpleado, String usuUsuario, String usuClave, String paUsuario, String usuFechaCreacion) {
        this.idEmpleado = idEmpleado;
        this.usuUsuario = usuUsuario;
        this.usuClave = usuClave;
        this.paUsuario = paUsuario;
        this.usuFechaCreacion = usuFechaCreacion;
    }

    

    public usuarios() {
    }
    //metodos setters y gettes

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public String getPaUsuario() {
        return paUsuario;
    }

    public void setPaUsuario(String paUsuario) {
        this.paUsuario = paUsuario;
    }

    public String getUsuFechaCreacion() {
        return usuFechaCreacion;
    }

    public void setUsuFechaCreacion(String usuFechaCreacion) {
        this.usuFechaCreacion = usuFechaCreacion;
    }

        
    //Guarda un nuevo Registro
    public void guardar(){
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;
        
        String sql;

        sql="INSERT INTO usuarios (idEmpleado,usuUsuario,usuClave,paUsuario,usuFechaCreacion)VALUES (?,?,?,?,?)";
        try {
            ps=cnn.prepareStatement(sql);
            
            ps.setString(1,getIdEmpleado().toString());
            ps.setString(2,getUsuUsuario());
            ps.setString(3,getUsuClave());
            ps.setString(4,getPaUsuario());
            ps.setString(5,getUsuFechaCreacion());
            
            
            int n = ps.executeUpdate();
            
            if (n > 0){
                JOptionPane.showMessageDialog(null,"Registrado con exito", "Grabar Registro",JOptionPane.INFORMATION_MESSAGE);
            }
            
            cnn.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"No se logro grabar el Registro..."+ex, "Grabar Registro",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public boolean actualizar() throws SQLException {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;
        
        String sql;
        sql = "UPDATE usuarios SET idUsuario=?, usuUsuario=?,usuClave=? WHERE idUsuario="+ getIdUsuario();
        ps = cnn.prepareStatement(sql);
        ps.setString(1,getIdUsuario().toString());
        ps.setString(2,getUsuUsuario());
        ps.setString(3,getUsuClave());
        //ps.setString(11,per.getCli_tipo());

            
        int rowsUpdated = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null,"Actualización Exitosa", "Actualizar Registro",JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"No se pudo Actualizar el Registro", "Actualizar Registro",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public String consultarUsuario(String usu) { //admin
       
        Connection cn = conectar.getConexion();
        
        PreparedStatement ps = null;
        
        ResultSet res;
        
        
        String sql = "SELECT idUsuario, usuUsuario FROM usuarios WHERE usuUsuario = '" + usu + "'";
//        String sql = "SELECT usu_codigo, usu_usuario FROM usuarios WHERE usu_usuario = '" + usu+ "'";
        try{
            ps = cn.prepareStatement(sql);
            res = ps.executeQuery();
            while(res.next()){
                String idUsuario = res.getString("idUsuario");
                if ( idUsuario != null ){
                    //JOptionPane.showMessageDialog(null, "Expediente encontrado: " + cerexa, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    return idUsuario;
                } 
            }
            res.close();            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
        
    }
     public  ResultSet listarusuarioPorParametro(Integer datci, String  criterio, String busqueda) throws Exception {
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call sp2 ( ?,?,?,?,?)}");
            st.setString("datci", criterio);
            st.setString("datusu", busqueda);
              st.setString("clave", criterio);
            st.setString("pa", busqueda);
              st.setString("fecha", criterio);
            System.out.println(st);
            rs = st.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    
    }
    public boolean consultarContrasena(String cod, String contrasenia) {
        
        Connection cnn = conectar.getConexion();
        
        PreparedStatement ps = null;
        
        ResultSet res;
        
        String sql = "SELECT idUsuario, usuClave FROM usuarios WHERE idUsuario = '" + cod + "' AND usuClave = '" + contrasenia+ "'";
        try{
            ps = cnn.prepareStatement(sql);
            
            res = ps.executeQuery();
            while(res.next()){
                String usuClave = res.getString("usuClave");
                if ( usuClave != null ){
                    return true;
                } 
            }
            res.close();            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
        
    }
    public String consultarFechaCreacion(Integer idUsuario) { //admin
       
        Connection cn = conectar.getConexion();
        PreparedStatement ps = null;
        ResultSet res;
        String sql = "SELECT idUsuario, usuFechaCreacion FROM usuarios WHERE idUsuario = '" + idUsuario + "'";
        try{
            ps = cn.prepareStatement(sql);
            res = ps.executeQuery();
            while(res.next()){
                String fechaCreacion = res.getString("usuFechaCreacion");
                if ( fechaCreacion != null ){
                    //JOptionPane.showMessageDialog(null, "Expediente encontrado: " + cerexa, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    return fechaCreacion;
                } 
            }
            res.close();            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
        
    }
       
}

    
    
