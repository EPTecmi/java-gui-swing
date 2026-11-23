import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AutoresApp extends JFrame {

    // Campos de captura
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;

    // Tabla de autores
    private JTable tabla;
    private DefaultTableModel modelo;

    // Mensaje de estado
    private JLabel lblEstado;

    public AutoresApp() {

        setTitle("Sistema de Gestión de Autores");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirInterfaz();
    }

    private void construirInterfaz() {

        setLayout(new BorderLayout(10, 10));

        // Panel de captura
        JPanel panelFormulario = new JPanel(
            new GridLayout(3, 2, 10, 10)
        );

        panelFormulario.setBorder(
            BorderFactory.createTitledBorder("Datos del autor")
        );

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();

        panelFormulario.add(new JLabel("ID del autor:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido paterno:"));
        panelFormulario.add(txtApellido);

        add(panelFormulario, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(
            new FlowLayout()
        );

        JButton btnGuardar = new JButton("Guardar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        modelo = new DefaultTableModel(
            new String[]{
                "ID", "Nombre", "Apellido paterno"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelCentral = new JPanel(new BorderLayout());

        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Estado
        lblEstado = new JLabel("Sistema listo");

        add(lblEstado, BorderLayout.SOUTH);

        // Eventos de los botones
        btnGuardar.addActionListener(e -> guardarAutor());

        btnModificar.addActionListener(e -> modificarAutor());

        btnEliminar.addActionListener(e -> eliminarAutor());

        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Evento de selección de tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                seleccionarAutor();
            }
        });

        // Datos iniciales
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {

        modelo.addRow(new Object[]{
            1, "Harvey", "Deitel"
        });

        modelo.addRow(new Object[]{
            2, "Paul", "Deitel"
        });

        modelo.addRow(new Object[]{
            3, "Tem", "Nieto"
        });

        modelo.addRow(new Object[]{
            4, "Sean", "Santry"
        });
    }

    private void guardarAutor() {

        try {

            int id = Integer.parseInt(txtId.getText().trim());

            String nombre = txtNombre.getText().trim();

            String apellido = txtApellido.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios."
                );

                return;
            }

            if (buscarFila(id) != -1) {

                JOptionPane.showMessageDialog(
                    this,
                    "El identificador del autor ya existe."
                );

                return;
            }

            modelo.addRow(new Object[]{
                id, nombre, apellido
            });

            lblEstado.setText("Autor registrado correctamente.");

            limpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "El ID debe ser un número entero."
            );
        }
    }

    private void modificarAutor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecciona un autor de la tabla."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();

        String apellido = txtApellido.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "El nombre y apellido son obligatorios."
            );

            return;
        }

        modelo.setValueAt(nombre, fila, 1);

        modelo.setValueAt(apellido, fila, 2);

        lblEstado.setText("Autor modificado correctamente.");

        limpiarCampos();
    }

    private void eliminarAutor() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecciona un autor para eliminar."
            );

            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Deseas eliminar este autor?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            modelo.removeRow(fila);

            lblEstado.setText("Autor eliminado correctamente.");

            limpiarCampos();
        }
    }

    private void seleccionarAutor() {

        int fila = tabla.getSelectedRow();

        if (fila != -1) {

            txtId.setText(
                modelo.getValueAt(fila, 0).toString()
            );

            txtNombre.setText(
                modelo.getValueAt(fila, 1).toString()
            );

            txtApellido.setText(
                modelo.getValueAt(fila, 2).toString()
            );
        }
    }

    private int buscarFila(int id) {

        for (int i = 0; i < modelo.getRowCount(); i++) {

            int idActual = Integer.parseInt(
                modelo.getValueAt(i, 0).toString()
            );

            if (idActual == id) {
                return i;
            }
        }

        return -1;
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");

        tabla.clearSelection();

        lblEstado.setText("Campos limpios.");

        txtId.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AutoresApp ventana = new AutoresApp();

            ventana.setVisible(true);
        });
    }
}