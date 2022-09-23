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
import javax.swing.table.DefaultTableModel;
public class proveedores {
    private Integer IDPROVEEDOR;
    private String PRONOMBRE;
    private String PRONOMBRECONTACTO;
    private String PRORUC;
    private String PROCORREO;
    private String PROTELEFONO;
    private String PRODIRECCION;
    private String PROPAGINAWEB;
     public proveedores(Integer IDPROVEEDOR, String PRONOMBRE, String PRONOMBRECONTACTO, String PRORUC, String PROCORREO, String PROTELEFONO, String PRODIRECCION, String PROPAGINAWEB) {
        this.IDPROVEEDOR = IDPROVEEDOR;
        this.PRONOMBRE = PRONOMBRE;
        this.PRONOMBRECONTACTO = PRONOMBRECONTACTO;
        this.PRORUC = PRORUC;
        this.PROCORREO = PROCORREO;
        this.PROTELEFONO = PROTELEFONO;
        this.PRODIRECCION = PRODIRECCION;
        this.PROPAGINAWEB = PROPAGINAWEB;
    }
      public proveedores( String PRONOMBRE, String PRONOMBRECONTACTO, String PRORUC, String PROCORREO, String PROTELEFONO, String PRODIRECCION, String PROPAGINAWEB) {
        this.PRONOMBRE = PRONOMBRE;
        this.PRONOMBRECONTACTO = PRONOMBRECONTACTO;
        this.PRORUC = PRORUC;
        this.PROCORREO = PROCORREO;
        this.PROTELEFONO = PROTELEFONO;
        this.PRODIRECCION = PRODIRECCION;
        this.PROPAGINAWEB = PROPAGINAWEB;
    }
       public proveedores() {
        this.IDPROVEEDOR = 0;
//        this.nombres="Ramiro";
//        this.apellidos="Aguirre";
    }

    

    public Integer getIDPROVEEDOR() {
        return IDPROVEEDOR;
    }

    public void setIDPROVEEDOR(Integer IDPROVEEDOR) {
        this.IDPROVEEDOR = IDPROVEEDOR;
    }

    public String getPRONOMBRE() {
        return PRONOMBRE;
    }

    public void setPRONOMBRE(String PRONOMBRE) {
        this.PRONOMBRE = PRONOMBRE;
    }

    public String getPRONOMBRECONTACTO() {
        return PRONOMBRECONTACTO;
    }

    public void setPRONOMBRECONTACTO(String PRONOMBRECONTACTO) {
        this.PRONOMBRECONTACTO = PRONOMBRECONTACTO;
    }

    public String getPRORUC() {
        return PRORUC;
    }

    public void setPRORUC(String PRORUC) {
        this.PRORUC = PRORUC;
    }

    public String getPROCORREO() {
        return PROCORREO;
    }

    public void setPROCORREO(String PROCORREO) {
        this.PROCORREO = PROCORREO;
    }

    public String getPROTELEFONO() {
        return PROTELEFONO;
    }

    public void setPROTELEFONO(String PROTELEFONO) {
        this.PROTELEFONO = PROTELEFONO;
    }

    public String getPRODIRECCION() {
        return PRODIRECCION;
    }

    public void setPRODIRECCION(String PRODIRECCION) {
        this.PRODIRECCION = PRODIRECCION;
    }

    public String getPROPAGINAWEB() {
        return PROPAGINAWEB;
    }

    public void setPROPAGINAWEB(String PROPAGINAWEB) {
        this.PROPAGINAWEB = PROPAGINAWEB;
    }

     public void guardar() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO proveedores (PRONOMBRE,PRONOMBRECONTACTO,PRORUC,PROCORREO,PROTELEFONO,PRODIRECCION,PROPAGINAWEB)VALUES (?,?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getPRONOMBRE());
            ps.setString(2, getPRONOMBRECONTACTO());
            ps.setString(3, getPRORUC());
            ps.setString(4, getPROCORREO());
            ps.setString(5, getPROTELEFONO());
            ps.setString(6, getPRODIRECCION());
            ps.setString(7,getPROPAGINAWEB() );

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

