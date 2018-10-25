package mensaje;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import static java.awt.image.ImageObserver.ERROR;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberto
 */
public class vista_mensaje2 extends javax.swing.JFrame {

    int caracteres = 32;
    private OutputStream Output = null;
    SerialPort serialPort; 
    private final String PORT_NAME = "COM5";
    private static final int TIME_OUT = 2000; 
    private static final int DATA_RATE = 9600;
    
    //TEMP
    InputStream in = null;
    int temperatura=10;
    Thread timer;
    
    public vista_mensaje2() {
        initComponents();
        ArduinoConnection();
        letras();        
    }
    
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

        } catch (Exception e) {

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
    
    public void letras() {
        caracteres = 32 - txtMensaje.getText().length(); //Indica la cantidad de caracteres
        //disponibles. En el LCD solo se permite imprimir 32 caracteres.

        if (caracteres <= 0) { //Si la cantidad de caracteres se ha //agotado...
        labelCaracter.setText("Caracteres disponibles: 0"); //Se imprime que la cantidad de caracteres disponibles es 0
        String cadena = ""; //Se declara la variable que guardará el //mensaje a enviar
        cadena = txtMensaje.getText(); //Se asigna el //texto del TextField a la variable cadena
        cadena = cadena.substring(0, 32); //Se evita que por //alguna razón la //variable contenga
        //más de 32 caracteres, utilizando el substring que crea un //string a partir de uno mayor.
        txtMensaje.setText(cadena); //se regresa la //cadena con 32 caracteres al TextField
        } else {
        //Si la cantidad de caracteres disponibles es ayor a 0 solamente //se imprimirá la cantidad de caracteres disponibles
        labelCaracter.setText("Caracteres disponibles: " + (caracteres));
        }
    }

    public void hora(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                // wait after connecting, so the bootloader can finish
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                // enter an infinite loop that sends text to the arduino
                PrintWriter output;
                try { 
                    output = new PrintWriter(serialPort.getOutputStream());
                    while (true) {
                        output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                        output.flush();
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(vista_mensaje2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
    }
    
     public void temperatura(){ 
        serialPort.close();
        try {             
            Output.close();            
        } catch (IOException ex) {
            Logger.getLogger(vista_mensaje2.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.dispose();
            Ventana2 v= new Ventana2();
            v.setVisible(true);
            v.setSize(400,300);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        btnVerMensaje = new javax.swing.JButton();
        btnVerHora = new javax.swing.JButton();
        btnLimpiarMensaje = new javax.swing.JButton();
        btnVerTemperatura = new javax.swing.JButton();
        labelCaracter = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Escribe un Mensaje");
        jLabel1.setToolTipText("Escribe un Mensaje");

        txtMensaje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMensaje.setToolTipText("Escribe un Mensaje");
        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        txtMensaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMensajeKeyReleased(evt);
            }
        });

        btnVerMensaje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVerMensaje.setText("Ver Mensaje");
        btnVerMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerMensajeActionPerformed(evt);
            }
        });

        btnVerHora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVerHora.setText("Ver Hora");
        btnVerHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerHoraActionPerformed(evt);
            }
        });

        btnLimpiarMensaje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLimpiarMensaje.setText("Limpiar Mensaje");
        btnLimpiarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarMensajeActionPerformed(evt);
            }
        });

        btnVerTemperatura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVerTemperatura.setText("Ver Temperatura");
        btnVerTemperatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTemperaturaActionPerformed(evt);
            }
        });

        labelCaracter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnVerMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLimpiarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnVerTemperatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnVerHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(labelCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelCaracter, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerMensaje)
                    .addComponent(btnVerHora))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiarMensaje)
                    .addComponent(btnVerTemperatura))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarMensajeActionPerformed
        txtMensaje.setText("");
        letras();
    }//GEN-LAST:event_btnLimpiarMensajeActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        EnviarDatos(txtMensaje.getText());
        txtMensaje.setText(""); 
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void btnVerMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerMensajeActionPerformed
        EnviarDatos(txtMensaje.getText());
        txtMensaje.setText("");
        letras();
                
           Thread thread = new Thread() {
            @Override
            public void run() {                
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }                                                                   
                    while (true) {                        
                        hora();
                        try { 
                            Thread.sleep(5000); 
                        } catch (Exception e) {
                        }
                    }                
            }
        };
        thread.start();  
    }//GEN-LAST:event_btnVerMensajeActionPerformed

    private void txtMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMensajeKeyReleased
        letras();
    }//GEN-LAST:event_txtMensajeKeyReleased

    private void btnVerHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerHoraActionPerformed
        hora();
        letras();
    }//GEN-LAST:event_btnVerHoraActionPerformed

    private void btnVerTemperaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTemperaturaActionPerformed
        temperatura();
    }//GEN-LAST:event_btnVerTemperaturaActionPerformed

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
            java.util.logging.Logger.getLogger(vista_mensaje2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vista_mensaje2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vista_mensaje2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vista_mensaje2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vista_mensaje2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiarMensaje;
    private javax.swing.JButton btnVerHora;
    private javax.swing.JButton btnVerMensaje;
    private javax.swing.JButton btnVerTemperatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelCaracter;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
