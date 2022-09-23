
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

public class productos {
    private Integer IDPRODUCTO;
    private String PRODESCRIPCION;
    private String PROCODIGO;
    private String PROUNIDADMEDIDA;
    private String PROINFORMACIONNUTRICIONAL;
    private String PROOBSERVACION;
    
    public productos(Integer IDPRODUCTO, String PRODESCRIPCION, String PROCODIGO, String PROUNIDADMEDIDA, String PROINFORMACIONNUTRICIONAL, String PROOBSERVACION) {
        this.IDPRODUCTO = IDPRODUCTO;
        this.PRODESCRIPCION = PRODESCRIPCION;
        this.PROCODIGO= PROCODIGO;
        this.PROUNIDADMEDIDA = PROUNIDADMEDIDA;
        this.PROINFORMACIONNUTRICIONAL = PROINFORMACIONNUTRICIONAL;
        this.PROOBSERVACION = PROOBSERVACION;
      
    }

    public productos(String PRODESCRIPCION, String PROCODIGO, String PROUNIDADMEDIDA, String PROINFORMACIONNUTRICIONAL, String PROOBSERVACION) {
        this.PRODESCRIPCION = PRODESCRIPCION;
        this.PROCODIGO= PROCODIGO;
        this.PROUNIDADMEDIDA = PROUNIDADMEDIDA;
        this.PROINFORMACIONNUTRICIONAL = PROINFORMACIONNUTRICIONAL;
        this.PROOBSERVACION = PROOBSERVACION;
    }
    public productos() {
        this.IDPRODUCTO = 0;
//        this.nombres="Ramiro";
//        this.apellidos="Aguirre";
    }


    public Integer getIDPRODUCTO() {
        return IDPRODUCTO;
    }

    public void setIDPRODUCTO(Integer IDPRODUCTO) {
        this.IDPRODUCTO = IDPRODUCTO;
    }

    public String getPRODESCRIPCION() {
        return PRODESCRIPCION;
    }

    public void setPRODESCRIPCION(String PRODESCRIPCION) {
        this.PRODESCRIPCION = PRODESCRIPCION;
    }

    public String getPROCODIGO() {
        return PROCODIGO;
    }

    public void setPROCODIGO(String PROCODIGO) {
        this.PROCODIGO = PROCODIGO;
    }

    public String getPROUNIDADMEDIDA() {
        return PROUNIDADMEDIDA;
    }

    public void setPROUNIDADMEDIDA(String PROUNIDADMEDIDA) {
        this.PROUNIDADMEDIDA = PROUNIDADMEDIDA;
    }

    public String getPROINFORMACIONNUTRICIONAL() {
        return PROINFORMACIONNUTRICIONAL;
    }

    public void setPROINFORMACIONNUTRICIONAL(String PROINFORMACIONNUTRICIONAL) {
        this.PROINFORMACIONNUTRICIONAL = PROINFORMACIONNUTRICIONAL;
    }

    public String getPROOBSERVACION() {
        return PROOBSERVACION;
    }

    public void setPROOBSERVACION(String PROOBSERVACION) {
        this.PROOBSERVACION = PROOBSERVACION;
    }

    public void guardar() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO productos (PRODESCRIPCION,PROCODIGO,PROUNIDADMEDIDA,PROINFORMACIONNUTRICIONAL,PROOBSERVACION)VALUES (?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getPROCODIGO());
            ps.setString(2, getPRODESCRIPCION());
            ps.setString(3, getPROINFORMACIONNUTRICIONAL());
            ps.setString(4, getPROOBSERVACION());
            ps.setString(5, getPROUNIDADMEDIDA());
           
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

            String sql = "DELETE FROM PRODUCTOS WHERE idPRODUCTO = " + getIDPRODUCTO();
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

    public DefaultTableModel SP_S_productosParametro(String criterio, String busqueda) {
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

      public  ResultSet SP_S_productosParametro1(String criterio, String busqueda) {
         System.out.println(criterio + " " + busqueda);
        Connection cnn = conectar.getConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_productosParametro(?,?)}");
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

            String sql = "UPDATE productos SET PRODESCRIPCION=?, PROCODIGO=?,PROUNIDADMEDIDA=?, PROINFORMACIONNUTRICIONAL=?, PROOBSERVACION=? WHERE IDPRODUCTO=" + getIDPRODUCTO();
            try {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, getPRODESCRIPCION());
                ps.setString(2, getPROCODIGO());
                ps.setString(3, getPROUNIDADMEDIDA());
                ps.setString(4, getPROINFORMACIONNUTRICIONAL());
                ps.setString(5, getPROOBSERVACION());
                
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
        msj = ("\n\nIdProducto: " + getIDPRODUCTO()
                 + "\nDescripcion: " + getPRODESCRIPCION()
                + "\nCodigo: " + getPROCODIGO()
                + "\nUnidad de medida: " + getPROUNIDADMEDIDA()
                + "\nInformacion Nutricional: " + getPROINFORMACIONNUTRICIONAL()
                + "\nObservacion: " + getPROOBSERVACION());
        return msj;
    }
    public String mostrarDatosFactura() {
        String msj;
        msj = ("IdProducto: " + getIDPRODUCTO()
                   + "\nDescripcion: " + getPRODESCRIPCION()
                + "\nCodigo: " + getPROCODIGO()
                + "\nInformacion Nutricional: " + getPROINFORMACIONNUTRICIONAL()
                + "\nUnidad de medida: " + getPROUNIDADMEDIDA()
                + "\nObservacion: " + getPROOBSERVACION());
        return msj;
    }

}