import { Dispatch } from 'redux';
import { Movie, Page, User} from '../../interface';
import { ActionType } from '../actionTypes';

export const login = (user: User) => (dispatch: Dispatch) => {
    dispatch({
        type: ActionType.LOGIN_SUCCESS,
        payload: user
    });
}

export const setUser = (user: User) => async (dispatch: Dispatch) => {
    dispatch({
        type: ActionType.SET_USER,
        payload: user
    });

}

export const setMoviePage = (page: Page<Movie>) => async (dispatch: Dispatch) => {
    dispatch({
        type: ActionType.SET_MOVIE_PAGE,
        payload: page
    })
}

