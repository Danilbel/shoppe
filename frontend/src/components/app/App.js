import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import MainPage from '../pages/MainPage';
import AuthorizationPage from '../pages/authorizationPage/AuthorizationPage';
import UserPage from '../pages/userPage/UserPage';
import PrivateRoute from '../privateRoute/PrivateRoute';
import Shop from '../pages/shop/Shop';

const App = () => {
    return (
        <Router>
            <div className='App'>
                <Routes>
                    <Route path={'/'} element={<MainPage />} />
                    <Route path={'/auth'} element={<AuthorizationPage />} />
                    <Route path={'/user'} element={<PrivateRoute Component={UserPage} />} />
                    <Route path={'/shop'} element={<Shop />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;