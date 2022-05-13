export interface Address {
    name: string
}

export interface User {
    email: string
}

export interface Movie {
    id: number,
    title: string,
    desc: string,
    url: string,
    img: string,
    user: User
}

export interface Page<T> {
    page: number,
    pageSize: number,
    totalPage: number,
    list: Array<T>
}

export interface UserForm {
    email: string,
    password: string
}

