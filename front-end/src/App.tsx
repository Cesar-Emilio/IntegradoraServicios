import { BrowserRouter } from "react-router-dom";
import { AppRouter } from "./Router";
import { NotificationProvider } from "./context/notification.context";

function App() {
    return (
        <NotificationProvider>
            <BrowserRouter>
                <AppRouter></AppRouter>
            </BrowserRouter>
        </NotificationProvider>
    );
}

export default App;
