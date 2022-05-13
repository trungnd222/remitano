import { Movie, Page, User } from "../../interface";

export enum ActionType {
    LOGIN_SUCCESS = "LOGIN_SUCCESS",
    SET_USER = "SET_USER",
    SET_MOVIE_PAGE = "SET_MOVIE_PAGE",
}

interface loginSuccess {
    type: ActionType.LOGIN_SUCCESS,
    payload: User;
}

interface setUser {
    type: ActionType.SET_USER
    payload: User;
}

interface setMoviePage {
    type: ActionType.SET_MOVIE_PAGE
    payload: Page<Movie>
}

export type Action = loginSuccess | setUser | setMoviePage