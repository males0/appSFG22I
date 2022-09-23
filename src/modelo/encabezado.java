/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import db.conectar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author darwi
 */
public class encabezado {
     private Integer IdEncabezado;
    private Integer IdCliente;
    private Integer IdUsuario;
    private Integer numeroFactura;
    private String fecha;

    public encabezado() {
        this.IdCliente = 0;
    }

    public encabezado(Integer IdEncabezado, Integer IdCliente, Integer IdUsuario, Integer numeroFactura, String fecha) {
        this.IdEncabezado = IdEncabezado;
        this.IdCliente = IdCliente;
        this.IdUsuario = IdUsuario;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
    }

    public encabezado(Integer IdCliente, Integer IdUsuario, Integer numeroFactura, String fecha) {
        this.IdCliente = IdCliente;
        this.IdUsuario = IdUsuario;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
    }

    public Integer getIdEncabezado() {
        return IdEncabezado;
    }

    public void setIdEncabezado(Integer IdEncabezado) {
        this.IdEncabezado = IdEncabezado;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer IdCliente) {
        this.IdCliente = IdCliente;
    }

    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

 
    public void guardarenc() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;
        
       



        sql = "INSERT INTO encabezados (idClientes,idUsuario,encNumeroFactura,encFecha)VALUES (?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, getIdCliente());
            ps.setInt(2, getIdUsuario());
            ps.setInt(3, getNumeroFactura());
            ps.setString(4, getFecha());


            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Grabación Exitosa"+ps);
                JOptionPane.showMessageDialog(null, "Registrado con exito", "Grabar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.." + e);
        }
    }
    
    
    
    public Integer consultarMaxId(){
        System.out.println("\n\nConsultar Max Id ");
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_consultarMaxId}");
            rs = st.executeQuery();
             while(rs.next()){
                Integer stock = rs.getInt("MAX(IDENCABEZADO)");
                System.out.println(stock);
                if ( stock != null ){
                    //JOptionPane.showMessageDialog(null, "Expediente encontrado: " + cerexa, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    return stock;
                } 
            }
            rs.close();  
        } catch (SQLException SQLex) {
            System.out.println("");
        }
        return null;
    }
}

