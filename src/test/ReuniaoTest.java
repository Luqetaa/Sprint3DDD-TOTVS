package test;

import connection.Conexao;
import dao.ReuniaoDAO;
import model.Reuniao;

import java.util.List;

public class ReuniaoTest {

    public static void main(String[] args) {
        System.out.println("### INICIANDO TESTES DO SISTEMA ###\n");

        // Cria a tabela no banco caso ainda não exista
        Conexao.inicializarBanco();

        testarRegrasDeNegocio();
        testarCRUD();

        System.out.println("\n### TESTES FINALIZADOS COM SUCESSO ###");
    }

    private static void testarRegrasDeNegocio() {
        System.out.println("--- 1. Testando Regras de Negócio (Model) ---\n");

        // Cenário 1: Reunião com problema grave / risco de churn alto
        Reuniao r1 = new Reuniao(
            "Indústrias Metalflex Ltda",
            "Carlos Eduardo",
            "2026-09-10",
            75,
            "O cliente relatou estar muito insatisfeito com a lentidão e pensa em cancelar o contrato para ir para o concorrente. Precisamos resolver a conciliação fiscal e o backoffice com urgência.",
            "NEGATIVO",
            45,
            30,
            "PROTHEUS"
        );

        System.out.println("Cenário 1: " + r1.getClienteNome() + " (" + r1.getProdutoRelacionado() + ")");
        System.out.println("Sentimento: " + r1.getSentimento());
        System.out.println("Risco de Churn: " + r1.calcularRiscoChurn());
        System.out.printf("Proporção de Fala do Vendedor: %.2f%%\n", r1.calcularProporcaoFala());
        System.out.println("Sugestão de Cross-Selling: " + r1.identificarOportunidadeCrossSelling());
        System.out.println("Prioridade da Reunião: " + r1.classificarPrioridade());
        System.out.println("Dados: " + r1);
        System.out.println();

        // Cenário 2: Reunião neutra com menção a suprimentos
        Reuniao r2 = new Reuniao(
            "Agro Alimentos Sul S.A.",
            "Mariana Souza",
            "2026-09-11",
            40,
            "Conversamos sobre a gestão de suprimentos e gargalos na linha de produção da fábrica. Tivemos um problema pontual na entrega, mas o cliente está receptivo à cadeia logística.",
            "NEUTRO",
            20,
            20,
            "DATASUL"
        );

        System.out.println("Cenário 2: " + r2.getClienteNome() + " (" + r2.getProdutoRelacionado() + ")");
        System.out.println("Sentimento: " + r2.getSentimento());
        System.out.println("Risco de Churn: " + r2.calcularRiscoChurn());
        System.out.printf("Proporção de Fala do Vendedor: %.2f%%\n", r2.calcularProporcaoFala());
        System.out.println("Sugestão de Cross-Selling: " + r2.identificarOportunidadeCrossSelling());
        System.out.println("Prioridade da Reunião: " + r2.classificarPrioridade());
        System.out.println("Dados: " + r2);
        System.out.println();

        // Cenário 3: Reunião positiva com menção a RH
        Reuniao r3 = new Reuniao(
            "Colégio & Faculdade Horizonte",
            "Lucas Pinheiro",
            "2026-09-12",
            30,
            "Alinhamento muito produtivo com a equipe de RH. O cliente elogiou a facilidade do cálculo de folha e pagamento dos colaboradores, sem evasão cadastral.",
            "POSITIVO",
            10,
            20,
            "RM"
        );

        System.out.println("Cenário 3: " + r3.getClienteNome() + " (" + r3.getProdutoRelacionado() + ")");
        System.out.println("Sentimento: " + r3.getSentimento());
        System.out.println("Risco de Churn: " + r3.calcularRiscoChurn());
        System.out.printf("Proporção de Fala do Vendedor: %.2f%%\n", r3.calcularProporcaoFala());
        System.out.println("Sugestão de Cross-Selling: " + r3.identificarOportunidadeCrossSelling());
        System.out.println("Prioridade da Reunião: " + r3.classificarPrioridade());
        System.out.println("Dados: " + r3);
        System.out.println();

        // Cenário 4: Teste de proporção quando tempo total for zero
        Reuniao rZero = new Reuniao(
            "Cliente Teste",
            "Vendedor Teste",
            "2026-09-12",
            0,
            "Reunião cancelada.",
            "NEUTRO",
            0,
            0,
            "PROTHEUS"
        );
        System.out.println("Cenário 4 (Divisão por zero): Proporção de fala = " + rZero.calcularProporcaoFala() + "%");
        System.out.println();
    }

    private static void testarCRUD() {
        System.out.println("--- 2. Testando Operações de Banco de Dados (DAO) ---\n");

        ReuniaoDAO dao = new ReuniaoDAO();

        // Create
        System.out.println("Inserindo nova reunião...");
        Reuniao novaReuniao = new Reuniao(
            "Logística Express Brasil",
            "Fernanda Lima",
            "2026-09-12",
            50,
            "Cliente quer avaliar otimização da cadeia de suprimentos e módulo de planejamento com Datasul.",
            "POSITIVO",
            25,
            25,
            "DATASUL"
        );

        boolean inserido = dao.inserir(novaReuniao);
        System.out.println("Inserção realizada: " + inserido + " | ID gerado: " + novaReuniao.getId());

        if (!inserido) {
            System.err.println("Erro ao tentar inserir registro no banco.");
            return;
        }

        // Read All
        System.out.println("\nListando todas as reuniões cadastradas:");
        List<Reuniao> lista = dao.listarTodas();
        for (Reuniao r : lista) {
            System.out.println(" - " + r);
        }

        // Read by ID
        int idTeste = novaReuniao.getId();
        System.out.println("\nBuscando reunião de ID: " + idTeste);
        Reuniao buscada = dao.buscarPorId(idTeste);
        if (buscada != null) {
            System.out.println("Encontrada: " + buscada.getClienteNome() + " | Vendedor: " + buscada.getVendedorNome());
        } else {
            System.out.println("Nenhum registro encontrado.");
        }

        // Update
        System.out.println("\nAtualizando dados da reunião...");
        if (buscada != null) {
            buscada.setSentimento("NEGATIVO");
            buscada.setTranscricao("O cliente cancelou o contrato e foi para o concorrente devido a problema grave.");
            buscada.setDuracaoMinutos(70);
            boolean atualizado = dao.atualizar(buscada);
            System.out.println("Atualização realizada: " + atualizado);

            Reuniao posUpdate = dao.buscarPorId(idTeste);
            System.out.println("Novo sentimento: " + posUpdate.getSentimento());
            System.out.println("Novo risco recalculado: " + posUpdate.calcularRiscoChurn());
            System.out.println("Nova prioridade: " + posUpdate.classificarPrioridade());
        }

        // Delete
        System.out.println("\nExcluindo reunião de ID: " + idTeste);
        boolean deletado = dao.deletar(idTeste);
        System.out.println("Exclusão realizada: " + deletado);

        Reuniao checagem = dao.buscarPorId(idTeste);
        if (checagem == null) {
            System.out.println("Registro excluído com sucesso do banco.");
        } else {
            System.out.println("Atenção: O registro ainda existe no banco.");
        }
    }
}
