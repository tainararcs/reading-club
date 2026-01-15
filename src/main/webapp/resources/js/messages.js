function setupAutoDismissMessages() {
    // Encontra todas as mensagens
    var messages = document.querySelectorAll('.ui-messages, .p-messages');
    
    messages.forEach(function(msg) {
        // Se já tem um timer, ignora
        if (msg.dataset.hasTimer === 'true') return;
        
        // Marca que já tem timer
        msg.dataset.hasTimer = 'true';
        
        // Configura fade out após 4 segundos
        setTimeout(function() {
            msg.style.transition = 'opacity 0.5s ease';
            msg.style.opacity = '0';
        }, 4000);
        
        // Remove após 5 segundos
        setTimeout(function() {
            msg.style.display = 'none';
        }, 5000);
    });
}

// Executa quando o DOM carrega
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', function() {
        setTimeout(setupAutoDismissMessages, 500);
    });
} else {
    setTimeout(setupAutoDismissMessages, 500);
}

// Executa após cada requisição AJAX do PrimeFaces
if (typeof PrimeFaces !== 'undefined') {
    PrimeFaces.ajax.addOnComplete(function() {
        setTimeout(setupAutoDismissMessages, 300);
    });
}