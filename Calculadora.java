// Autor: Emerson da Silva Oliveira

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    private JTextField visor;
    private double num1 = 0, num2 = 0, resultado = 0;
    private String operador = "";
    private boolean novaOperacao = true;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Visor da calculadora
        visor = new JTextField("0");
        visor.setEditable(false);
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setFont(new Font("Arial", Font.BOLD, 22));
        add(visor, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 5, 5));

        String[] botoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "", "", ""
        };

        for (String texto : botoes) {
            if (!texto.isEmpty()) {
                JButton botao = new JButton(texto);
                botao.setFont(new Font("Arial", Font.BOLD, 16));
                botao.addActionListener(this);
                painelBotoes.add(botao);
            } else {
                painelBotoes.add(new JLabel(""));
            }
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            // Tratamento de números e ponto decimal
            if ((comando.charAt(0) >= '0' && comando.charAt(0) <= '9') || comando.equals(".")) {
                if (novaOperacao) {
                    visor.setText(comando);
                    novaOperacao = false;
                } else {
                    if (comando.equals(".") && visor.getText().contains(".")) return;
                    visor.setText(visor.getText() + comando);
                }
            } 
            // Operadores (+, -, *, /)
            else if (comando.equals("+") || comando.equals("-") || comando.equals("*") || comando.equals("/")) {
                num1 = Double.parseDouble(visor.getText());
                operador = comando;
                novaOperacao = true;
            } 
            // Botão de igualdade (=)
            else if (comando.equals("=")) {
                num2 = Double.parseDouble(visor.getText());
                switch (operador) {
                    case "+": resultado = num1 + num2; break;
                    case "-": resultado = num1 - num2; break;
                    case "*": resultado = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            throw new ArithmeticException("Erro: Divisão por zero não permitida!");
                        }
                        resultado = num1 / num2;
                        break;
                    default: resultado = num2;
                }
                visor.setText(String.valueOf(resultado));
                novaOperacao = true;
            } 
            // Botão "C" (Clear) limpa a memória e o visor
            else if (comando.equals("C")) {
                num1 = 0;
                num2 = 0;
                resultado = 0;
                operador = "";
                visor.setText("0");
                novaOperacao = true;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato numérico!", "Erro", JOptionPane.ERROR_MESSAGE);
            visor.setText("0");
            novaOperacao = true;
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro Matemático", JOptionPane.ERROR_MESSAGE);
            visor.setText("0");
            novaOperacao = true;
        } finally {
            // Bloco finally obrigatório conforme enunciado
            System.out.println("Operação executada / processada no visor.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}