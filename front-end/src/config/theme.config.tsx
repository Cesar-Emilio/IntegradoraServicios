import { createTheme, CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";

type ThemeProp = {
    children: JSX.Element;
};

export enum themePalette {
    BG = "#12181B",
    LIME = "#C8FA5F",
    FONT_GLOBAL = "'Montserrat', serif",

}

const theme = createTheme({
    palette:{
        mode:"dark",
        background:{
            default:themePalette.BG,
        },
        primary:{
            main:themePalette.LIME,
        },
    },
    typography:{
        fontFamily:themePalette.FONT_GLOBAL,
        fontSize:18,
    },
    components:{
        MuiButton:{
            defaultProps:{
                style:{
                    textTransform: "none",
                    boxShadow: "none",
                    borderRadius: "0.5em",
                },
            },
        },
        MuiFormLabel: {
            styleOverrides: {
              asterisk: {
                color: "red",
              },
            },
        },
    },
});

export const ThemeConfig: React.FC<ThemeProp> = ({children}) => {
    return (
        <ThemeProvider theme = {theme}>
            <CssBaseline/>
            {children}
        </ThemeProvider>
    );
};