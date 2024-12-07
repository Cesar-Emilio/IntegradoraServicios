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
    it('Navegar a la pagina de controlador de tareas (Ver Detalles)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiCardActions-root').eq(0) // Selecciona el primer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón
        cy.url().should('include', '/proyect/Controlador');

    });
    it('Navegar a la pagina de sistema de inventarios (Ver Detalles)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(1) // Selecciona el segundo div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón
        cy.url().should('include', '/proyect/Sistema');
    });
    it('Navegar a la pagina de gestión de proyectos (Ver Detalles)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(2) // Selecciona el tercer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón

        cy.url().should('include', '/proyect/Gesti%C3%B3n');
    });
    it('Navegar a la pagina de controlador de tareas (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(2)
            .contains("Controlador Tareas")
            .click();
        cy.contains('Controlador Tareas');

    });
    it('Navegar a la pagina de sistema de inventarios (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.contains('Lista de Proyectos').should('be.visible'); // Título del Dashboard
        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(3)
            .contains("Sistema de Inventarios")
            .click();
        cy.contains('Sistema de Inventarios');

    });
    it('Navegar a la pagina de gestión de proyectos (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(4)
            .contains("Gestión de Proyectos")
            .click();
        cy.contains('Gestión de Proyectos');
    });
    it('Navegar a la pagina de Lista de Proyectos y cerrar sesión (Barra Lateral)', () => {
        cy.visit('/login');
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiPaper-root')
            .eq(1)
            .find('button')
            .click(); // Haz clic en el botón
        cy.get('.MuiListItem-root').eq(1)
            .contains("Cerrar sesión")
            .click();
        cy.contains('Bienvenido a To-Do List')
    });



});
