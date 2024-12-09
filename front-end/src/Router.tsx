import React from "react";
import { Route, Routes } from "react-router-dom";
import { HomePage } from "./pages/home";
import { LoginPage } from "./pages/login";
import { RegisterPage } from "./pages/register";
import { DashboardPage } from "./pages/dashboard";
import NotFound from "./pages/404";
import { ProjectPage } from "./pages/proyect";
import { MenuLayout } from "./common/MenuLayout";
import { UserPage } from "./pages/profile";
import { ChangePasswordPage } from "./pages/authPassword";
import { PasswordSolicitudePage } from "./pages/passwordSolicitude";
import { ResetPasswordSolicitudePage } from "./pages/resetPasswordSolicitude";

export const AppRouter: React.FC<{}> = () => {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/forgotPassword" element={< PasswordSolicitudePage />} />
            <Route path="/reset-password" element={<ResetPasswordSolicitudePage />} />
            <Route path="/" element={<MenuLayout />} >
                <Route path="/proyect/:projectId/:id" element={<ProjectPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/profile" element={<UserPage />} />
                <Route path="/changePassword" element={<ChangePasswordPage />} />
            </Route>
            <Route path="*" element={<NotFound />} />
        </Routes>
    );
};
