import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./pages/Login.tsx";
import SignUp from "./pages/SignUp.tsx";
import Dashboard from "./pages/Dashboard.tsx";
import ProtectedRoute from "./pages/ProtectedRoute.tsx";
import { AuthProvider } from "./auth/authProvider.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login />,
    },
    {
        path: "/signup",
        element: <SignUp />,
    },
    {
        path: "/",
        element: <ProtectedRoute />,
        children: [
            {
                path: "/dashboard",
                element: <Dashboard />,
            },
        ],
    },
]);

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <AuthProvider>
            <RouterProvider router={router} />
        </AuthProvider>
    </StrictMode>
);