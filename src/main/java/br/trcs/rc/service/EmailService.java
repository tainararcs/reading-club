package br.trcs.rc.service;

import java.time.LocalDate;
import java.util.Properties;

import br.trcs.rc.utils.EmailConsts;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * Serviço responsável pelo envio de e-mails do sistema.
 * Centraliza toda a lógica de comunicação por e-mail do projeto, incluindo:
 * </p>
 * <ul>
 *   <li>Ativação de conta</li>
 *   <li>Confirmação de ativação</li>
 *   <li>Notificação de empréstimos em atraso</li>
 * </ul>
 * <p>
 */
public class EmailService {
	// criar uma classe para colcoar as mnesagens? dps css
	/**
	 * Variáveis de configuração do SMTP.
	 */
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String EMAIL_FROM = System.getenv("READING_CLUB_EMAIL");
	private static final String EMAIL_PASSWORD = System.getenv("READING_CLUB_EMAIL_PASSWORD");
	private static final String BASE_URL = System.getenv("READING_CLUB_BASE_URL");
	
    /**
     * Envia e-mail de ativação de conta com link direto.
     * O link direciona o usuário para a página de ativação contendo o CPF como parâmetro de requisição.
     *
     * @param toEmail e-mail do destinatário.
     * @param userName nome do usuário.
     * @param cpf CPF do usuário (com ou sem formatação).
     * @return {@code true} se o e-mail foi enviado com sucesso.
     */
    public static boolean sendActivationEmail(String toEmail, String userName, String cpf) {
    	// Remove formatação do CPF para usar na URL.
        String cleanCpf = cpf.replace(".", "").replace("-", "");

        String activationUrl = String.format("%s/activate.xhtml?cpf=%s", BASE_URL, cleanCpf);
        String htmlContent = EmailConsts.ACTIVATION_MESSAGE.formatted(userName, activationUrl, activationUrl);
        
        return sendEmail(toEmail, EmailConsts.ACTIVATION_SUBJECT, htmlContent);
    }
    
    /**
     * Envia e-mail de confirmação após a ativação da conta.
     *
     * @param toEmail e-mail do destinatário.
     * @param userName nome do usuário.
     * @return {@code true} se o e-mail foi enviado com sucesso.
     */
    public static boolean sendWelcomeEmail(String toEmail, String userName) {
        String htmlContent = EmailConsts.WELCOME_MESSAGE.formatted(userName);
        return sendEmail(toEmail, EmailConsts.WELCOME_SUBJECT, htmlContent);
    }
    
    /**
     * Envia notificação de empréstimo em atraso.
     *
     * @param toEmail e-mail do usuário.
     * @param userName nome do usuário.
     * @param comicTitle título da revista.
     * @param daysOverdue quantidade de dias em atraso.
     * @return {@code true} se o e-mail foi enviado com sucesso.
     */
    public static boolean sendOverdueNotification(String toEmail, String userName, String comicTitle, int daysOverdue) {
        String htmlContent = EmailConsts.OVERDUE_MESSAGE.formatted(userName, comicTitle, daysOverdue, LocalDate.now().getYear());
        return sendEmail(toEmail, EmailConsts.OVERDUE_SUBJECT, htmlContent);
    }
    
    /**
     * Método privado responsável pelo envio efetivo do e-mail.
     * Configura a sessão SMTP, autentica o remetente e envia a mensagem no formato HTML.
     *
     * @param toEmail e-mail do destinatário.
     * @param subject assunto do e-mail.
     * @param htmlContent conteúdo HTML do e-mail.
     * @return {@code true} se o envio foi realizado com sucesso.
     */
    private static boolean sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            // Configuração das propriedades SMTP.
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.ssl.trust", SMTP_HOST);
            
            // Autenticação.
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
                }
            });
            
            // Criação da mensagem.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM, "Clube da Leitura"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");
            
            // Envio.
            Transport.send(message);
            System.out.println("E-mail enviado para: " + toEmail);
            return true;
            
        } catch (Exception e) {
        	System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}