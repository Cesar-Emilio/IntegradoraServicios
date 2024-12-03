import React from "react";
import { Route, Routes } from "react-router-dom";
import { HomePage } from "./pages/home";
import { LoginPage } from "./pages/login";
import { RegisterPage } from "./pages/register";
import { DashboardPage } from "./pages/dashboard";
import NotFound from "./pages/404";
import { ProjectPage } from "./pages/proyect";
import { MenuLayout } from "./common/MenuLayout";

export const AppRouter: React.FC<{}> = () => {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/" element={<MenuLayout />} >
                <Route path="/proyect/:projectId" element={<ProjectPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
            </Route>
            <Route path="*" element={<NotFound />} />
        </Routes>
    );
};
