package Vistas;

import Conexiones.ConexionRs;
import Utils.ControlMensajes;
import Controladores.ContTcCobrosCaja;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmVistaEntidadesEmisoras extends javax.swing.JDialog {

    ContTcCobrosCaja voucher = new ContTcCobrosCaja();
    DefaultTableModel modelo = new DefaultTableModel();

    public FrmVistaEntidadesEmisoras() {
        initComponents();
        setModal(true);
        addTiposTarjetas();
        formatoColumnas();
        this.setLocationRelativeTo(null);
    }

    private void addTiposTarjetas() {
        ConexionRs cnRs = new ConexionRs();
        String[] titulos = {"IDENTIFICADOR", "CODIGO", "DESCRIPCION"};
        String[] registro = new String[3];
        modelo = new DefaultTableModel(null, titulos);

        String sql = "SELECT IDENTIFICADOR, CODIGO, DESCRIPCION FROM TC_ENT_EMISORAS";
        try {
            PreparedStatement pst = cnRs.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("CODIGO");
                registro[2] = rs.getString("DESCRIPCION");
                modelo.addRow(registro);
            }

            tablaEntidades.setModel(modelo);
            pst.close();
        } catch (SQLException ex) {
            ControlMensajes.error("tiposTarjeta: " + ex, ContTcCobrosCaja.class.getName());
        } finally {
            cnRs.cerrarConexion();
        }

    }

    private void formatoColumnas() {
        tablaEntidades.getColumnModel().getColumn(0).setMinWidth(0);
        tablaEntidades.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaEntidades.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private void mandarDatos(int id, String desc) {
        FrmPosCobros.idEntidad = id;
        FrmPosCobros.txtProcesadora.setText(desc);

        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEntidades = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("..::TIPOS DE TARJETAS::..");
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        tablaEntidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICADOR", "DESCRIPCION"
            }
        ));
        tablaEntidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaEntidadesMousePressed(evt);
            }
        });
        tablaEntidades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaEntidadesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEntidades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaEntidadesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaEntidadesKeyPressed
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                int fila = tablaEntidades.getSelectedRow();

                int id = Integer.parseInt(tablaEntidades.getValueAt(fila, 0).toString());
                String desc = tablaEntidades.getValueAt(fila, 1).toString();
                mandarDatos(id, desc);
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
            }
        } catch (NumberFormatException ex) {

        }
    }//GEN-LAST:event_tablaEntidadesKeyPressed

    private void tablaEntidadesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEntidadesMousePressed
        if (evt.getClickCount() == 2) {
            int fila = tablaEntidades.rowAtPoint(evt.getPoint());

            int id = Integer.parseInt(tablaEntidades.getValueAt(fila, 0).toString());
            String desc = tablaEntidades.getValueAt(fila, 1).toString();
            mandarDatos(id, desc);
        }
    }//GEN-LAST:event_tablaEntidadesMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVistaEntidadesEmisoras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEntidades;
    // End of variables declaration//GEN-END:variables

}
