package br.trcs.rc.utils;

/**
 * 
 */
public class EmailConsts {

	/**	 */
	private EmailConsts() {}
	
	 /**
     * 
     */
    public static final String ACTIVATION_SUBJECT = "✅ Ative sua conta - Clube da Leitura";

    /**
     * 
     */
    public static final String WELCOME_SUBJECT = "🎉 Conta ativada com sucesso!";

    /**
     * 
     */
    public static final String OVERDUE_SUBJECT = "⏰ Empréstimo em atraso - Clube da Leitura";
	
	/**
	 * 
	 */
	public static final String ACTIVATION_MESSAGE = """
		<!DOCTYPE html>
	    <html>
			<head> <meta charset="UTF-8"> </head>
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
		                       style="background-color: #4CAF50; color: white; padding: 15px 30px; text-decoration: none; 
				                      border-radius: 5px; font-size: 16px; display: inline-block;">
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
	""";
	
	/**
	 * 
	 */
	public static final String WELCOME_MESSAGE = """
		<!DOCTYPE html>
		<html>
			<head> <meta charset="UTF-8"> </head>
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
	""";
	
	/**
	 * 
	 */
	public static final String OVERDUE_MESSAGE = """
		<!DOCTYPE html>
	    <html>
		    <head> <meta charset="UTF-8"> </head>
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
		                <p style="margin: 0; font-size: 12px; color: #a0aec0;"> © %d Clube da Leitura. Todos os direitos reservados. </p>
		            </div>
		        </div>
		    </body>
	    </html>
	""";
}
