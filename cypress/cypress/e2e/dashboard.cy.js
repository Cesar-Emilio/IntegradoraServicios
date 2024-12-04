describe('Dashboard Elements', () => {
    beforeEach(() => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();
    });

    it('should display key elements', () => {
        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiCardActions-root').eq(0) // Selecciona el primer div con clase "card"
                .find('a').contains("Ver Detalles") // Encuentra el botón
                .click(); // Haz clic en el botón
        cy.url().should('include', '/proyect/Controlador');
        cy.visit('/dashboard');

        cy.get('.MuiCardActions-root').eq(1) // Selecciona el segundo div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón
        cy.url().should('include', '/proyect/Sistema');
        cy.visit('/dashboard');

        cy.get('.MuiCardActions-root').eq(2) // Selecciona el tercer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón

        cy.url().should('include', '/proyect/Gesti%C3%B3n');
    });

});
