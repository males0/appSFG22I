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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author darwi
 */
public class detalleFactura {
    private Integer IdDetalle;
    private Integer IdEncabezado;
    private Integer IdInventario;
    private String Fecha;
    private Double SubTotal;
    private Double Iva;
    private Double Total;

    public detalleFactura() {
    }

    public detalleFactura(Integer IdDetalle, Integer IdEncabezado, Integer IdInventario, String Fecha, Double SubTotal, Double Iva, Double Total) {
        this.IdDetalle = IdDetalle;
        this.IdEncabezado = IdEncabezado;
        this.IdInventario = IdInventario;
        this.Fecha = Fecha;
        this.SubTotal = SubTotal;
        this.Iva = Iva;
        this.Total = Total;
    }

    public detalleFactura(Integer IdEncabezado, Integer IdInventario, String Fecha, Double SubTotal, Double Iva, Double Total) {
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
    
    public DefaultTableModel listarDetallePorParametro(String criterio, String busqueda) {
        System.out.println("\n\nBuscar detallefactura\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();

        String titulos[] = {"IDINVENTARIO", "IDPRODUCTO", "IDPROVEEDOR", "INVFECHACOMPRA", "INVFECHAELABORACION", "INVFECHAEXPIRACION", "INVSTOK","invPrecio"};
        ResultSet rs = null;
        DefaultTableModel dtm = new DefaultTableModel(null, titulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }};
        
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_DetallefacturaParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            System.out.println(""+st);

            rs = st.executeQuery();
            String Datos[] = new String[7];
            //Eliminar los datos que se encuentran en la tabla
            int f, i;
            f = dtm.getRowCount();
            if (f > 0) {
                for (i = 0; i < f; i++) {
                    dtm.removeRow(0);
                }
            }
            //Llenar la tabla con la informacion de acuerdo al criterio y el texto de la busqueda
            while (rs.next()) {
                Datos[0] = (String) rs.getString(1);
                Datos[1] = (String) rs.getString(2);
                Datos[2] = (String) rs.getString(3);
                Datos[3] = (String) rs.getString(4);
                Datos[4] = (String) rs.getString(5);
                Datos[5] = (String) rs.getString(6);
                Datos[6] = (String) rs.getString(7);
                Datos[7] = (String) rs.getString(8);
              

                dtm.addRow(Datos);
            }
            return dtm;
        } catch (SQLException SQLex) {
            System.out.println("No se logro grabar el Registro.." + SQLex);
            return null;
        }
    }
    public ResultSet listarInventariodetalle(String criterio, String busqueda){
        System.out.println("\n\nBuscar Inventario\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_DetallefacturaParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            System.out.println("");
            return rs;
        }
        
        
    }
    
    public void guardar() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO detalleFacturas (idEncabezado,idInventario,detFecha,detSubtotal,detIva,detTotal)VALUES (?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, getIdEncabezado());
            ps.setInt(2, getIdInventario());
            ps.setString(3, getFecha());
            ps.setDouble(4, getSubTotal());
            ps.setDouble(5, getIva());
            ps.setDouble(6, getTotal());

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
    
    
    
    
     public String mostrarDatos() {
        String msj;
        msj = ("\n\nIdDetalle: " + getIdDetalle()
                + "\nIdEncabezado: " + getIdEncabezado()
                + "\nIdInventario: " + getIdInventario()
                + "\nFecha: " + getFecha()
                + "\nSubTotal: " + getSubTotal()
                + "\nIva: " + getIva()
                + "\nTotal: " + getTotal());
        return msj;
    }

     
    
}


