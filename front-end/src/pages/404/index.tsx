import { Box, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';

const NotFound = () => {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        textAlign: 'center',
      }}
    >
      <Typography variant="h2" color="text.primary">
        404
      </Typography>
      <Typography variant="h6" color="text.secondary" sx={{ marginBottom: 2 }}>
        La página que buscas no existe.
      </Typography>
      <Link to="/dashboard">
        <Button variant="contained" color="primary">
          Volver al Inicio
        </Button>
      </Link>
    </Box>
  );
};

export default NotFound;