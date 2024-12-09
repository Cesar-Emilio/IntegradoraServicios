describe('Navigation Tests', () => {
    it('Navegar a la pagina de registro y regresar a la pagina principal', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.contains('Regístrate'); // Ajusta según tu contenido
        cy.get('a').contains('Volver').click();
        cy.contains('Bienvenido a To-Do List');
    });
    it('Navegar a la pagina de inicio de sesión y regresar a la pagina principal', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click(); // Asegúrate de ajustar el selector
        cy.url().should('include', '/login');
        cy.contains('Iniciar sesión'); // Ajusta según tu contenido
        cy.get('a').contains('Volver').click();
        cy.contains('Bienvenido a To-Do List');
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
            .type('Controlador de Tareas');
        cy.get('.MuiInputBase-input')
            .eq(1) // Si hay varios, puedes usar el índice
            .type('NP');
        cy.get('.MuiInputBase-input')
            .eq(2) // Si hay varios, puedes usar el índice
            .type('La descripción de un proyecto');
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();

    });
    it('Navegar a la pagina de controlador de tareas (Ver Detalles)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiCardActions-root').eq(0) // Selecciona el primer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón
        cy.url().should('include', '/proyect/Controlador');

    });
    it('Navegar a la pagina de controlador de tareas (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(3)
            .contains("Controlador de Tareas")
            .click();
        cy.contains('Controlador de Tareas');

    });
    it('Editar Perfil (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(1)
            .click();
        cy.contains("Perfil")
    });
    it('Cerrar sesión (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(2)
            .click();
        cy.contains("Bienvenido a To-Do List")
    });
});