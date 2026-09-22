import api from "./api";

export const getAllDevelopers = async () => {
    const res = await api.get("/developers");
    return res.data;
};
