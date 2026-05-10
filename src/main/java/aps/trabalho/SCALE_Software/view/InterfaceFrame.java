package aps.trabalho.SCALE_Software.view;

import aps.trabalho.SCALE_Software.model.Processo;
import aps.trabalho.SCALE_Software.model.Recurso;
import aps.trabalho.SCALE_Software.model.Tipo;
import aps.trabalho.SCALE_Software.service.CalculadoraEmergia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Component
public class InterfaceFrame extends JFrame {

    private final List<Recurso> recursos = new ArrayList<>();
    private final List<Processo> processos = new ArrayList<>();

    private CardLayout cardLayout;
    private JPanel painelConteudo;

    private JTextArea areaResultados;
    private DefaultListModel<String> listaRecursosModel;
    private JLabel statusLabel;

    public InterfaceFrame() {
        configurarJanela();
        initUI();
    }

    private void configurarJanela() {
        setTitle("SCALE - Sistema de Emergia");
        setSize(1250, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        add(criarMenuLateral(), BorderLayout.WEST);
        add(criarPainelPrincipal(), BorderLayout.CENTER);
    }

    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setBackground(new Color(15, 17, 22));
        menu.setPreferredSize(new Dimension(240, getHeight()));
        menu.setLayout(new GridLayout(6, 1, 12, 12));
        menu.setBorder(new EmptyBorder(18, 12, 18, 12));

        JButton btnImportar = botaoMenu("Importar CSV");
        JButton btnCadastro = botaoMenu("Cadastro");
        JButton btnCalcular = botaoMenu("Calcular");
        JButton btnRelatorio = botaoMenu("Relatório");
        JButton btnLimpar = botaoMenu("Limpar Dados");

        btnImportar.addActionListener(e -> {
            mostrarTela("IMPORTAR");
            carregarCSV();
        });

        btnCadastro.addActionListener(e -> mostrarTela("CADASTRO"));

        btnCalcular.addActionListener(e -> {
            mostrarTela("RESULTADOS");
            calcular();
        });

        btnRelatorio.addActionListener(e -> {
            mostrarTela("RELATORIO");
            gerarRelatorio();
        });

        btnLimpar.addActionListener(e -> limparDados());

        menu.add(btnImportar);
        menu.add(btnCadastro);
        menu.add(btnCalcular);
        menu.add(btnRelatorio);
        menu.add(btnLimpar);

        return menu;
    }

    private JPanel criarPainelPrincipal() {
        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(20, 22, 28));

        JLabel titulo = new JLabel("Dashboard de Emergia");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(new EmptyBorder(16, 18, 12, 18));

