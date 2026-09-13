package model;

/**
 * Representa uma reunião gravada e analisada pelo sistema.
 */
public class Reuniao {

    private int id;
    private String clienteNome;
    private String vendedorNome;
    private String dataReuniao; // yyyy-MM-dd
    private int duracaoMinutos;
    private String transcricao;
    private String sentimento; // POSITIVO, NEGATIVO, NEUTRO
    private int tempoFalaVendedor;
    private int tempoFalaCliente;
    private String produtoRelacionado; // PROTHEUS, DATASUL, RM

    public Reuniao() {
    }

    // Construtor completo com ID (para consultas do banco)
    public Reuniao(int id, String clienteNome, String vendedorNome, String dataReuniao,
                   int duracaoMinutos, String transcricao, String sentimento,
                   int tempoFalaVendedor, int tempoFalaCliente, String produtoRelacionado) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.vendedorNome = vendedorNome;
        this.dataReuniao = dataReuniao;
        this.duracaoMinutos = duracaoMinutos;
        this.transcricao = transcricao;
        this.sentimento = sentimento;
        this.tempoFalaVendedor = tempoFalaVendedor;
        this.tempoFalaCliente = tempoFalaCliente;
        this.produtoRelacionado = produtoRelacionado;
    }

    // Construtor sem ID (para novos registros)
    public Reuniao(String clienteNome, String vendedorNome, String dataReuniao,
                   int duracaoMinutos, String transcricao, String sentimento,
                   int tempoFalaVendedor, int tempoFalaCliente, String produtoRelacionado) {
        this(0, clienteNome, vendedorNome, dataReuniao, duracaoMinutos, transcricao, sentimento, tempoFalaVendedor, tempoFalaCliente, produtoRelacionado);
    }

    // 1. Calcula o risco de churn com base no sentimento e palavras-chave da transcrição
    public String calcularRiscoChurn() {
        int contagemPalavrasNegativas = 0;
        String[] palavrasChave = {"cancelar", "concorrente", "insatisfeito", "problema"};

        if (transcricao != null && !transcricao.isEmpty()) {
            String textoMinusculo = transcricao.toLowerCase();
            for (String palavra : palavrasChave) {
                if (textoMinusculo.contains(palavra)) {
                    contagemPalavrasNegativas++;
                }
            }
        }

        boolean sentimentoNegativo = sentimento != null && sentimento.trim().equalsIgnoreCase("NEGATIVO");

        if (sentimentoNegativo && contagemPalavrasNegativas >= 2) {
            return "ALTO";
        } else if (sentimentoNegativo || contagemPalavrasNegativas >= 1) {
            return "MEDIO";
        } else {
            return "BAIXO";
        }
    }

    // 2. Calcula a porcentagem de fala do vendedor no tempo total
    public double calcularProporcaoFala() {
        int totalTempo = tempoFalaVendedor + tempoFalaCliente;
        if (totalTempo == 0) {
            return 0.0;
        }
        return ((double) tempoFalaVendedor / totalTempo) * 100.0;
    }

    // 3. Identifica oportunidades de cross-selling com base no conteúdo da conversa
    public String identificarOportunidadeCrossSelling() {
        if (transcricao == null || transcricao.trim().isEmpty()) {
            return "Nenhuma oportunidade identificada";
        }

        String texto = transcricao.toLowerCase();

        if (texto.contains("fiscal") || texto.contains("conciliação") || texto.contains("conciliacao") || texto.contains("backoffice")) {
            return "Módulo de Automação Fiscal";
        }
        if (texto.contains("suprimentos") || texto.contains("produção") || texto.contains("producao") || texto.contains("cadeia")) {
            return "Módulo de Planejamento de Produção";
        }
        if (texto.contains("folha") || texto.contains("pagamento") || texto.contains("rh") || texto.contains("evasão") || texto.contains("evasao")) {
            return "Módulo de Automação de RH";
        }

        return "Nenhuma oportunidade identificada";
    }

    // 4. Classifica a prioridade da reunião combinando risco de churn e duração
    public String classificarPrioridade() {
        String risco = calcularRiscoChurn();

        if ("ALTO".equals(risco) && duracaoMinutos > 60) {
            return "CRITICA";
        } else if ("ALTO".equals(risco) || duracaoMinutos > 60) {
            return "ALTA";
        } else if ("MEDIO".equals(risco)) {
            return "MEDIA";
        } else {
            return "BAIXA";
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public void setVendedorNome(String vendedorNome) {
        this.vendedorNome = vendedorNome;
    }

    public String getDataReuniao() {
        return dataReuniao;
    }

    public void setDataReuniao(String dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTranscricao() {
        return transcricao;
    }

    public void setTranscricao(String transcricao) {
        this.transcricao = transcricao;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public int getTempoFalaVendedor() {
        return tempoFalaVendedor;
    }

    public void setTempoFalaVendedor(int tempoFalaVendedor) {
        this.tempoFalaVendedor = tempoFalaVendedor;
    }

    public int getTempoFalaCliente() {
        return tempoFalaCliente;
    }

    public void setTempoFalaCliente(int tempoFalaCliente) {
        this.tempoFalaCliente = tempoFalaCliente;
    }

    public String getProdutoRelacionado() {
        return produtoRelacionado;
    }

    public void setProdutoRelacionado(String produtoRelacionado) {
        this.produtoRelacionado = produtoRelacionado;
    }

    @Override
    public String toString() {
        return "Reuniao [id=" + id + ", clienteNome=" + clienteNome + ", vendedorNome=" + vendedorNome
                + ", dataReuniao=" + dataReuniao + ", duracaoMinutos=" + duracaoMinutos + ", sentimento="
                + sentimento + ", produtoRelacionado=" + produtoRelacionado + "]";
    }
}
