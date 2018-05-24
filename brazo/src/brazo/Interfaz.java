/*
 * 
 */
package brazo;


import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Interfaz extends javax.swing.JFrame {

    //variables para la comunicacion con el arduino por serial
    private OutputStream Output = null;         //Variable que se usara de salida para mandar por serial
    SerialPort serialPort;                      //Variable que servira para obtener el puerto serial
    private final String PORT_NAME = "COM9";    //Variable donde se coloca el número de puerto que se usara
    private static final int TIME_OUT = 2000;   //Variable que contendrá el tiempo de salida
    private static final int DATA_RATE = 9600;  //Variable que inicializa el puerto serial
   
    //variables para el control manual del brazo
    String mo1, mo2, mo3, mo4;
    //variable que almaneca el programa a mandar al brazo para con control
    //automatico
    String programa="A";
    //variable auxiliar del modo automatico para limitar los pasos del
    //programa a 10 pasos max
    int pasos=0;

    public void ArduinoConnection() {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            if (PORT_NAME.equals(currPortId.getName())) {
                portId = currPortId;
                break;
            }
        }

        if (portId == null) {

            System.exit(ERROR);
            return;
        }

        try {

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            Output = serialPort.getOutputStream();

        } catch (PortInUseException | UnsupportedCommOperationException | IOException e) {
            System.out.println(e.getMessage());
            System.exit(ERROR);
        }

    }

    private void EnviarDatos(String data) {

        try {

            Output.write(data.getBytes());

        } catch (IOException e) {

            System.exit(ERROR);
        }
    }


    public Interfaz() {
        initComponents();
        pnManual.setVisible(false);
        setLocationRelativeTo(null);                    //la interfaz se localizará de manera realtiva, o sea cualquier posición
        setResizable(false);                            //que permita colocar algun objeto debajo
        setTitle("Brazo Robotico");                        // se le coloca nombre a la barra superior de la pantalla desplegada

        ((JPanel) getContentPane()).setOpaque(false);    //se crea un panel que no contendra un color de fondo
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/imagenes/brazo.jpeg")); // se hace la llamada a la imagen
        JLabel fondo = new JLabel();                     // se crea un objeto para el JLabel
        fondo.setIcon(uno);                             // dentro del label se insertara la imagen que se obtuvo            
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);   //se posiciona la imagen en todo el fondodel frame 
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());    //la imagen se posiciona en las coordenadas 0,0 

         ArduinoConnection();  
    }

    public void moto1() {
        mo1 = String.valueOf(slPinza.getValue());
        int mp = Integer.parseInt(mo1);
        if (mp < 10) {
            mo1 = "00" + mo1;
        } else if (mp < 100) {
            mo1 = "0" + mo1;

        }

    }

    public void moto2() {
        mo2 = String.valueOf(slMuneca.getValue());
        int mm = Integer.parseInt(mo2);
        if (mm < 10) {
            mo2 = "00" + mo2;
        } else if (mm < 100) {
            mo2 = "0" + mo2;

        }

    }

    public void moto3() {
        mo3 = String.valueOf(slCodo.getValue());
        int mc = Integer.parseInt(mo3);
        if (mc < 10) {
            mo3 = "00" + mo3;
        } else if (mc < 100) {
            mo3 = "0" + mo3;

        }
    }

    public void moto4() {
        mo4 = String.valueOf(slBase.getValue());
        int mb = Integer.parseInt(mo4);
        if (mb < 10) {
            mo4 = "00" + mo4;
        } else if (mb < 100) {
            mo4 = "0" + mo4;

        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPinza = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMuneca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBase = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnAutomatico = new javax.swing.JButton();
        btnManual = new javax.swing.JButton();
        pnManual = new javax.swing.JPanel();
        slPinza = new javax.swing.JSlider();
        slMuneca = new javax.swing.JSlider();
        slCodo = new javax.swing.JSlider();
        slBase = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbM1 = new javax.swing.JLabel();
        lbM2 = new javax.swing.JLabel();
        lbM3 = new javax.swing.JLabel();
        lbM4 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pinza");

        jLabel2.setText("Muñeca");

        jLabel3.setText("Codo");

        jLabel4.setText("Base");

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial Black", 3, 18)); // NOI18N
        jLabel5.setText("Posiciones en grados de los motores ");

        btnAutomatico.setText("Control Automatico");
        btnAutomatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutomaticoActionPerformed(evt);
            }
        });

        btnManual.setText("Control Manuel");
        btnManual.setActionCommand("Control Manual");
        btnManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManualActionPerformed(evt);
            }
        });

        slPinza.setMaximum(180);
        slPinza.setToolTipText("");
        slPinza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slPinzaMouseReleased(evt);
            }
        });

        slMuneca.setMaximum(180);
        slMuneca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slMunecaMouseReleased(evt);
            }
        });

        slCodo.setMaximum(180);
        slCodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slCodoMouseReleased(evt);
            }
        });

        slBase.setMaximum(4);
        slBase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slBaseMouseReleased(evt);
            }
        });

        jLabel6.setText("Pinza");

        jLabel7.setText("Muñeca");

        jLabel8.setText("Codo");

        jLabel9.setText("Base");

        lbM1.setText("Motor 1");

        lbM2.setText("Motor 2");

        lbM3.setText("Motor 3");

        lbM4.setText("Motor 4");

        javax.swing.GroupLayout pnManualLayout = new javax.swing.GroupLayout(pnManual);
        pnManual.setLayout(pnManualLayout);
        pnManualLayout.setHorizontalGroup(
            pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnManualLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnManualLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(slPinza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(pnManualLayout.createSequentialGroup()
                        .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slCodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slMuneca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnManualLayout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(lbM1))
                        .addComponent(lbM2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(lbM4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbM3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnManualLayout.setVerticalGroup(
            pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnManualLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnManualLayout.createSequentialGroup()
                        .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(slPinza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(lbM1))
                        .addGap(18, 18, 18)
                        .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slMuneca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(lbM2))
                .addGap(13, 13, 13)
                .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slCodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lbM3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(slBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(lbM4))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        btnAgregar.setText("Agregar Paso");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnEnviar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(30, 30, 30)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(32, 32, 32)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtBase, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addComponent(txtCodo)
                                .addComponent(txtMuneca)
                                .addComponent(txtPinza)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnManual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(btnAutomatico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(btnManual)
                        .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPinza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMuneca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnManual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnManual)
                            .addComponent(btnAutomatico)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregar)))
                .addGap(18, 18, 18)
                .addComponent(btnEnviar)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
            programa = programa+"*";
        EnviarDatos(programa);
        System.out.println(programa);
        programa="A";
        txtPinza.setText("000");
        txtMuneca.setText("000");
        txtCodo.setText("000");
        txtBase.setText("000");
        pasos=0;
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnAutomaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutomaticoActionPerformed
        pnManual.setVisible(false);
        
        JOptionPane.showMessageDialog(rootPane, "Modo de programacion");
    }//GEN-LAST:event_btnAutomaticoActionPerformed

    private void btnManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManualActionPerformed

        pnManual.setVisible(true);
        JOptionPane.showMessageDialog(rootPane, "MODO MANUAL");
    }//GEN-LAST:event_btnManualActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if(pasos<10){
        programa = programa+
                txtPinza.getText()+
                txtMuneca.getText()+
                txtCodo.getText()+
                txtBase.getText();
        pasos++;
        }else{
            JOptionPane.showMessageDialog(rootPane, "Numero de pasos excedido");
        }
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    
    //MOTOR MANUAL 1
    private void slPinzaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slPinzaMouseReleased
        moto1();
        lbM1.setText(mo1);
        mo1 = "M1"+mo1;
        EnviarDatos(mo1);
        System.out.println(mo1);
    }//GEN-LAST:event_slPinzaMouseReleased

    //MOTOR MANUAL 2
    private void slMunecaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slMunecaMouseReleased
        moto2();
        lbM2.setText(mo2);
        mo2="M2"+mo2;
        EnviarDatos(mo2);
        System.out.println(mo2);
    }//GEN-LAST:event_slMunecaMouseReleased
    
    //MOTOR MANUAL 3
    private void slCodoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slCodoMouseReleased
        moto3();
        lbM3.setText(mo3);
        mo3="M3"+mo3;
        EnviarDatos(mo3);
        System.out.println(mo3);
    }//GEN-LAST:event_slCodoMouseReleased

    //MOTOR MANUAL 4
    private void slBaseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slBaseMouseReleased
        moto4();
        lbM4.setText(mo4);
        mo4="M4"+mo4;
        EnviarDatos(mo4);
        System.out.println(mo4);
    }//GEN-LAST:event_slBaseMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAutomatico;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnManual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbM1;
    private javax.swing.JLabel lbM2;
    private javax.swing.JLabel lbM3;
    private javax.swing.JLabel lbM4;
    private javax.swing.JPanel pnManual;
    private javax.swing.JSlider slBase;
    private javax.swing.JSlider slCodo;
    private javax.swing.JSlider slMuneca;
    private javax.swing.JSlider slPinza;
    private javax.swing.JTextField txtBase;
    private javax.swing.JTextField txtCodo;
    private javax.swing.JTextField txtMuneca;
    private javax.swing.JTextField txtPinza;
    // End of variables declaration//GEN-END:variables
}
