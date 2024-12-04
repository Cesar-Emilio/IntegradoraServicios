describe('Navigation Tests', () => {
    it('should navigate to the login page from home', () => {
        cy.visit('/'); // Página inicial
        cy.get('a').contains('Iniciar sesión').click(); // Asegúrate de ajustar el selector
        cy.url().should('include', '/login');
        cy.contains('Iniciar sesión'); // Verifica contenido en la página destino
    });

    it('should navigate to the register page from login', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.contains('Regístrate'); // Ajusta según tu contenido
    });
});
