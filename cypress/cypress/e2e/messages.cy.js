describe('Navigation Tests', () => {
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('button[type="submit"]').click();
        cy.contains('El correo electrónico es requerido');

    });
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.get('button[type="submit"]').click();
        cy.contains('La contraseña es requerida');

    });
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();
        cy.contains('El nombre es requerido');

    });
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('input[name="name"]').type('Erick');
        cy.get('button[type="submit"]').click();
        cy.contains('El apellido es requerido');

    });
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('input[name="name"]').type('Erick');
        cy.get('input[name="lastname"]').type('Teja');
        cy.get('button[type="submit"]').click();
        cy.contains('Ingrese un número valido');

    });
    it('Navegar a la pagina de login y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.url().should('include', '/login');
        cy.get('button[type="submit"]').click();
        cy.contains('El correo electrónico es requerido');

    });
    it('Navegar a la pagina de login y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('erickhumberto.teja@gmail.com');
        cy.url().should('include', '/login');
        cy.get('button[type="submit"]').click();
        cy.contains('La contraseña es requerida');

    });
    it('Navegar a la pagina de login y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('erickhumbetotc@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.url().should('include', '/login');
        cy.get('button[type="submit"]').click();
        cy.contains('Inicio de sesión exitoso');

    });
    it('Navegar a la pagina de registro y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Registrarte').click();
        cy.url().should('include', '/register');
        cy.get('input[name="email"]').type('elegidokawai.lindo@gmail.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('input[name="name"]').type('Erick');
        cy.get('input[name="lastname"]').type('Teja');
        cy.get('input[name="phone"]').type('7774806546');
        cy.get('button[type="submit"]').click();
        cy.contains('Se ha registrado correctamente');

    });

    it('Navegar a la interfaz de crear un proyecto y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('button').eq(2).click();
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('El nombre del proyecto es requerido');

    });

    it('Navegar a la interfaz de crear un proyecto y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('button').eq(2).click();
        cy.get('.MuiInputBase-input')
            .eq(0) // Si hay varios, puedes usar el índice
            .type('Nombre del proyecto');
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('La abreviación es requerida');

    });

    it('Navegar a la interfaz de crear un proyecto y revisar los mensajes del sistema', () => {
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
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('La descripción es requerida');

    });

    it('Navegar a la interfaz de crear un proyecto y revisar los mensajes del sistema', () => {
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

    it('Navegar a la interfaz de crear una categoria y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(1) // Selecciona el primer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón

        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary').eq(0).click();
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('Error al crear la categoría');
        cy.visit('/dashboard')

    });

    it('Navegar a la interfaz de crear una categoria y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(1) // Selecciona el primer div con clase "card"
            .find('a').contains("Ver Detalles") // Encuentra el botón
            .click(); // Haz clic en el botón

        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary').eq(0).click();
        cy.get('.MuiInputBase-input')
            .eq(0) // Si hay varios, puedes usar el índice
            .type('Nombre de una Categoria');
        cy.get('.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium').eq(1).click();
        cy.contains('Error al crear la categoría');
        cy.visit('/dashboard')

    });

    it('Navegar a la interfaz de crear una categoria y revisar los mensajes del sistema', () => {
        cy.visit('/');
        cy.get('a').contains('Iniciar sesión').click();
        cy.get('input[name="email"]').type('admin@admin.com');
        cy.get('input[name="password"]').type('12345');
        cy.get('button[type="submit"]').click();

        cy.get('.MuiCardActions-root').eq(1) // Selecciona el primer div con clase "card"
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
