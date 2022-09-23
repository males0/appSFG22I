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

public class empleados {
    
    private Integer idEmpleados;
    private String apellidos;
    private String nombres;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;
    private String cargo;
    public empleados(Integer idempleados, String apellidos, String nombres, String cedula, String correo, String telefono, String direccion,String cargo) {
        this.idEmpleados = idempleados;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cargo=cargo;
    }

    public empleados(String apellidos, String nombres, String cedula, String correo, String telefono, String direccion,String cargo) {
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cargo=cargo;
    }

    public empleados() {
        this.idEmpleados = 0;
//        this.nombres="Ramiro";
//        this.apellidos="Aguirre";
    }

    public Integer getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Integer idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCargo() {
        return direccion;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public void guardar() {
        Connection cnn = conectar.getConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO empleados (empApellidos,empNombres,empCedula,empCorreo,empTelefono,empDireccion,empCargo)VALUES (?,?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getApellidos());
            ps.setString(2, getNombres());
            ps.setString(3, getCedula());
            ps.setString(4, getCorreo());
            ps.setString(5, getTelefono());
            ps.setString(6, getDireccion());
            ps.setString(7, getCargo());

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

            String sql = "DELETE FROM empleados WHERE IDEMPLEADO = " + getIdEmpleados();
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

    public DefaultTableModel listarEmpleadosPorParametro(String criterio, String busqueda) {
        System.out.println("\n\nBuscar Empleados\nCriterio: "+criterio+"\ntextoBusquda: "+busqueda);
        Connection cnn = conectar.getConexion();

        String titulos[] = {"idEmpleado", "Apellidos", "Nombres", "Cedula", "Correo", "Telefono", "Direccion","cargo"};
        ResultSet rs = null;
        DefaultTableModel dtm = new DefaultTableModel(null, titulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }};
        
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_empleadosParametro(?,?)}");
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

    

    public void actualizar() {

        if (pregunta("¿Esta seguro de Actualizar el registro?", "Actualizar Datos") == JOptionPane.YES_OPTION) {
            Connection cnn = conectar.getConexion();
            PreparedStatement ps = null;

            String sql = "UPDATE empleados SET EMPAPELLIDOS=?,EMPNOMBRES=?,EMPCEDULA=?,EMPCORREO=?,EMPTELEFONO=?,EMPDIRECCION=?,EMPCARGO=? WHERE IDEMPLEADO=" + getIdEmpleados();
            try {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, getApellidos());
                ps.setString(2, getNombres());
                ps.setString(3, getCedula());
                ps.setString(4, getCorreo());
                ps.setString(5, getTelefono());
                ps.setString(6, getDireccion());
                ps.setString(7,getCargo());
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
        msj = ("\n\nIdCliente: " + getIdEmpleados()
                + "\nApellidos: " + getApellidos()
                + "\nNombres: " + getNombres()
                + "\nCedula: " + getCedula()
                + "\nCorreo: " + getCorreo()
                + "\nTelefono: " + getTelefono()
                + "\nDireccion: " + getDireccion())
                +"\ncargo :"+ getCargo();
        return msj;
    }
    public String mostrarDatosFactura() {
        String msj;
        msj = ("IdCliente: " + getIdEmpleados()
                + "\nApellidos: " + getApellidos()
                + "\nNombres: " + getNombres()
                + "\nCedula: " + getCedula()
                + "\nCorreo: " + getCorreo()
                + "\nTelefono: " + getTelefono()
                + "\nDireccion: " + getDireccion())
                +"\nCargo" + getCargo();
        return msj;
    }

}
