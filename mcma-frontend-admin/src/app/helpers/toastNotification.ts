import { toast } from "react-toastify";

enum StatusCode {
    BadRequest = 400,
    Unauthorized = 401,
    Forbidden = 403,
    TooManyRequests = 429,
    InternalServerError = 500,
}
export const toastError = (error: {
    data?: any,
    code?: any,
    response?: any,
    message?: string,
    request?: any,
}) => {
    let message = null;
    if (error.data) {
        message = error.data.description ?? error.message ?? '';
    } else if (error.code) {
        if (error.code === 'ECONNABORTED') {
            message = 'No responses from server';
        }
    } else if (error.response) {
        const status = error.response;
        switch (status) {
            case StatusCode.Unauthorized:
                message = 'Unauthorized';
                break;
            case StatusCode.BadRequest:
                message = 'BadRequest';
                break;
            case StatusCode.InternalServerError:
                message = 'InternalServerError';
                break;
            default:
                message = 'Unexpected server error. Try again later';
                break;
        }
    } else if (error.message) {
        ({message: message} = error);
        if (message === 'Network Error') {
            message = 'Cannot connect to server';
        }
    } else {
        message = `${error.request}` as string;
    }
    toast.error(message, { toastId: 'custom-error-id'})
}
export const toastSuccess = (success: { message: string}) => {
    let message: string;
    ({ message: message } = success);
    toast.success(message, { toastId: 'custom-success-id'})
}
export const toastWarning = (warning : { message: string }) => {
    toast.warning(warning.message, { toastId: 'custom-warning-id'});
}