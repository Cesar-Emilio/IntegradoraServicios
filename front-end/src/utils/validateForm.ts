import * as Yup from "yup";

export const LoginValidate = Yup.object().shape({
    password: Yup.string().required('La contraseña es requerida'),
    email: Yup.string().trim().email('Correo electónico invalido').required('El correo electrónico es requerido'),
});

export const RegisterValidate = Yup.object().shape({
    phone: Yup.number().min(100000000, "Ingrese un número valido").required('El teléfono es requerido'),
    lastname: Yup.string().required('El apellido es requerido'),
    name: Yup.string().required('El nombre es requerido'),
    password: Yup.string().required('La contraseña es requerida'),
    email: Yup.string().trim().email('Correo electónico invalido').required('El correo electrónico es requerido'),
});

export const ProyectValidate = Yup.object().shape({
    proyectDescription: Yup.string().trim()
        .required("La descripción es requerida")
        .max(200, "La descripción no puede tener más de 200 caracteres"),
    proyectAbreviation: Yup.string().trim()
        .required("La abreviación es requerida")
        .max(5, "La abreviación no puede tener más de 5 caracteres"),
    proyectName: Yup.string().trim()
        .required("El nombre del proyecto es requerido")
        .max(50, "El nombre del proyecto no puede tener más de 50 caracteres"),
  });


export const CategoryValidate = Yup.object().shape({
    description: Yup.string()
      .required("La descripción de la categoría es obligatoria")
      .max(200, "La descripción no puede superar los 200 caracteres"),
    name: Yup.string()
      .required("El nombre de la categoría es obligatorio")
      .max(50, "El nombre no puede superar los 50 caracteres"),
  });
  
export const TaskValidate = Yup.object().shape({
    responsibles_id: Yup.array()
        .of(Yup.number())
        .min(1, "Debe asignar un responsable"),
    category_id: Yup.number()
        .required("La categoría es obligatoria")
        .typeError("Debe seleccionar una categoría válida"),
    description: Yup.string()
        .required("La descripción de la tarea es obligatoria")
        .max(200, "La descripción no puede superar los 200 caracteres"),
    name: Yup.string()
        .required("El nombre de la tarea es obligatorio")
        .max(50, "El nombre no puede superar los 50 caracteres"),
  });