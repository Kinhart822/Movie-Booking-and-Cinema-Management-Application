import {IAuthPayload} from "../../models/auth";
import {IErrorResponse, ISuccessResponse, IUserProfile} from "../../models/common";
import {http} from "../../helpers/http-config";
import {AxiosResponse} from 'axios';

export const loginUser = async (
    payload: IAuthPayload,
    ): Promise<{ accessToken?: string }> => {
    const { data } = await http.post<
        any,
        AxiosResponse<{ accessToken?: string }>>(
            '/auth/sign-in', payload);
    return data;
}

export const getProfile = async (): Promise<IUserProfile> => {
    const { data } = await http.get<IUserProfile>('/auth/profile');
    return data;
}

export const resetPasswordInit = async (payload: {
    email: string,
    type: number,
}): Promise<ISuccessResponse | IErrorResponse> => {
    const { data } = await http.post<
        any,
        AxiosResponse<ISuccessResponse | IErrorResponse>
    >('/auth/reset-password/request', payload);
    return data;
}

export const resetPasswordCheck = async (params: {
    resetKey: string,
    type: number,
}): Promise<ISuccessResponse | IErrorResponse> => {
    const { data } = await http.get<
        any,
        AxiosResponse<ISuccessResponse | IErrorResponse>
    >('/auth/reset-password/check', { params });
    return data;
}

export const resetPasswordFinish = async (payload: {
    resetKey: string,
    type: number,
    newPassword: string,
}): Promise<ISuccessResponse | IErrorResponse> => {
    const { data } = await http.post<
        any,
        AxiosResponse<ISuccessResponse | IErrorResponse>
    >('/auth/reset-password/finish', payload);
    return data;
}