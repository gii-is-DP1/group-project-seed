import api from "./api";

/**
 * Returns the matches, optionally filtered.
 * @param {{name?: string, status?: "WAITING"|"PLAYING"|"FINISHED"}} [filters]
 */
export const getAllMatches = async (filters = {}) => {
    const res = await api.get("/matches", { params: filters });
    return res.data;
};

export const getMatchById = async (id) => {
    const res = await api.get(`/matches/${id}`);
    return res.data;
};

export const createMatch = async (match) => {
    const res = await api.post("/matches", match);
    return res.data;
};

export const updateMatch = async (id, match) => {
    const res = await api.put(`/matches/${id}`, match);
    return res.data;
};

export const deleteMatch = async (id) => {
    const res = await api.delete(`/matches/${id}`);
    return res.data;
};
