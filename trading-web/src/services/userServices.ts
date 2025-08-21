import { apiRequest } from '../utils/apiUtils';

export const getUsers = async() => {
    const response = await apiRequest(`/api/user/get`, {
        method: "post",
        body: {

        }
    });
    return response;
};

export const updateUser = async() => {
    const response = await apiRequest(`/api/user/update`, {
        method: "put",
        body: {

        }
    });
    return response;
};

export const deleteUsers = async(ids: string[]) => {
    const response = await apiRequest(`/api/user/delete`, {
        method: "delete",
        body: {
            ids: ids,
        }
    });
    return response;
};