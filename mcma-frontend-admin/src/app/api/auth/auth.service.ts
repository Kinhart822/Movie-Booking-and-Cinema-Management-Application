import {IAuthPayload} from "../../models/auth";
import {IUserProfile} from "../../models/common";
import {AxiosResponse} from "axios";
import {http} from "../../helpers/http-config";

export const loginUser = async (
    payload: IAuthPayload,
    ): Promise<{ accessToken?: string }> => {
    const { data } = await http.post<
        any,
        AxiosResponse<{ accessToken?: string }>>(
            '/auth/sign-up', payload);
    return data;
}

export const getProfile = async (): Promise<IUserProfile> => {
    const { data } = await http.get<IUserProfile>('/auth/profile');
    return data;
}