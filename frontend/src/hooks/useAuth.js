import { jwtDecode } from "jwt-decode";
import tokenService from "../services/token.service";

/**
 * Decodes the locally stored JWT (if any) into the current user's roles and username.
 *
 * @returns {{jwt: string|null, roles: string[], username: string|null}}
 */
export default function useAuth() {
    const jwt = tokenService.getLocalAccessToken();
    if (!jwt) return { jwt: null, roles: [], username: null };

    const decoded = jwtDecode(jwt);
    return { jwt, roles: decoded.authorities, username: decoded.sub };
}
