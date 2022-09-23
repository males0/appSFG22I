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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author darwi
 */
public class factura {
    
     private Integer IdDetalle;
    private Integer IdEncabezado;
    private Integer IdInventario;
    private String Fecha;
    private Double SubTotal;
    private Double Iva;
    private Double Total;

    public factura() {
    }

    public factura(Integer IdDetalle, Integer IdEncabezado, Integer IdInventario, String Fecha, Double SubTotal, Double Iva, Double Total) {
        this.IdDetalle = IdDetalle;
        this.IdEncabezado = IdEncabezado;
        this.IdInventario = IdInventario;
        this.Fecha = Fecha;
        this.SubTotal = SubTotal;
        this.Iva = Iva;
        this.Total = Total;
    }

    public factura(Integer IdEncabezado, Integer IdInventario, String Fecha, Double SubTotal, Double Iva, Double Total) {
        this.IdEncabezado = IdEncabezado;
        this.IdInventario = IdInventario;
        this.Fecha = Fecha;
        this.SubTotal = SubTotal;
        this.Iva = Iva;
        this.Total = Total;
    }

    
    public Integer getIdDetalle() {
        return IdDetalle;
    }

    public void setIdDetalle(Integer IdDetalle) {
        this.IdDetalle = IdDetalle;
    }

    public Integer getIdEncabezado() {
        return IdEncabezado;
    }

    public void setIdEncabezado(Integer IdEncabezado) {
        this.IdEncabezado = IdEncabezado;
    }

    public Integer getIdInventario() {
        return IdInventario;
    }

    public void setIdInventario(Integer IdInventario) {
        this.IdInventario = IdInventario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public Double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Double SubTotal) {
        this.SubTotal = SubTotal;
    }

    public Double getIva() {
        return Iva;
    }

    public void setIva(Double Iva) {
        this.Iva = Iva;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }
    
    
    
    
      public void guardar() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO detallefacturas (IdEncabezado,IdInventario,detFecha,,detSubtotal,detIva,detTotal)VALUES (?,?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, getIdDetalle());
            ps.setInt(2, getIdEncabezado());
            ps.setInt(3, getIdInventario());
            ps.setString(4, getFecha());
            ps.setDouble(5, getSubTotal());
            ps.setDouble(6, getIva());
            ps.setDouble(7, getTotal());
           

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

    public void buscar() {

    }
    
    public Integer pregunta(String msj1, String msj2) {
        Object[] options = {"Aceptar", "Cancelar"};
        Integer n = JOptionPane.showOptionDialog(null,
                msj1 + mostrarDatos(),
                msj2,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return n;
    }

    public void eliminar() {

       if (pregunta("Esta seguro de Eliminar el registro?", "Eliminar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "DELETE FROM detallefacturas WHERE idInventario = " + getIdInventario();
            try {
                ps = cnn.prepareStatement(sql);

                int n = ps.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
                }

                cnn.close();
                ps.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);
                System.out.println("No se logro Eliminar el Registro..." + ex);

            }

        } else {
            System.out.println("Eliminacion Cancelada");
        }

    }

    public void validar() {

    }



    public ResultSet listarInventarioPorParametro(String criterio, String busqueda) {
        System.out.println(criterio + " " + busqueda);
        Connection cnn = conectar.getConexion();

        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_DetallefacturaParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            System.out.println(""+st);

            rs = st.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            System.out.println("No se logro grabar el Registro.." + SQLex);
            return null;
        }
    }
    
    
    public void actualizar() {

         if (pregunta("¿Esta seguro de Actualizar el registro?", "Actualizar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "UPDATE detallefacturas SET detFecha=?, detSubtotal=?, detIva=?, detTotal=? WHERE idDetalle=" + getIdDetalle();

            try {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, getFecha());
                ps.setString(2, getSubTotal().toString());
                ps.setString(3, getIva().toString());
                ps.setString(4, getTotal().toString());
               
               
                System.out.println(sql);

                int r = ps.executeUpdate();
                cnn.close();
                ps.close();
                if (r > 0) {
                    JOptionPane.showMessageDialog(null, "Actualización Exitosa", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo Actualizar el Registro", "Actualizar Registro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                Logger.getLogger(clientes.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("No se logro actualizar el Registro.." + e);
            }
        } else {
            System.out.println("Actualizacion Cancelada");
        }
    }
    
    
    
    public String mostrarDatos() {
        String msj;
        msj = ("\n\nIdDetalle: " + getIdDetalle()
                + "\nIdEncabezado: " + getIdEncabezado()
                + "\nIdInventario: " + getIdInventario()
                + "\nFecha: " + getFecha()
                + "\nSubtotal: " + getSubTotal()
                + "\nIva: " + getIva()
                + "\nTotal: " + getTotal());
                
        
        return msj;
    }
}


