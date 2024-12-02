import { AlertColor } from "@mui/material";
import React from "react";
import { Notification } from "../components";

type ContextProps = {
    getError: (message: string) => void;
    getSuccess: (message: string) => void;
    getWarning: (message: string) => void;
    getInfo: (message: string) => void;
};

const NotificationContext = React.createContext<ContextProps | null>(null);

export const NotificationProvider: React.FC<{ children: JSX.Element }> = ({
    children,
}) => {
    const [message, setMessage] = React.useState("");
    const [open, setOpen] = React.useState(false);
    const [severity, setSeverity] = React.useState<AlertColor | undefined>(
        undefined
    );

    const getError = (message: string) => {
        setOpen(true);
        setMessage(message);
        setSeverity("error");
    };
    const getSuccess = (message: string) => {
        setOpen(true);
        setMessage(message);
        setSeverity("success");
    }
    const getWarning = (message: string) => {
        setOpen(true);
        setMessage(message);
        setSeverity("warning");
    }
    const getInfo = (message: string) => {
        setOpen(true);
        setMessage(message);
        setSeverity("info");
    }

    const handleClose = () => {
        setOpen(false);
    };

    const value = {
        getError,
        getSuccess,
        getWarning,
        getInfo,
    };

    return (
        <NotificationContext.Provider value={value}>
            <Notification
                handleClose={handleClose}
                open={open}
                severity={severity}
                message={message}
            />
            {children}
        </NotificationContext.Provider>
    );
};

export const useNotification = () => {
    const context = React.useContext(NotificationContext);
    if (!context) {
        throw new Error(
            "No existe un contexto"
        );
    }
    return context;
};
