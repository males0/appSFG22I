package modelo;

import db.conectar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class inventario {
    private Integer idInventario;
    private Integer idProducto;
    private Integer idProveedor;
    private String fechaCompra;
    private String fechaElaboracion;
    private String fechaExpiracion;
    private Integer stock;
    private Double precio;

    public inventario(Integer idInventario, Integer idProducto, Integer idProveedor, String fechaCompra, String fechaElaboracion, String fechaExpiracion, Integer stock, Double precio) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaCompra = fechaCompra;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaExpiracion = fechaExpiracion;
        this.stock = stock;
        this.precio = precio;
    }

    public inventario(Integer idInventario, Double precio) {
        this.idInventario = idInventario;
        this.precio = precio;
    }
    
    

    public inventario(Integer idProducto, Integer idProveedor, String fechaCompra, String fechaElaboracion, String fechaExpiracion, Integer stock, Double precio) {
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaCompra = fechaCompra;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaExpiracion = fechaExpiracion;
        this.stock = stock;
        this.precio = precio;
    }

    public inventario() {
        this.idProducto = 0;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(String fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    

    
    
    public void guardar(){
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;
        
        String sql;

        sql=" insert into inventario (IDPRODUCTO,IDPROVEEDOR,INVFECHACOMPRA,INVFECHAELABORACION,INVFECHAEXPIRACION,INVSTOK,invPrecio) value (?,?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1,getIdProducto());
            ps.setInt(2,getIdProveedor());
            ps.setString(3,getFechaCompra());
            ps.setString(4,getFechaElaboracion());
            ps.setString(5,getFechaExpiracion());
            ps.setInt(6,getStock());
            ps.setDouble(7,getPrecio());
            
            int n = ps.executeUpdate();
            if (n>0){
                System.out.println("Grabación Exitosa");
                JOptionPane.showMessageDialog(null,"Registrado con exito", "Grabar Registro",JOptionPane.INFORMATION_MESSAGE);
            }
            
            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.."+e);
        }
    }
    public void actualizar() {

         if (pregunta("¿Esta seguro de Actualizar el registro?", "Actualizar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "UPDATE inventario SET INVFECHACOMPRA=?, invFechaElaboracion=?, invFechaExpiracion=?, INVSTOK=?, invPrecio=? WHERE idInventario=" + getIdInventario();

            try {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, getFechaCompra());
                ps.setString(2, getFechaElaboracion());
                ps.setString(3, getFechaExpiracion());
                ps.setString(4, getStock().toString());
                ps.setString(5, getPrecio().toString());
               
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
    
    public ArrayList<inventario> listarInventario() {
//        db.conectar con = new conectar();
        Connection cnn = conectar.getConexion();
        
        ArrayList<inventario> listaInventario = new ArrayList<inventario>();
        
        
        try {
            
            CallableStatement st = cnn.prepareCall("{call SP_S_Inventario}");
            
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                inventario inv = new inventario();

                inv.setIdInventario(rs.getInt("idInventario"));
                inv.setIdProducto(rs.getInt("idProducto"));
                inv.setIdProveedor(rs.getInt("idProveedor"));
                inv.setFechaCompra(rs.getString("invFechaCompra"));
                inv.setFechaElaboracion(rs.getString("invFechaElaboracion"));
                inv.setFechaExpiracion(rs.getString("invFechaExpiracion"));
                inv.setStock(rs.getInt("invStock"));
                inv.setPrecio(rs.getDouble("invPrecio"));

                listaInventario.add(inv);
            }
            return listaInventario;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void eliminar() {

       if (pregunta("Esta seguro de Eliminar el registro?", "Eliminar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "DELETE FROM inventario WHERE idInventario = " + getIdInventario();
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
    public  ResultSet listarInventarioPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("\n\nBuscar Inventario\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_invProParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);

            rs = st.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
        
    }

    public ResultSet listarInventarioProductos(String criterio, String busqueda){
        System.out.println("\n\nBuscar Inventario\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_InventarioParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            System.out.println("");
            return rs;
        }
        
        
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
    public String mostrarDatos() {
        String msj;
        msj = ("\n\nIdInventario: " + getIdInventario()
                + "\nIdProducto: " + getIdProducto()
                + "\nIdProveedor: " + getIdProveedor()
                + "\nFecha de Compra: " + getFechaCompra()
                + "\nFecha de Elaboracion: " + getFechaElaboracion()
                + "\nFecha de Expiracion: " + getFechaExpiracion()
                + "\nStock: " + getStock())
                + "\nPrecio: " + getPrecio();
        
        
        return msj;
    }
     public DefaultTableModel z(String criterio, String busqueda) {
        System.out.println("\n\nBuscar productos\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();

        String titulos[] = {"idPRODUCTO","DESCRIPCION","CODIGO","UNIDADMEDIDA","INFORMACIONNUTRICIONAL","OBSERVACION",};
        ResultSet rs = null;
        DefaultTableModel dtm = new DefaultTableModel(null, titulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }};
        
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_productosParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            System.out.println(""+st);

            rs = st.executeQuery();
            String Datos[] = new String[6];
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
               

                dtm.addRow(Datos);
            }
            return dtm;
        } catch (SQLException SQLex) {
            System.out.println("No se logro grabar el Registro.." + SQLex);
            return null;
        }
    }

    
    public Integer consultarStock(Integer idInventario){
        System.out.println("\n\nBuscar Inventario\nCriterio: "+idInventario);
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_consultarStock(?)}");
            st.setString("pIdInventario", String.valueOf(idInventario));
            rs = st.executeQuery();
             while(rs.next()){
                String stock = rs.getString("invStock");
                System.out.println(stock);
                if ( stock != null ){
                    //JOptionPane.showMessageDialog(null, "Expediente encontrado: " + cerexa, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    return Integer.parseInt(stock);
                } 
            }
            rs.close();  
        } catch (SQLException SQLex) {
            System.out.println("");
//            return null;
        }
        return null;
    }
    public void actualizarStock(Integer idInventario, Integer cantidad) {

        if (pregunta("¿Esta seguro de Actualizar el registro?", "Actualizar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "UPDATE inventario SET invStock=? WHERE IDINVENTARIO=" + idInventario;
            try {
                ps = cnn.prepareStatement(sql);
                Integer totalStock=0, stockDB;
                stockDB = consultarStock(idInventario);
                totalStock = stockDB - cantidad;
                
                ps.setString(1, totalStock.toString());
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
                System.out.println("No se logro actualizar el Registro.." + e);
            }
        } else {
            System.out.println("Actualizacion Cancelada");
        }

    }
    
}
