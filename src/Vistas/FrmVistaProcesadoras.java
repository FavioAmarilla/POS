package Vistas;

import Utils.Utilidades;
import Controladores.ContTcCobrosCaja;


import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmVistaProcesadoras extends javax.swing.JDialog {

   
    ContTcCobrosCaja voucher = new ContTcCobrosCaja();
    DefaultTableModel modelo = new DefaultTableModel();

    public FrmVistaProcesadoras() {
        initComponents();
        setModal(true);
        this.setLocationRelativeTo(null);
        addProcesadoras();
        formatoColumnas();
    }

    private void addProcesadoras() {
        modelo = voucher.procesadoras();
        tablaProcesadoras.setModel(modelo);
    }

    private void formatoColumnas(){
        tablaProcesadoras.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProcesadoras.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaProcesadoras.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    private void mandarDatos(int id, String desc) {
        FrmPosCobros.idProcesadora = id;
        FrmPosCobros.txtProcesadora.setText(desc);
        FrmPosCobros.txtTipoTarjeta.requestFocus();
        
        
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProcesadoras = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("..::PROCESADORAS::..");
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        tablaProcesadoras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICADOR", "DESCRIPCION"
            }
        ));
        tablaProcesadoras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaProcesadorasMousePressed(evt);
            }
        });
        tablaProcesadoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProcesadorasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProcesadoras);

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

    private void tablaProcesadorasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProcesadorasKeyPressed
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                int fila = tablaProcesadoras.getSelectedRow();

                int id = Integer.parseInt(tablaProcesadoras.getValueAt(fila, 0).toString());
                String desc = tablaProcesadoras.getValueAt(fila, 1).toString();
                mandarDatos(id, desc);
            }else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
            }
        } catch (NumberFormatException ex) {

        }
    }//GEN-LAST:event_tablaProcesadorasKeyPressed

    private void tablaProcesadorasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProcesadorasMousePressed
        if (evt.getClickCount() == 2) {
            int fila = tablaProcesadoras.rowAtPoint(evt.getPoint());

            int id = Integer.parseInt(tablaProcesadoras.getValueAt(fila, 0).toString());
            String desc = tablaProcesadoras.getValueAt(fila, 1).toString();
            mandarDatos(id, desc);
        }
    }//GEN-LAST:event_tablaProcesadorasMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVistaProcesadoras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProcesadoras;
    // End of variables declaration//GEN-END:variables


}
