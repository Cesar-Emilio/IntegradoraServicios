export interface TypeUser {
    id: number;
    nombre: string;
    apellido: string;
    email: string;
    telefono: number;
    password: string;
    status: boolean;
    admin: string;
    proyects: any[];
    tasks: any[];
}
