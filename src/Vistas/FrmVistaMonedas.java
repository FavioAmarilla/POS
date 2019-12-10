package Vistas;

import Conexiones.ConexionRs;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmVistaMonedas extends javax.swing.JDialog {

   
    ConexionRs cnRs = new ConexionRs();

    public FrmVistaMonedas() {
        initComponents();
        setModal(true);
        addcontMonedas();
        tablacontMonedas.getTableHeader().setReorderingAllowed(false);
        formatoColumnas();
    }

    private void addcontMonedas() {
        try {
            DefaultTableModel modelo;
            String[] titulos = {"IDENTIFICADOR", "CODIGO", "DESCRIPCION"};
            String[] registro = new String[3];
            modelo = new DefaultTableModel(null, titulos);
            ResultSet rs;
            rs = cnRs.consultar("SELECT IDENTIFICADOR, CODIGO, DESCRIPCION FROM FND_MONEDAS ORDER BY IDENTIFICADOR ASC");

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("CODIGO");
                registro[2] = rs.getString("DESCRIPCION");
                modelo.addRow(registro);
            }

            tablacontMonedas.setModel(modelo);
            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(FrmVistaMonedas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void formatoColumnas() {
        tablacontMonedas.getColumnModel().getColumn(0).setMinWidth(0);
        tablacontMonedas.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablacontMonedas.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private void mandarDatos(int id, String codigo) {
        FrmPosCobros.idMoneda = id;
        FrmPosCobros.txtMoneda.setText(codigo);

        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablacontMonedas = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::contMonedas.::");
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        tablacontMonedas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICADOR", "CODIGO", "DESCRIPCION"
            }
        ));
        tablacontMonedas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablacontMonedasMousePressed(evt);
            }
        });
        tablacontMonedas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacontMonedasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablacontMonedas);

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

    private void tablacontMonedasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacontMonedasKeyPressed
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                int fila = tablacontMonedas.getSelectedRow();

                int id = Integer.parseInt(tablacontMonedas.getValueAt(fila, 0).toString());
                String codigo = tablacontMonedas.getValueAt(fila, 1).toString();
                mandarDatos(id, codigo);
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
            }
        } catch (NumberFormatException ex) {

        }
    }//GEN-LAST:event_tablacontMonedasKeyPressed

    private void tablacontMonedasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacontMonedasMousePressed
        if (evt.getClickCount() == 2) {
            int fila = tablacontMonedas.rowAtPoint(evt.getPoint());

            int id = Integer.parseInt(tablacontMonedas.getValueAt(fila, 0).toString());
            String codigo = tablacontMonedas.getValueAt(fila, 1).toString();
            mandarDatos(id, codigo);
        }
    }//GEN-LAST:event_tablacontMonedasMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVistaMonedas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablacontMonedas;
    // End of variables declaration//GEN-END:variables

}
