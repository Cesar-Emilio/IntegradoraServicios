import { Alert, AlertColor, Snackbar, Typography } from "@mui/material";
import React from "react";

type NotificationProps = {
    open: boolean;
    message: string;
    severity: AlertColor | undefined;
    handleClose: () => void;
};

export const Notification: React.FC<NotificationProps> = ({
    open,
    message,
    severity,
    handleClose,
}) => {
    return (
        <Snackbar
            open={open}
            autoHideDuration={5000}
            onClose={handleClose}
            anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
        >
            <Alert
                onClose={handleClose}
                severity={severity}
                sx={{ width: "100%" }}
            >
                <Typography>{message}</Typography>
            </Alert>
        </Snackbar>
    );
};
