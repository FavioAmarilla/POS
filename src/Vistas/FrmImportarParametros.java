package Vistas;

import Controladores.ContAppVtaPromociones;
import Controladores.ContAppVtaPromocionesProv;
import Controladores.ContFndFormasPago;
import Controladores.ContPosAplicNovedades;
import Controladores.ContFndTurnosTrabajo;
import Controladores.ContParmEmpresas;
import Controladores.ContVtaControlComprob;
import Controladores.ContTcCajas;
import Controladores.ContPrProductos;
import Controladores.ContFndMonedas;
import Utils.Utilidades;
import Utils.ControlMensajes;
import Controladores.ContVtaClientes;
import Controladores.ContFndUsuarios;
import Controladores.ContTcDenomMoneda;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class FrmImportarParametros extends javax.swing.JDialog {

    private final String titulo = "Importar parametros";

    ContFndFormasPago contFormasPago = new ContFndFormasPago();
    ContTcDenomMoneda contDenomMoneda = new ContTcDenomMoneda();

    ContFndTurnosTrabajo turno = new ContFndTurnosTrabajo();
    ContVtaClientes cliente = new ContVtaClientes();
    ContFndUsuarios usuario = new ContFndUsuarios();
    ContFndMonedas moneda = new ContFndMonedas();
    ContVtaControlComprob comprobante = new ContVtaControlComprob();
    ContTcCajas caja = new ContTcCajas();
    ContPrProductos producto = new ContPrProductos();
    ContParmEmpresas empresa = new ContParmEmpresas();
    ContPosAplicNovedades novedades = new ContPosAplicNovedades();
    ContAppVtaPromociones contAppVtaPromociones = new ContAppVtaPromociones();
    ContAppVtaPromocionesProv contAppVtaPromocionesProv = new ContAppVtaPromocionesProv();

    public String formulario = "";

    public FrmImportarParametros() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        setModal(true);
        this.setTitle(titulo);
    }

    private boolean menu(int opc) {
        if (!Utilidades.getPing()) {
            ControlMensajes.error("Sin conexion a servidor", titulo);
            return false;
        }

        switch (opc) {
            case 0:
                if (empresa.imp_param_empresa()) {
                    if (empresa.imp_sitios()) {
                        if (empresa.imp_unidades_operativas()) {
                            if (empresa.impPeriodos()) {
                                if (empresa.imp_aplic_novedades()) {
                                    if (moneda.importarMonedas()) {
                                        if (contDenomMoneda.importarDenominaciones()) {
                                            if (caja.imp_tc_cajas()) {
                                                if (caja.imp_puntos_emision()) {
                                                    if (contFormasPago.importarFormasPago()) {
                                                        if (caja.impConceptosArqueo()) {
                                                            if (turno.impTurnosTrabajo()) {
                                                                if (turno.impTurnosCaja()) {
                                                                    if (comprobante.importarControlComprob()) {
                                                                        if (comprobante.importarAsignacionComp()) {
                                                                            if (comprobante.importarTiposComprob()) {
                                                                                if (producto.novedades("PR_PRODUCTOS") && producto.novedades("PR_UM_PRODUCTO")) {
                                                                                    if (producto.importarPreciosAplicar()) {
                                                                                        if (producto.importarCategoriasProd()) {
                                                                                            if (usuario.impPerfiles()) {
                                                                                                if (usuario.impUsuarios()) {
                                                                                                    if (usuario.impCajeros()) {
                                                                                                        if (cliente.novedades()) {
                                                                                                            if (caja.impProcesadoras()) {
                                                                                                                if (caja.impTiposTarjetas()) {
                                                                                                                    if (ContFndUsuarios.USR_NOMBRE.equals("USRINSTALL")) {
                                                                                                                        if (empresa.valPosAplicNovedades()) {
                                                                                                                            if (novedades.getAplicNovedades()) {
                                                                                                                                if (contAppVtaPromociones.importarPromociones(1)) {
                                                                                                                                    if (contAppVtaPromocionesProv.importarPromociones(1)) {
                                                                                                                                        ControlMensajes.informacion("Promociones importadas", titulo);
                                                                                                                                    } else {
                                                                                                                                        ControlMensajes.informacion("Promociones por proveedor no importado", titulo);
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    ControlMensajes.informacion("Promociones no importadas", titulo);
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                                                                                                                            }
                                                                                                                        } else if (empresa.imp_aplic_novedades()) {
                                                                                                                            if (novedades.getAplicNovedades()) {
                                                                                                                                ControlMensajes.informacion("Parametros Importados", titulo);
                                                                                                                            } else {
                                                                                                                                ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                                                                                                                        }
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    ControlMensajes.error("Tipos de tarjetas no importados", titulo);
                                                                                                                }
                                                                                                            } else {
                                                                                                                ControlMensajes.error("Procesadoras de tarjetas no importadas", titulo);
                                                                                                            }

                                                                                                        } else {
                                                                                                            ControlMensajes.error("Clientes no importados", titulo);
                                                                                                        }
                                                                                                    } else {
                                                                                                        ControlMensajes.error("Cajeros no importados", titulo);
                                                                                                    }
                                                                                                } else {
                                                                                                    ControlMensajes.error("Usuarios no importados", titulo);
                                                                                                }
                                                                                            } else {
                                                                                                ControlMensajes.error("Perfiles no importados", titulo);
                                                                                            }
                                                                                        } else {
                                                                                            ControlMensajes.error("Categorias de prod. no importadas", titulo);
                                                                                        }
                                                                                    } else {
                                                                                        ControlMensajes.error("Precios no actualizados", titulo);
                                                                                    }
                                                                                } else {
                                                                                    ControlMensajes.error("Productos no importados", titulo);
                                                                                }
                                                                            } else {
                                                                                ControlMensajes.error("Tipos de comprobantes no importados", titulo);
                                                                            }
                                                                        } else {
                                                                            ControlMensajes.error("Asignacion de comprobantes no importados", titulo);
                                                                        }
                                                                    } else {
                                                                        ControlMensajes.error("Control de comprobantes no importados", titulo);
                                                                    }
                                                                } else {
                                                                    ControlMensajes.error("Turnos de caja no importados", titulo);
                                                                }
                                                            } else {
                                                                ControlMensajes.error("Turnos de trabajo no importados", titulo);
                                                            }
                                                        } else {
                                                            ControlMensajes.error("Conceptos de arqueo no importados", titulo);
                                                        }
                                                    } else {
                                                        ControlMensajes.error("Formas de pago no importados", titulo);
                                                    }
                                                } else {
                                                    ControlMensajes.error("Puntos de emision no importados", titulo);
                                                }
                                            } else {
                                                ControlMensajes.error("Cajas no importadas", titulo);
                                            }
                                        } else {
                                            ControlMensajes.error("Denominaciones no importadas", titulo);
                                        }
                                    } else {
                                        ControlMensajes.error("Monedas no importadas", titulo);
                                    }
                                } else {
                                    ControlMensajes.error("Aplic. de novedades no imporatadas", titulo);
                                }
                            } else {
                                ControlMensajes.error("Periodo administrativo no importado", titulo);
                            }
                        } else {
                            ControlMensajes.error("Unidades operat. no importadas", titulo);
                        }
                    } else {
                        ControlMensajes.error("Sucursales no importadas", titulo);
                    }
                } else {
                    ControlMensajes.error("Empresas no importadas", titulo);
                }
                break;
            case 1:
                if (!empresa.imp_param_empresa()) {
                    ControlMensajes.error("Empresas no importadas", titulo);
                    return false;
                }

                if (!empresa.imp_sitios()) {
                    ControlMensajes.error("Sucursales no importadas", titulo);
                    return false;
                }

                if (!empresa.impPeriodos()) {
                    ControlMensajes.error("Periodo administrativo no importado", titulo);
                    return false;
                }

                ControlMensajes.informacion("Parametros de empresa importadas", titulo);
                break;

            case 2:
                if (!caja.imp_tc_cajas()) {
                    ControlMensajes.error("Cajas no importadas", titulo);
                    return false;
                }
                if (!caja.imp_puntos_emision()) {
                    ControlMensajes.error("Denominaciones no importadas", titulo);
                    return false;
                }
                if (!contFormasPago.importarFormasPago()) {
                    ControlMensajes.error("Formas de pago no importados", titulo);
                    return false;
                }
                if (!moneda.importarMonedas()) {
                    ControlMensajes.error("Monedas no importadas", titulo);
                    return false;
                }
                if (!caja.impConceptosArqueo()) {
                    ControlMensajes.error("Conceptos de arqueo no importados", titulo);
                    return false;
                }
                if (!contDenomMoneda.importarDenominaciones()) {
                    ControlMensajes.informacion("Denominaciones no importadas", titulo);
                    return false;
                }
                if (!empresa.imp_aplic_novedades()) {
                    ControlMensajes.informacion("Aplic. de novedades no importadas", titulo);
                    return false;
                }
                if (!turno.impTurnosTrabajo()) {
                    ControlMensajes.error("Turnos de trabajo no importados", titulo);
                    return false;
                }
                if (!turno.impTurnosCaja()) {
                    ControlMensajes.error("Turnos de caja no importados", titulo);
                    return false;
                }
                if (!contFormasPago.importarFormasPago()) {
                    ControlMensajes.error("Formas de pagos no importados", titulo);
                    return false;
                }
                if (!caja.impProcesadoras()) {
                    ControlMensajes.error("Procesadoras de tarjetas no importadas", titulo);
                    return false;
                }
                if (!caja.impTiposTarjetas()) {
                    ControlMensajes.error("Tipos de tarjetas no importados", titulo);
                    return false;
                }

                ControlMensajes.informacion("Parametros de cajas importados", titulo);

                break;

            case 3:
                if (!comprobante.importarControlComprob()) {
                    ControlMensajes.error("Control de comprobantes no importados", titulo);
                    return false;
                }
                if (!comprobante.importarAsignacionComp()) {
                    ControlMensajes.error("Asignacion de comprobantes no importados", titulo);
                    return false;
                }
                if (!comprobante.importarTiposComprob()) {
                    ControlMensajes.error("Tipos de comprobantes no importados", titulo);
                    return false;
                }
                ControlMensajes.informacion("Parametros del comprobante importados", titulo);

                break;

            case 4:
                if (ContFndUsuarios.USR_NOMBRE.equals("USRINSTALL")) {
                    if (empresa.valPosAplicNovedades()) {
                        if (novedades.getAplicNovedades()) {
                            if (producto.novedades("PR_PRODUCTOS") && producto.novedades("PR_UM_PRODUCTO")) {
                                if (producto.importarPreciosAplicar()) {
                                    ControlMensajes.informacion("Productos importados", titulo);
                                } else {
                                    ControlMensajes.error("Productos no importados", titulo);
                                }
                            } else {
                                ControlMensajes.error("Precios no actualizados", titulo);
                            }
                        } else {
                            ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                        }
                    } else if (empresa.imp_aplic_novedades()) {
                        if (novedades.getAplicNovedades()) {
                            if (producto.novedades("PR_PRODUCTOS") && producto.novedades("PR_UM_PRODUCTO")) {
                                if (producto.importarPreciosAplicar()) {
                                    ControlMensajes.informacion("Productos importados", titulo);
                                } else {
                                    ControlMensajes.error("Productos no importados", titulo);
                                }
                            } else {
                                ControlMensajes.error("Precios no actualizados", titulo);
                            }
                        } else {
                            ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                        }
                    } else {
                        ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                    }
                } else if (producto.novedades("PR_PRODUCTOS") && producto.novedades("PR_UM_PRODUCTO")) {
                    if (producto.importarPreciosAplicar()) {
                        if (producto.importarCategoriasProd()) {
                            ControlMensajes.informacion("Productos importados", titulo);
                        } else {
                            ControlMensajes.error("Categorias de prod. no importadas", titulo);
                        }
                    } else {
                        ControlMensajes.error("Productos no importados", titulo);
                    }
                } else {
                    ControlMensajes.error("Productos no importados", titulo);
                }
                break;

            case 5:
                if (!usuario.impPerfiles()) {
                    ControlMensajes.error("Perfiles no importados", titulo);
                    return false;
                }
                if (!usuario.impUsuarios()) {
                    ControlMensajes.error("Usuarios no importados", titulo);
                    return false;
                }
                if (!usuario.impCajeros()) {
                    ControlMensajes.error("Cajeros no importados", titulo);
                    return false;
                }

                ControlMensajes.informacion("Usuarios importados", titulo);

                break;

            case 6:
                if (ContFndUsuarios.USR_NOMBRE.equals("USRINSTALL")) {
                    if (empresa.valPosAplicNovedades()) {
                        if (novedades.getAplicNovedades()) {
                            if (cliente.novedades()) {
                                ControlMensajes.informacion("Clientes importados", titulo);
                            } else {
                                ControlMensajes.error("Clientes no importados", titulo);
                            }
                        } else {
                            ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                        }
                    } else if (empresa.imp_aplic_novedades()) {
                        if (novedades.getAplicNovedades()) {
                            if (cliente.novedades()) {
                                ControlMensajes.informacion("Clientes importados", titulo);
                            } else {
                                ControlMensajes.error("Clientes no importados", titulo);
                            }
                        } else {
                            ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                        }
                    } else {
                        ControlMensajes.error("Aplic. de novedades no importadas", titulo);
                    }
                } else if (cliente.novedades()) {
                    ControlMensajes.informacion("Clientes importados", titulo);
                } else {
                    ControlMensajes.error("Clientes no importados", titulo);
                }
                break;

            case 7:
                if (!empresa.impPeriodos()) {
                    ControlMensajes.error("Periodo administrativo no importado", titulo);
                    return false;
                }
                ControlMensajes.informacion("Periodo administrativo importado", titulo);
                break;

            case 8:
                if (contAppVtaPromociones.importarPromociones(1)) {
                    if (contAppVtaPromocionesProv.importarPromociones(1)) {
                        ControlMensajes.informacion("Promociones importadas", titulo);
                    } else {
                        ControlMensajes.informacion("Promociones por proveedor no importado", titulo);
                        return false;
                    }
                } else {
                    ControlMensajes.informacion("Promociones por monto no importada", titulo);
                    return false;
                }
                break;
        }

        return true;
    }

    private void btnImportar() {
        System.out.println("<INICIO PROCESO DE IMPORTACION>");
        menu(cboOpciones.getSelectedIndex());
        cboOpciones.requestFocus();
        System.out.println("<FIN PROCESO DE IMPORTACION>");
    }

    private void btnSalir() {
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnImportar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        cboOpciones = new javax.swing.JComboBox<>();

        setTitle(".::IMPORTAR PARAMETROS::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnImportar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnImportar.setText("Aceptar");
        btnImportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImportarMouseClicked(evt);
            }
        });
        btnImportar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnImportarKeyPressed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        btnSalir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalirKeyPressed(evt);
            }
        });

        cboOpciones.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cboOpciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los Parametros", "Parametros de empresa", "Parametros de Caja", "Parametros de Comprobante", "Productos", "Usuario", "Clientes", "Periodo Administrativo", "Promociones" }));
        cboOpciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboOpcionesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboOpciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(cboOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImportarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnImportarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnImportar();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnImportarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSalir();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void cboOpcionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboOpcionesKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnImportar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_cboOpcionesKeyPressed

    private void btnImportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportarMouseClicked
        if (evt.getClickCount() == 1) {
            btnImportar();
        }
    }//GEN-LAST:event_btnImportarMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            btnSalir();
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btnSalir();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmImportarParametros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboOpciones;
    // End of variables declaration//GEN-END:variables

}
