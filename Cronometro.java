import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cronometro extends JFrame
{
    private JLabel tempoLabel;
    private JButton iniciarButton, pausarButton, resetarButton;
    private Timer timer;
    private long startTime;
    private long elapsedTime;
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

        // Configuração do timer (atualização a cada 1 milisegundo)
        timer = new Timer(1, new ActionListener()
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
            startTime = System.nanoTime() - elapsedTime;
            timer.start();
        }
    }

    private void pausar()
    {
        if (rodando) 
        {
            rodando = false;
            timer.stop();
            elapsedTime = System.nanoTime() - startTime;
        }
    }

    private void resetar()
    {
        timer.stop();
        rodando = false;
        elapsedTime = 0;
        atualizarDisplay();
    }

    private void atualizarTempo()
    {
        if (rodando)
        {
            elapsedTime = System.nanoTime() - startTime;
            atualizarDisplay();
        }
    }

    private void atualizarDisplay()
    {
        long totalMillis = elapsedTime / 1_000_000; // Convert nanoseconds to milliseconds
        long horas = totalMillis / (60 * 60 * 1000);
        long minutos = (totalMillis % (60 * 60 * 1000)) / (60 * 1000);
        long segundos = (totalMillis % (60 * 1000)) / 1000;
        long milisegundos = totalMillis % 1000;

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