export interface IErrorResponse {
    status?: string;
    message?: string;
    description?: string;
}
export interface IUserProfile {
    id?: number,
    firstName?: string;
    lastName?: string;
    sex?: number;
    dateOfBirth?: string;
    email?: string;
    phone?: string;
    address?: string;
    userType?: number;
    createdBy?: number;
    lastModifiedBy?: number;
    createdDate?: string;
    lastModifiedDate?: string;
}