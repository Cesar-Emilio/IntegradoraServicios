import React from "react";
import { useForm } from "react-hook-form";
import "../assets/Login.css";
import backgroundImage from '../assets/backgroundColorPurple.png';

interface LoginFormInputs {
  email: string;
  password: string;
}

const Login: React.FC = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormInputs>();

  const onSubmit = (data: LoginFormInputs) => {
    console.log("Datos del usuario:", data);
  };

  return (
    <div className="flex items-center justify-center min-h-screen" style={{
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat:   
       'no-repeat'
      }}>
      <div className="w-full max-w-md p-6 rounded-lg shadow-lg card ">
        <h1 className="text-2xl font-bold text-center text-purple-600">¡Bienvenido de nuevo!</h1>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6 mt-6">

          {/* correo /u/ */}
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Correo Electrónico
            </label>
            <input
              id="email"
              type="email"
              className="mt-2 w-full px-3 py-2 text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0" style={{borderColor: '#9E00FF'}}
              {...register("email", { required: "El correo es obligatorio" })}
            />
            {errors.email && (
              <p className="mt-1 text-sm text-red-500">{errors.email.message}</p>
            )}
          </div>

          {/* pasguord */}
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
              Contraseña
            </label>
            <input
              id="password"
              type="password"
              className="mt-2 w-full px-3 py-2 text-gray-900 border-b border-gray-300 focus:outline-none focus:ring-0"  style={{borderColor: '#9E00FF'}}
              {...register("password", { required: "La contraseña es obligatoria" })}
            />
            {errors.password && (
              <p className="mt-1 text-sm text-red-500">{errors.password.message}</p>
            )}
          </div>

          {/* Botón de Iniciar sesion */}
          <button
            type="submit"
            className="w-full py-2 text-white font-medium rounded" style={{backgroundColor: '#9E00FF'}}
          >
            Iniciar Sesión
          </button>
        </form>

        {/* Enlaces */}
        <div className="mt-4 text-center text-gray-700 space-y-2">
        <span
            className="cursor-pointer"
            onClick={() => alert("Función para recuperar contraseña")}
        >
            Recuperar contraseña
        </span><br></br>
        <span>
            ¿No tienes una cuenta? <a className="cursor-pointer" href="SignUp" style={{fontWeight: 'bold', color: 'black'}}>Registrate</a>
        </span>
        </div>
      </div>
    </div>
  );
};

export default Login;