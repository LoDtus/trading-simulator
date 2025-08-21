import { apiRequest } from '../utils/apiUtils';

export const getRank = async() => {
    const response = await apiRequest(`/api/rank/get`, {
        method: "post",
        body: {
            
        }
    });
    return response;
};