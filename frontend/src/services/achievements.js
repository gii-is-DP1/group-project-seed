import api from "./api";

export const getAllAchievements = async () => {
    const res = await api.get("/achievements");
    return res.data;
};

export const getAchievementById = async (id) => {
    const res = await api.get(`/achievements/${id}`);
    return res.data;
};

export const createAchievement = async (achievement) => {
    const res = await api.post("/achievements", achievement);
    return res.data;
};

export const updateAchievement = async (id, achievement) => {
    const res = await api.put(`/achievements/${id}`, achievement);
    return res.data;
};

export const deleteAchievement = async (id) => {
    const res = await api.delete(`/achievements/${id}`);
    return res.data;
};
