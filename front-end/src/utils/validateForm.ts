import * as Yup from "yup";

export const LoginValidate = Yup.object().shape({
    password: Yup.string().required('La contraseña es requerida'),
    email: Yup.string().trim().email('Correo electónico invalido').required('El correo electrónico es requerido'),
});

export const RegisterValidate = Yup.object().shape({
    phone: Yup.number().required('El teléfono es requerido'),
    lastname: Yup.string().required('El apellido es requerido'),
    name: Yup.string().required('El nombre es requerido'),
    password: Yup.string().required('La contraseña es requerida'),
    email: Yup.string().trim().email('Correo electónico invalido').required('El correo electrónico es requerido'),
});