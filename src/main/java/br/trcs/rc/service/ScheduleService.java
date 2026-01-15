package br.trcs.rc.service;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.trcs.rc.dao.BorrowingDAO;
import br.trcs.rc.model.Borrowing;

/**
 * Serviço responsável pelo agendamento e execução automática
 * de tarefas relacionadas aos empréstimos do sistema.
 * <ul>
 *   <li>Identifica empréstimos em atraso</li>
 *   <li>Verifica se o usuário possui e-mail confirmado</li>
 *   <li>Envia notificações automáticas de atraso</li>
 * </ul>
 */
@WebListener
public class ScheduleService implements ServletContextListener {
	/**
     * Executor responsável pelo agendamento das tarefas periódicas.
     */
	private ScheduledExecutorService scheduler;
	
	/**
     * Método executado automaticamente quando o contexto da aplicação é inicializado.
     * Responsável por configurar e iniciar o agendador de verificação de empréstimos em atraso.
     *
     * @param sce evento de inicialização do contexto.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando scheduler de empréstimos...");
        
        scheduler = Executors.newSingleThreadScheduledExecutor();

        /*
         * Executa o job a cada 1 dia.
         * O primeiro disparo ocorre após 1 dia da inicialização da aplicação.
         *
         * Para testes, pode-se utilizar:
         * scheduler.scheduleAtFixedRate(this::runJob, 0, 1, TimeUnit.MINUTES);
         */
        scheduler.scheduleAtFixedRate(this::runJob, 1, 1, TimeUnit.DAYS);

    }

    /**
     * Job responsável pela verificação de empréstimos em atraso.
     * <ol>
     *   <li>Busca todos os empréstimos em atraso</li>
     *   <li>Ignora empréstimos já devolvidos</li>
     *   <li>Ignora usuários com e-mail não confirmado</li>
     *   <li>Calcula a quantidade de dias em atraso</li>
     *   <li>Envia notificação por e-mail</li>
     * </ol>
     */
    private void runJob() {
    	System.out.println("🔍 Verificando empréstimos em atraso...");

        try {
            BorrowingDAO dao = new BorrowingDAO(Borrowing.class);
            List<Borrowing> overdueList = dao.listOverdue();

            System.out.println("Empréstimos em atraso encontrados: " + overdueList.size());

            for (Borrowing borrowing : overdueList) {
                if (borrowing.getReturnDate() != null) continue;

                if (!borrowing.getUser().getConfirmedEmail()) {
                    System.out.println("Usuário sem e-mail confirmado: " + borrowing.getUser().getName());
                    continue;
                }
                
                // Calcula dias de atraso.
                long daysOverdue = ChronoUnit.DAYS.between(borrowing.getExpectedReturnDate(), LocalDate.now());

                if (daysOverdue <= 0) continue;

                System.out.println("Enviando e-mail para: " + borrowing.getUser().getEmail());
                
                // Envio da notificação de atraso.
                EmailService.sendOverdueNotification(
                        borrowing.getUser().getEmail(),
                        borrowing.getUser().getName(),
                        borrowing.getComic().getCollection(),
                        (int) daysOverdue
                );
            }

            System.out.println("Verificação finalizada");

        } catch (Exception e) {
            System.err.println("Erro no job de atraso");
            e.printStackTrace();
        }
    }

    /**
     * Método executado automaticamente quando o contexto da aplicação é destruído.
     * Responsável por encerrar corretamente o executor e liberar recursos.
     *
     * @param sce evento de destruição do contexto.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Finalizando scheduler...");
        if (scheduler != null) 
            scheduler.shutdownNow();
    }
}
