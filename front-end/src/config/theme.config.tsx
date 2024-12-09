import { createTheme, CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";

type ThemeProp = {
    children: JSX.Element;
};

export enum themePalette {
    BG = "#12181B",
    LIME = "#C8FA5F",
    FONT_GLOBAL = "'Montserrat', serif",
    //Alerts styles
    ERROR_MAIN = "#F44336",
    BG_ERROR_MAIN = "rgba(244,67,54,0.1)",
    SUCCESS_MAIN = "#4CAF50",
    BG_SUCCESS_MAIN = "rgba(76,175,80,0.1)",
    WARNING_MAIN = "#FFC107",
    BG_WARNING_MAIN = "rgba(255,193,7,0.1)",
    INFO_MAIN = "#2196F3",
    BG_INFO_MAIN = "rgba(33,150,243,0.1)",
}

const theme = createTheme({
    palette: {
        mode: "dark",
        background: {
            default: themePalette.BG,
        },
        primary: {
            main: themePalette.LIME,
        },
        secondary: {
            main: themePalette.LIME,
        },
        error: {
            main: themePalette.ERROR_MAIN,
        },
    },
    typography: {
        fontFamily: themePalette.FONT_GLOBAL,
        fontSize: 18,
    },
    components: {
        MuiButton: {
            defaultProps: {
                style: {
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
        MuiAlert: {
            defaultProps: {
                style: {
                    borderRadius: "0.5em",
                    fontSize: "1em",
                },
            },
            styleOverrides: {
                standardInfo: {
                    backgroundColor: themePalette.BG_INFO_MAIN,
                    color: themePalette.INFO_MAIN,
                    border: `1px solid ${themePalette.INFO_MAIN}`,
                },
                standardWarning: {
                    backgroundColor: themePalette.BG_WARNING_MAIN,
                    color: themePalette.WARNING_MAIN,
                    border: `1px solid ${themePalette.WARNING_MAIN}`,
                },
                standardError: {
                    backgroundColor: themePalette.BG_ERROR_MAIN,
                    color: themePalette.ERROR_MAIN,
                    border: `1px solid ${themePalette.ERROR_MAIN}`,
                },
                standardSuccess: {
                    backgroundColor: themePalette.BG_SUCCESS_MAIN,
                    color: themePalette.SUCCESS_MAIN,
                    border: `1px solid ${themePalette.SUCCESS_MAIN}`,
                },
            },
        },
    },
});

export const ThemeConfig: React.FC<ThemeProp> = ({ children }) => {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            {children}
        </ThemeProvider>
    );
};
