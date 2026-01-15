package br.trcs.rc.service;

import java.time.LocalDate;
import java.util.Properties;
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

	/**
	 * Variáveis de configuração do SMTP.
	 */
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_FROM = "tainararobertacarvalho@gmail.com"; 
    private static final String EMAIL_PASSWORD = "lditmlwunrelczxr";
    
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
        String cpfClean = cpf.replace(".", "").replace("-", "");
        
        // URL direta para ativação.
        String activationUrl = "http://localhost:8080/ReadingClub/activate.xhtml?cpf=" + cpfClean;
        
        String subject = "✅ Ative sua conta - Clube da Leitura";
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                <div style="max-width: 600px; margin: 0 auto; background: #f9f9f9; padding: 20px;">
                    <div style="text-align: center; background: #4CAF50; color: white; padding: 20px;">
                        <h1>🎉 Bem-vindo ao Clube da Leitura!</h1>
                    </div>
                    
                    <div style="padding: 20px;">
                        <p>Olá, <strong>%s</strong>!</p>
                        <p>Seu cadastro foi recebido com sucesso.</p>
                        <p>Para <strong>ativar sua conta</strong> e começar a usar o sistema, clique no botão abaixo:</p>
                        
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" 
                               style="background-color: #4CAF50; color: white; padding: 15px 30px; 
                                      text-decoration: none; border-radius: 5px; font-size: 16px;
                                      display: inline-block;">
                                ✅ ATIVAR MINHA CONTA
                            </a>
                        </div>
                        
                        <p>Ou copie e cole este link no seu navegador:</p>
                        <p style="background: #eee; padding: 10px; border-radius: 5px; font-size: 12px; word-wrap: break-word;">
                            %s
                        </p>
                        
                        <hr style="border: none; border-top: 1px solid #ddd; margin: 30px 0;">
                        
                        <p style="font-size: 12px; color: #666;">
                            Se você não solicitou este cadastro, ignore este e-mail.<br>
                            Este é um e-mail automático, por favor não responda.
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, activationUrl, activationUrl);
        
        return sendEmail(toEmail, subject, htmlContent);
    }
    
    /**
     * Envia e-mail de confirmação após a ativação da conta.
     *
     * @param toEmail e-mail do destinatário.
     * @param userName nome do usuário.
     * @return {@code true} se o e-mail foi enviado com sucesso.
     */
    public static boolean sendWelcomeEmail(String toEmail, String userName) {
        String subject = "🎉 Conta ativada com sucesso!";
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <body style="font-family: Arial, sans-serif;">
                <div style="text-align: center; background: #4CAF50; color: white; padding: 30px;">
                    <h1 style="margin: 0;">✅ CONTA ATIVADA!</h1>
                </div>
                
                <div style="padding: 30px;">
                    <p>Parabéns, <strong>%s</strong>! 🎉</p>
                    <p>Sua conta no <strong>Clube da Leitura</strong> foi ativada com sucesso.</p>
                    <p>Você já pode fazer login e começar a emprestar revistas!</p>
                    
                    <div style="text-align: center; margin: 40px 0;">
                        <a href="http://localhost:8080/ReadingClub/login.xhtml" 
                           style="background-color: #4CAF50; color: white; padding: 15px 40px; 
                                  text-decoration: none; border-radius: 5px; font-size: 18px;
                                  display: inline-block;">
                            🚀 ACESSAR O SISTEMA
                        </a>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName);
        
        return sendEmail(toEmail, subject, htmlContent);
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
        String subject = "⏰ Empréstimo em atraso - Clube da Leitura";
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; background: #fff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
                    <!-- Cabeçalho -->
                    <div style="background: linear-gradient(135deg, #ff6b6b 0%%, #ee5a52 100%%); color: white; padding: 25px; text-align: center;">
                        <h1 style="margin: 0; font-size: 24px;">⏰ ATENÇÃO: EMPRÉSTIMO EM ATRASO</h1>
                    </div>
                    
                    <!-- Conteúdo -->
                    <div style="padding: 30px;">
                        <p>Olá, <strong>%s</strong>!</p>
                        
                        <p>Identificamos que você possui um <strong style="color: #ff6b6b;">empréstimo em atraso</strong> no sistema do Clube da Leitura.</p>
                        
                        <div style="background: #fff5f5; border-left: 4px solid #ff6b6b; padding: 15px; margin: 20px 0; border-radius: 4px;">
                            <h3 style="margin-top: 0; color: #c53030;">📚 Revista: %s</h3>
                            <p style="margin-bottom: 5px;"><strong>⚠️ Dias em atraso:</strong> <span style="color: #c53030; font-size: 18px;">%d dia(s)</span></p>
                            <p style="margin: 0;"><strong>📅 Data prevista de devolução:</strong> Já ultrapassada</p>
                        </div>
                        
                        <p>Por favor, <strong>devolva a revista o mais breve possível</strong> para evitar bloqueios no sistema.</p>
                        
                        <div style="background: #f0f9ff; border: 1px solid #bee3f8; border-radius: 8px; padding: 15px; margin: 25px 0;">
                            <h4 style="margin-top: 0; color: #2c5282;">📋 O que fazer agora:</h4>
                            <ol style="margin-bottom: 0;">
                                <li>Localize a revista em sua posse</li>
                                <li>Entre em contato com o administrador do clube</li>
                                <li>Faça a devolução para regularizar sua situação</li>
                            </ol>
                        </div>
                        
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="http://localhost:8080/ReadingClub/login.xhtml" 
                               style="background: linear-gradient(135deg, #4CAF50 0%%, #45a049 100%%); 
                                      color: white; padding: 14px 35px; text-decoration: none; 
                                      border-radius: 6px; font-weight: bold; display: inline-block;
                                      box-shadow: 0 4px 15px rgba(76, 175, 80, 0.3);">
                                📋 ACESSAR MEUS EMPRÉSTIMOS
                            </a>
                        </div>
                        
                        <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 30px 0;">
                        
                        <p style="font-size: 12px; color: #718096;">
                            Esta é uma notificação automática do sistema.<br>
                            Dúvidas? Entre em contato com o administrador do Clube da Leitura.
                        </p>
                    </div>
                    
                    <!-- Rodapé -->
                    <div style="background: #f7fafc; padding: 20px; text-align: center; border-top: 1px solid #e2e8f0;">
                        <p style="margin: 0; font-size: 12px; color: #a0aec0;">
                            © %d Clube da Leitura. Todos os direitos reservados.
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, comicTitle, daysOverdue, LocalDate.now().getYear());
        
        return sendEmail(toEmail, subject, htmlContent);
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
            
            System.out.println("E-mail enviado com sucesso para: " + toEmail);
            return true;
            
        } catch (Exception e) {
        	System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}