            String sql = "DELETE FROM proveedores WHERE idproveedor = " + getIDPROVEEDOR();
            try {
                ps = cnn.prepareStatement(sql);

                int n = ps.executeUpdate();
//                if (n > 0) {
//                    JOptionPane.showMessageDialog(null, "Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
//                }

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

//    public ArrayList<clientes> listarClientes() {
////        db.conectar con = new conectar();
//        Connection cnn = conectar.getConexion();
//
//        ArrayList<clientes> clienteUsuarios = new ArrayList<clientes>();
//
//        try {
//
//            CallableStatement st = cnn.prepareCall("{call SP_S_Cliente}");
//
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                clientes cli5 = new clientes();
//
//                cli5.setIdCliente(rs.getInt("idCliente"));
//                cli5.setApellidos(rs.getString("cliApellidos"));
//                cli5.setNombres(rs.getString("cliNombres"));
//                cli5.setCedula(rs.getString("cliCedula"));
//                cli5.setCorreo(rs.getString("cliCorreo"));
//                cli5.setTelefono(rs.getString("cliTelefono"));
//                cli5.setDireccion(rs.getString("cliDireccion"));
//                clienteUsuarios.add(cli5);
//            }
//            return clienteUsuarios;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

    public DefaultTableModel SP_S_provedoresParametro(String criterio, String busqueda) {
        System.out.println("\n\nBuscar Provedores\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();

        String titulos[] = {"IDPROVEEDOR","NOMBRE","NOMBRECONTACTO ","RUC","CORREO","TELEFONO","DIRECCION","PAGINAWEB"};
        ResultSet rs = null;
        DefaultTableModel dtm = new DefaultTableModel(null, titulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }};
        
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_provedoresParametro(?,?)}");
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
                
               

                dtm.addRow(Datos);
            }
            return dtm;
        } catch (SQLException SQLex) {
            System.out.println("No se logro grabar el Registro.." + SQLex);
            return null;
        }
    }

    public ResultSet SP_S_provedoresParametro1(String criterio, String busqueda) {
        System.out.println(criterio + " " + busqueda);
        Connection cnn = conectar.getConexion();

        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_provedoresParametro(?,?)}");
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

            String sql = "UPDATE proveedores SET PRONOMBRE=?,PRONOMBRECONTACTO=?,PRORUC=?,PROCORREO=?,PROTELEFONO=?,PRODIRECCION=?,PROPAGINAWEB=? WHERE idproveedor=" + getIDPROVEEDOR();
            try {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, getPRONOMBRE());
                ps.setString(2, getPRONOMBRECONTACTO());
                ps.setString(3, getPRORUC());
                ps.setString(4, getPROCORREO());
                ps.setString(5, getPROTELEFONO());
                ps.setString(6, getPRODIRECCION());
                ps.setString(7,getPROPAGINAWEB());
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

    public String mostrarDatos() {
        String msj;
        msj = ("\n\nIdProvedor: " + getIDPROVEEDOR()
                + "\nNombre contacto: " + getPRONOMBRECONTACTO()
                + "\nRuc: " + getPRORUC()
                + "\nCorreo: " + getPROCORREO()
                + "\nTelefono: " + getPROTELEFONO()
                + "\nDireccion: " + getPRODIRECCION()
                + "\nPagina Web: " + getPROPAGINAWEB());
        return msj;
    }
    public String mostrarDatosFactura() {
        String msj;
        msj = ("IdCliente: " + getIDPROVEEDOR()
                + "\nNombre: " + getPRONOMBRE()
                + "\nNombre de Contacto: " + getPRONOMBRECONTACTO()
                + "\nRuc: " + getPRORUC()
                + "\nCorreo: " + getPROCORREO()
                + "\nTelefono: " + getPROTELEFONO()
                + "\nDireccion: " + getPRODIRECCION()
                +" \nPaginaWeb:"+getPROPAGINAWEB());
        return msj;
    }

    public ResultSet listarProveedorPorParametro(String criterio, String busqueda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}