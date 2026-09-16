import api from "./api";

export const signin = async (credentials) => {
    const res = await api.post("/auth/signin", credentials);
    return res.data;
};

export const signup = async (user) => {
    const res = await api.post("/auth/signup", user);
    return res.data;
};

export const validateToken = async (token) => {
    const res = await api.get("/auth/validate", { params: { token } });
    return res.data;
};
