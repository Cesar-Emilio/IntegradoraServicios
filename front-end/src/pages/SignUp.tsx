import React from "react";
import { useForm } from "react-hook-form";
import "../assets/SignUp.css";
import backgroundImage from '../assets/backgroundColorPurple.png';

interface SignUpFormInputs {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  password: string;
  confirmPassword: string;
}

const SignUp: React.FC = () => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<SignUpFormInputs>();

  const onSubmit = (data: SignUpFormInputs) => {
    console.log("Datos de registro:", data);
  };

  const password = watch("password");

  return (
    <div className="flex items-center justify-center min-h-screen" style={{
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat',
    }}>
      <div className="w-full max-w-md p-6 rounded-lg shadow-lg card">
        <h1 className="text-2xl font-bold text-center text-purple-600">Crear una cuenta</h1>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6 mt-6">
          {/* Nombre */}
          <div>
            <label htmlFor="firstName" className="block text-sm font-medium text-gray-700">
              Nombre
            </label>
            <input
              id="firstName"
              type="text"
              className="mt-2 w-full px-3 py-2 text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("firstName", { required: "El nombre es obligatorio" })}
            />
            {errors.firstName && (
              <p className="mt-1 text-sm text-red-500">{errors.firstName.message}</p>
            )}
          </div>

          {/* Apellidos */}
          <div>
            <label htmlFor="lastName" className="block text-sm font-medium text-gray-700">
              Apellidos
            </label>
            <input
              id="lastName"
              type="text"
              className="mt-2 w-full px-3 py-2  text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("lastName", { required: "Los apellidos son obligatorios" })}
            />
            {errors.lastName && (
              <p className="mt-1 text-sm text-red-500">{errors.lastName.message}</p>
            )}
          </div>

          {/* Correo */}
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Correo Electrónico
            </label>
            <input
              id="email"
              type="email"
              className="mt-2 w-full px-3 py-2  text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("email", { required: "El correo es obligatorio" })}
            />
            {errors.email && (
              <p className="mt-1 text-sm text-red-500">{errors.email.message}</p>
            )}
          </div>
            {/* Teléfono */}
            <div>
            <label htmlFor="phone" className="block text-sm font-medium text-gray-700">
              Teléfono
            </label>
            <input
              id="phone"
              type="tel"
              className="mt-2 w-full px-3 py-2  text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("phone", {
              required: "El teléfono es obligatorio",
              pattern: {
                value: /^[0-9]+$/,
                message: "El teléfono debe contener solo números",
              },
              minLength: {
                value: 10,
                message: "El teléfono debe tener al menos 10 dígitos",
              },
              maxLength: {
                value: 15,
                message: "El teléfono no debe tener más de 15 dígitos",
              },
              })}
            />
            {errors.phone && (
              <p className="mt-1 text-sm text-red-500">{errors.phone.message}</p>
            )}
            </div>

          {/* Contraseña */}
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
              Contraseña
            </label>
            <input
              id="password"
              type="password"
              className="mt-2 w-full px-3 py-2  text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("password", {
                required: "La contraseña es obligatoria",
                minLength: { value: 6, message: "La contraseña debe tener al menos 6 caracteres" },
              })}
            />
            {errors.password && (
              <p className="mt-1 text-sm text-red-500">{errors.password.message}</p>
            )}
          </div>

          {/* Confirmar contraseña */}
          <div>
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">
              Confirmar Contraseña
            </label>
            <input
              id="confirmPassword"
              type="password"
              className="mt-2 w-full px-3 py-2  text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"
              style={{ borderColor: '#9E00FF' }}
              {...register("confirmPassword", {
                required: "Por favor confirma tu contraseña",
                validate: (value) =>
                  value === password || "Las contraseñas no coinciden",
              })}
            />
            {errors.confirmPassword && (
              <p className="mt-1 text-sm text-red-500">{errors.confirmPassword.message}</p>
            )}
          </div>

          {/* Botón de registro */}
          <button
            type="submit"
            className="w-full py-2 text-white font-medium rounded"
            style={{ backgroundColor: '#9E00FF' }}
          >
            Registrarme
          </button>
        </form>

        {/* Enlace */}
        <div className="mt-4 text-center text-gray-700">
          <span>
            ¿Ya tienes una cuenta? <a href="/" className="cursor-pointer" style={{ color: 'black', fontWeight: 'bold' }}>Inicia Sesión</a>
          </span>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
