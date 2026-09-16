import { useEffect, useState } from 'react';
import tokenService from '../services/token.service';
import * as authApi from '../services/auth';
import Login from '../auth/login';

const PrivateRoute = ({ children }) => {
    const jwt = tokenService.getLocalAccessToken();
    const [isLoading, setIsLoading] = useState(true);
    const [isValid, setIsValid] = useState(null);

    useEffect(() => {
        if (!jwt) {
            setIsLoading(false);
            return;
        }

        let ignore = false;

        async function validate() {
            const valid = await authApi.validateToken(jwt);
            if (!ignore) {
                setIsValid(valid);
                setIsLoading(false);
            }
        }

        validate();

        return () => {
            ignore = true;
        };
    }, [jwt]);

    if (!jwt) return <Login message={null} navigation={false} />;
    if (isLoading) return <div>Loading...</div>;
    return isValid === true
        ? children
        : <Login message="Your token has expired. Please, sign in again." navigation={true} />;
};

export default PrivateRoute;
