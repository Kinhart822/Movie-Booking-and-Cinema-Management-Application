import axios, {AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig} from "axios";
import queryString from 'query-string'
import {toastError} from "./toastNotification";

const headers: Readonly<Record<string, string | boolean>> = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    // 'Access-Control-Allow-Credentials': true,
    // 'X-Requested-With': 'XMLHttpRequest',
}

class Http {
    private instance: AxiosInstance | null = null;

    private get http(): AxiosInstance {
        return this.instance != null ? this.instance : this.initHttp();
    }

    initHttp() {
        const http = axios.create({
            baseURL: process.env.REACT_APP_API_URL,
            headers: headers as any,
            paramsSerializer: (params: any) => queryString.stringify(params),
            timeout: 5 * 60 * 1000,
        });
        const currentExecutingRequests: any = {}
        http.interceptors.request.use(
            async (req: InternalAxiosRequestConfig) => {
                const originalRequest = req;
                const accessToken = window.localStorage.getItem('accessToken')
                if (accessToken) {
                    (req.headers as any).Authorization = `Bearer ${accessToken}`;
                }
                return originalRequest;
            }, (error: any) => {
                Promise.reject(error);
            },
        )
        http.interceptors.response.use(
            (response: any) => {
                if (currentExecutingRequests[response.request.responseURL]) {
                    delete currentExecutingRequests[response.request.responseURL];
                }
                const responseData = response.data;
                if (responseData.status && parseInt(responseData.status) !== 200) {
                    const error = response;
                    if (responseData.message !== 'BAD_CREDENTIALS' &&
                        responseData.message !== 'EMAIL_NOT_FOUND'
                    ) {
                        toastError(error);
                    }
                    return Promise.reject(response);
                }
                return response;
            }, async (error: any) => {
                const originalRequest = error.config;
                if (axios.isCancel(error)) {
                    // here you check if this is a cancelled request to drop it silently (without error)
                    return new Promise(() => {});
                }
                if (currentExecutingRequests[originalRequest.url]) {
                    // here you clean the request
                    delete currentExecutingRequests[originalRequest.url];
                }
                if (
                    error.response?.status &&
                    error.response.status === 401 &&
                    error.response.config.url !== '/api/auth'
                ) {
                    window.localStorage.clear();
                    localStorage.removeItem('accessToken');
                    window.location.href = '/session/signin';
                    // originalRequest._retry = true
                }
                toastError(error);
                throw error;
            },
        )
        this.instance = http;
        return http;
    }
    request<T = any, R = AxiosResponse<T>>(
        config: AxiosRequestConfig,
    ): Promise<R> {
        return this.http.request(config);
    }
    get<T = any, R = AxiosResponse<T>>(
        url: string,
        config?: AxiosRequestConfig,
    ): Promise<R> {
        return this.http.get<T, R>(url, config);
    }
    post<T = any, R = AxiosResponse<T>>(
        url: string,
        data?: T,
        config?: AxiosRequestConfig,
    ): Promise<R> {
        return this.http.post<T, R>(url, data, config);
    }
    put<T = any, R = AxiosResponse<T>>(
        url: string,
        data?: T,
        config?: AxiosRequestConfig,
    ): Promise<R> {
        return this.http.put<T, R>(url, data, config);
    }
    delete<T = any, R = AxiosResponse<T>>(
        url: string,
        config?: AxiosRequestConfig,
    ): Promise<R> {
        return this.http.delete<T, R>(url, config);
    }
}

export const http = new Http();