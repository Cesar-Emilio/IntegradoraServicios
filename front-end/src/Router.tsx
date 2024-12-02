import React from "react";
import { Route, Routes } from "react-router-dom";
import { HomePage } from "./pages/home";
import { LoginPage } from "./pages/login";
import { RouterLayout } from "./common/RouterLayout";
import { RegisterPage } from "./pages/register";

export const AppRouter: React.FC<{}> = () => {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            {/*<Route path="/" element={<RouterLayout />}>
            </Route> */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="*" element={<h1>Not Found</h1>} />
        </Routes>
    );
};
