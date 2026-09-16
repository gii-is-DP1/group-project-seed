import api from "./api";

export const getAllUsers = async () => {
    const res = await api.get("/users");
    return res.data;
};

export const getUserById = async (id) => {
    const res = await api.get(`/users/${id}`);
    return res.data;
};

export const getUserAuthorities = async () => {
    const res = await api.get("/users/authorities");
    return res.data;
};

export const createUser = async (user) => {
    const res = await api.post("/users", user);
    return res.data;
};

export const updateUser = async (id, user) => {
    const res = await api.put(`/users/${id}`, user);
    return res.data;
};

export const deleteUser = async (id) => {
    const res = await api.delete(`/users/${id}`);
    return res.data;
};
