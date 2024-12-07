describe('Start to End Tests', () => {
    it('Registro de un usuario nuevo', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();

        cy.get('form').should('be.visible');
        cy.get('input[name="name"]').should('exist');
        cy.get('input[name="lastname"]').should('exist');
        cy.get('input[name="phone"]').should('exist');
        cy.get('input[name="email"]').should('exist');
        cy.get('input[name="password"]').should('exist');
        cy.get('button[type="submit"]').contains('Regístrate');

        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('input[name="name"]').type('Erick');
        cy.get('input[name="lastname"]').type('Teja');
        cy.get('input[name="phone"]').type('7774806546');
        cy.get('button[type="submit"]').click();

        // Verifica la redirección o mensaje de éxito
        cy.url().should('include', '/dashboard');
        cy.contains('Lista de Proyectos'); // Ajusta según el contenido real

    });
    it('Login de un usuario nuevo', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('form').should('be.visible');
        cy.get('input[name="email"]').should('exist');
        cy.get('input[name="password"]').should('exist');
        cy.get('button[type="submit"]').contains('Iniciar sesión');

        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.url().should('include', '/dashboard');
        cy.contains('Lista de Proyectos');
    });
    it('Crear un proyecto', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('button').eq(2).click();
        cy.get('.MuiInputBase-input')
            .eq(0) // Si hay varios, puedes usar el índice
            .type('Nombre del proyecto');
        cy.get('.MuiInputBase-input')
            .eq(1) // Si hay varios, puedes usar el índice
            .type('NP');
        cy.get('.MuiInputBase-input')
            .eq(2) // Si hay varios, puedes usar el índice
            .type('La descripción de un proyecto');
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('Nombre del proyecto');

    });
    it('Crear una categoria', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(0) // Selecciona el primer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón

        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary').eq(0).click();
        cy.get('.MuiInputBase-input')
            .eq(0) // Si hay varios, puedes usar el índice
            .type('Nombre de una Categoria');
        cy.get('.MuiInputBase-input')
            .eq(1) // Si hay varios, puedes usar el índice
            .type('Descripción de una categoria');
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('Categoría creada exitosamente');
        cy.visit('/dashboard')

    });
});