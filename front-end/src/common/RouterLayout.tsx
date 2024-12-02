import React from "react";
import { NavBar } from "./NavBar";
import { Outlet } from "react-router-dom";

export const RouterLayout: React.FC<{}> = () => {
    return (
        <>
            <NavBar />
            <Outlet />
        </>
    );
}