        statusLabel = new JLabel("Pronto para uso.");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(180, 190, 205));
        statusLabel.setBorder(new EmptyBorder(0, 18, 10, 18));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(20, 22, 28));
        topo.add(titulo, BorderLayout.NORTH);
        topo.add(statusLabel, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);
        painelConteudo.setBackground(new Color(20, 22, 28));

        painelConteudo.add(criarTelaImportacao(), "IMPORTAR");
        painelConteudo.add(criarTelaCadastro(), "CADASTRO");
        painelConteudo.add(criarTelaResultados(), "RESULTADOS");
        painelConteudo.add(criarTelaRelatorio(), "RELATORIO");

        principal.add(topo, BorderLayout.NORTH);
        principal.add(painelConteudo, BorderLayout.CENTER);

        cardLayout.show(painelConteudo, "IMPORTAR");
        return principal;
    }

    private JPanel criarTelaImportacao() {
        JPanel tela = painelBase();

        JPanel card = cardInterno();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel titulo = labelTitulo("Importar base CSV");
        JLabel subtitulo = labelTexto("Selecione um arquivo CSV com colunas: nome, tipo, quantidade, uev");

        JButton btnCarregar = botaoPrincipal("Selecionar CSV");
        btnCarregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCarregar.addActionListener(e -> carregarCSV());

        card.add(titulo);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(subtitulo);
        card.add(Box.createRigidArea(new Dimension(0, 24)));
        card.add(btnCarregar);

        tela.add(card, BorderLayout.CENTER);
        return tela;
    }

    private JPanel criarTelaCadastro() {
        JPanel tela = painelBase();

        JPanel card = cardInterno();
        card.setLayout(new BorderLayout(20, 20));

        JPanel formulario = new JPanel(new GridLayout(5, 2, 12, 12));
        formulario.setOpaque(false);

        JTextField campoNome = campoTexto();
        JComboBox<String> campoTipo = new JComboBox<>(new String[]{"R", "NR", "N"});
        estilizarCombo(campoTipo);
        JTextField campoQuantidade = campoTexto();
        JTextField campoUEV = campoTexto();

        formulario.add(labelCampo("Nome do recurso"));
        formulario.add(campoNome);
        formulario.add(labelCampo("Tipo"));
        formulario.add(campoTipo);
        formulario.add(labelCampo("Quantidade"));
        formulario.add(campoQuantidade);
        formulario.add(labelCampo("UEV"));
        formulario.add(campoUEV);

        JButton btnAdicionar = botaoPrincipal("Adicionar recurso");
        btnAdicionar.addActionListener(e -> {
            try {
                String nome = campoNome.getText().trim();
                Tipo tipo = Tipo.valueOf(Objects.requireNonNull(campoTipo.getSelectedItem()).toString().trim());
                double quantidade = Double.parseDouble(campoQuantidade.getText().trim());
                double uev = Double.parseDouble(campoUEV.getText().trim());

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o nome do recurso.");
                    return;
                }

                Recurso recurso = new Recurso(nome, tipo, quantidade, uev);
                recursos.add(recurso);
                atualizarListaRecursos();

                campoNome.setText("");
                campoQuantidade.setText("");
                campoUEV.setText("");

                statusLabel.setText("Recurso adicionado com sucesso.");
                JOptionPane.showMessageDialog(this, "Recurso cadastrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade e UEV devem ser numéricos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar recurso: " + ex.getMessage());
            }
        });

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.add(labelTitulo("Cadastro manual de recursos"), BorderLayout.NORTH);
        topo.add(new JLabel(" "), BorderLayout.CENTER);

        JPanel esquerda = new JPanel(new BorderLayout(0, 16));
        esquerda.setOpaque(false);
        esquerda.add(topo, BorderLayout.NORTH);
        esquerda.add(formulario, BorderLayout.CENTER);
        esquerda.add(btnAdicionar, BorderLayout.SOUTH);

        listaRecursosModel = new DefaultListModel<>();
        JList<String> listaRecursos = new JList<>(listaRecursosModel);
        listaRecursos.setBackground(new Color(18, 20, 26));
        listaRecursos.setForeground(Color.WHITE);
        listaRecursos.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scrollLista = new JScrollPane(listaRecursos);
        scrollLista.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 80, 95)),
                "Recursos cadastrados"
        ));
        scrollLista.getViewport().setBackground(new Color(18, 20, 26));

        card.add(esquerda, BorderLayout.WEST);
        card.add(scrollLista, BorderLayout.CENTER);

        tela.add(card, BorderLayout.CENTER);
        return tela;
    }

    private JPanel criarTelaResultados() {
        JPanel tela = painelBase();

        JPanel card = cardInterno();
        card.setLayout(new BorderLayout(16, 16));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        topo.setOpaque(false);

        JButton btnCalcular = botaoPrincipal("Calcular");
        JButton btnAtualizar = botaoPrincipal("Atualizar lista");

        btnCalcular.addActionListener(e -> calcular());
        btnAtualizar.addActionListener(e -> atualizarListaRecursos());

        topo.add(btnCalcular);
        topo.add(btnAtualizar);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(18, 20, 26));
        areaResultados.setForeground(Color.WHITE);
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 15));
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 80, 95)),
                "Resultados do cálculo"
        ));
        scroll.getViewport().setBackground(new Color(18, 20, 26));

        card.add(labelTitulo("Cálculo de emergia"), BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(topo, BorderLayout.SOUTH);

        tela.add(card, BorderLayout.CENTER);
        return tela;
    }

    private JPanel criarTelaRelatorio() {
        JPanel tela = painelBase();

        JPanel card = cardInterno();
        card.setLayout(new BorderLayout(16, 16));

        JTextArea areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setBackground(new Color(18, 20, 26));
        areaRelatorio.setForeground(Color.WHITE);
        areaRelatorio.setFont(new Font("Consolas", Font.PLAIN, 15));
        areaRelatorio.setText("Clique em 'Relatório' no menu lateral para gerar o arquivo relatorio_emergia.txt.");

        JScrollPane scroll = new JScrollPane(areaRelatorio);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 80, 95)),
                "Relatório"
        ));

        card.add(labelTitulo("Exportação de relatório"), BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        tela.add(card, BorderLayout.CENTER);
        return tela;
    }

    private void mostrarTela(String nome) {
        cardLayout.show(painelConteudo, nome);
    }

    private void carregarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecionar arquivo CSV");

        int resultado = chooser.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            statusLabel.setText("Importação cancelada.");
            return;
        }

        File arquivo = chooser.getSelectedFile();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            recursos.clear();

            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                if (primeira) {
                    primeira = false;
                    continue;
                }

                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] d = linha.split(",");

                if (d.length < 4) {
                    continue;
                }

                String nome = d[0].trim();
                Tipo tipo = Tipo.valueOf(d[1].trim());
                double quantidade = Double.parseDouble(d[2].trim());
                double uev = Double.parseDouble(d[3].trim());

                recursos.add(new Recurso(nome, tipo, quantidade, uev));
            }

            atualizarListaRecursos();
            statusLabel.setText("CSV carregado: " + arquivo.getName());
            JOptionPane.showMessageDialog(this, "CSV carregado com sucesso.");
            mostrarTela("RESULTADOS");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler CSV: " + e.getMessage());
            statusLabel.setText("Falha ao carregar CSV.");
        }
    }

    private void calcular() {
        if (recursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum recurso carregado/cadastrado.");
            statusLabel.setText("Não há dados para calcular.");
            return;
        }

        double total = 0;
        double totalR = 0;
        double totalNR = 0;
        double totalN = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("RESULTADO DO CÁLCULO DE EMERGIA\n");
        sb.append("===============================================\n\n");

        Processo processo = new Processo("Processo Principal");
        processos.clear();
        processos.add(processo);

        for (Recurso r : recursos) {
            processo.adicionarRecurso(r);

            double emergia = r.calcularEmergia();
            total += emergia;

            if (r.tipo.toString().equalsIgnoreCase(Tipo.R.toString())) {
                totalR += emergia;
            } else if (r.tipo.toString().equalsIgnoreCase(Tipo.NR.toString())) {
                totalNR += emergia;
            } else {
                totalN += emergia;
            }

            sb.append(String.format(
                    "%-20s | Tipo: %-2s | Qtd: %-12.2f | UEV: %-12.2e | Emergia: %.2e%n",
                    r.nome, r.tipo, r.quantidade, r.uev, emergia
            ));
        }

        sb.append("\n-----------------------------------------------\n");
        sb.append(String.format("Total calculado (manual): %.2e%n", total));
        sb.append(String.format("Total renovável (R):      %.2e%n", totalR));
        sb.append(String.format("Total não renovável (NR): %.2e%n", totalNR));
        sb.append(String.format("Total N:                  %.2e%n", totalN));
        sb.append(String.format("Total via processo:       %.2e%n", processo.calculaEmergiaTotal()));
        sb.append(String.format("Total via calculadora:    %.2e%n", CalculadoraEmergia.calcularTotal(recursos)));

        areaResultados.setText(sb.toString());
        statusLabel.setText("Cálculo concluído com sucesso.");
        mostrarTela("RESULTADOS");
    }

    private void gerarRelatorio() {
        if (areaResultados == null || areaResultados.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Calcule os dados antes de gerar o relatório.");
            statusLabel.setText("Relatório não gerado.");
            return;
        }

        File arquivo = new File("relatorio_emergia.txt");

        try (FileWriter fw = new FileWriter(arquivo)) {
            fw.write(areaResultados.getText());
            JOptionPane.showMessageDialog(this, "Relatório gerado em:\n" + arquivo.getAbsolutePath());
            statusLabel.setText("Relatório gerado com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
            statusLabel.setText("Falha ao gerar relatório.");
        }
    }

    private void limparDados() {
        recursos.clear();
        processos.clear();

        if (listaRecursosModel != null) {
            listaRecursosModel.clear();
        }

        if (areaResultados != null) {
            areaResultados.setText("");
        }

        statusLabel.setText("Dados limpos.");
        JOptionPane.showMessageDialog(this, "Todos os dados foram removidos.");
    }

    private void atualizarListaRecursos() {
        if (listaRecursosModel == null) {
            return;
        }

        listaRecursosModel.clear();

        for (Recurso r : recursos) {
            listaRecursosModel.addElement(
                    r.nome + " | " + r.tipo + " | qtd=" + r.quantidade + " | uev=" + r.uev
            );
        }
    }

    private JPanel painelBase() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(20, 22, 28));
        p.setBorder(new EmptyBorder(10, 16, 16, 16));
        return p;
    }

    private JPanel cardInterno() {
        JPanel p = new JPanel();
        p.setBackground(new Color(26, 29, 36));
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        return p;
    }

    private JButton botaoMenu(String texto) {
        JButton b = new JButton(texto);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(28, 31, 39));
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBorder(BorderFactory.createLineBorder(new Color(70, 80, 95)));
        return b;
    }

    private JButton botaoPrincipal(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 17));
        b.setBackground(new Color(25, 118, 230));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(220, 50));
        return b;
    }

    private JTextField campoTexto() {
        JTextField c = new JTextField();
        c.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        c.setBackground(new Color(18, 20, 26));
        c.setForeground(Color.WHITE);
        c.setCaretColor(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(new Color(70, 80, 95)));
        return c;
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        combo.setBackground(new Color(18, 20, 26));
        combo.setForeground(Color.WHITE);
    }

    private JLabel labelTitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
        return l;
    }

    private JLabel labelTexto(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(new Color(200, 205, 215));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return l;
    }

    private JLabel labelCampo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return l;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceFrame().setVisible(true));
    }
}