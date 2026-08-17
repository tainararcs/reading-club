function toggleForms() {
    console.log("Trocando painel...");

    const loginPanel = document.getElementById('loginPanel');
    const registerPanel = document.getElementById('registerPanel');

    if (!loginPanel || !registerPanel) {
        console.error("Painéis não encontrados no DOM");
        return;
    }

    loginPanel.classList.toggle('active');
    registerPanel.classList.toggle('active');
}