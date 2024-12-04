describe('Login Form Tests', () => {
    beforeEach(() => {
        cy.visit('/login'); // Navegar a la página de login
    });

    it('should display the login form', () => {
        cy.get('form').should('be.visible');
        cy.get('input[name="email"]').should('exist');
        cy.get('input[name="password"]').should('exist');
        cy.get('button[type="submit"]').contains('Iniciar sesión');
    });

    it('should allow a user to login', () => {
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        // Verifica que el usuario se redirige o recibe un mensaje de éxito
        cy.url().should('include', '/dashboard');
        cy.contains('Lista de Proyectos'); // Ajusta según el contenido real
    });
});
