import React, { useState } from 'react';
import { Paper, IconButton, Box, Drawer, Typography } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';

export const SideBar: React.FC = () => {
    const [open, setOpen] = useState(false);

    const toggleDrawer = (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
        setOpen(open);
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <Paper 
                sx={{ 
                    width: 100, 
                    height: '100vh', 
                    display: 'flex', 
                    flexDirection: 'column',
                    paddingTop: 2
                }}
                elevation={3}
            >
                <IconButton sx={{ color: 'white'}} onClick={toggleDrawer(true)}>
                    <MenuIcon />
                </IconButton>

                <Drawer anchor="left" open={open} onClose={toggleDrawer(false)}>
                    <Box sx={{ width: 400, padding: 2 }}>
                        <Typography variant="h6">Menú</Typography>
                        <Typography variant="body2">
                            skibidi toilet la grasa papu roblox
                        </Typography>
                    </Box>
                </Drawer>
            </Paper>
        </Box>
    );
};
