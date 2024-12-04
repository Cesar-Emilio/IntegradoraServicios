describe('Register Form Tests', () => {
    beforeEach(() => {
        cy.visit('/register'); // Navegar a la página de registro
    });

    it('should display the register form', () => {
        cy.get('form').should('be.visible');
        cy.get('input[name="name"]').should('exist');
        cy.get('input[name="lastname"]').should('exist');
        cy.get('input[name="phone"]').should('exist');
        cy.get('input[name="email"]').should('exist');
        cy.get('input[name="password"]').should('exist');
        cy.get('button[type="submit"]').contains('Regístrate');
    });

    it('should register a new user', () => {
        cy.get('input[name="email"]').type('erickhuberto.teja@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('input[name="name"]').type('Erick');
        cy.get('input[name="lastname"]').type('Teja');
        cy.get('input[name="phone"]').type('7774806546');
        cy.get('button[type="submit"]').click();

        // Verifica la redirección o mensaje de éxito
        cy.url().should('include', '/dashboard');
        cy.contains('Lista de Proyectos'); // Ajusta según el contenido real
    });
});
