import React from "react";
import { Outlet } from "react-router-dom";
import { Box } from "@mui/material";
import { SideBar } from "./SideBar";

export const MenuLayout: React.FC<{}> = () => {
    return (
        <>
            <Box sx={{ display: 'flex' }}>
                <SideBar />
                <Box sx={{ flexGrow: 1, padding: 3 }}>
                    <Outlet />
                </Box>
            </Box>
        </>
    );
};
