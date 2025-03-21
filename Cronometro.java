import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cronometro extends JFrame
{
    private JLabel tempoLabel;
    private JButton iniciarButton, pausarButton, resetarButton;
    private Timer timer;
    private int horas = 0, minutos = 0, segundos = 0, milisegundos = 0;
    private boolean rodando = false;

    public Cronometro()
    {
        // Configurações da janela
        super("Cronômetro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicialização do rótulo de tempo
        tempoLabel = new JLabel("00:00:00:000", JLabel.CENTER);
        tempoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(tempoLabel, BorderLayout.CENTER);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão de iniciar
        iniciarButton = new JButton("Iniciar");
        iniciarButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                iniciar();
            }
        });

        // Botão de pausar
        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pausar();
            }
        });

        // Botão de resetar
        resetarButton = new JButton("Resetar");
        resetarButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resetar();
            }
        });

        // Adicionar os botões ao painel
        buttonPanel.add(iniciarButton);
        buttonPanel.add(pausarButton);
        buttonPanel.add(resetarButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Configuração do timer (atualização a cada 10 milisegundos)
        timer = new Timer(10, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                atualizarTempo();
            }
        });

        setVisible(true);

        }

    private void iniciar()
        {
            if (!rodando)
            {
                rodando = true;
                timer.start();
            }
        }

    private void pausar()
        {
            if (rodando) 
            {
                rodando = false;
                timer.stop();
            }
        }

    private void resetar()
        {
            timer.stop();
            rodando = false;
            horas = 0;
            minutos = 0;
            segundos = 0;
            milisegundos = 0;
            atualizarDisplay();
        }

    private void atualizarTempo()
    {
        milisegundos += 10;

        if (milisegundos >= 1000) 
        {
            segundos++;
            milisegundos = 0;

            if (segundos >= 60)
            {
                minutos++;
                segundos = 0;

                if (minutos >= 60)
                {
                    horas++;
                    minutos = 0;
                }
            }
        }

        atualizarDisplay();
    }

    private void atualizarDisplay()
    {
        String tempo = String.format("%02d:%02d:%02d:%03d", horas, minutos, segundos, milisegundos);
        tempoLabel.setText(tempo);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    new Cronometro();
                }
            
        });
    }
  
}