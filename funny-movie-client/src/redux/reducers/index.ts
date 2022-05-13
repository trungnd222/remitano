import { Movie, Page, User } from '../../interface';
import { Action, ActionType } from '../actionTypes/index';

interface State {
    user: User;
    moviePage: Page<Movie>;
}

const initialState = {
    user: null as unknown as User,
    moviePage: null as unknown as Page<Movie>,
}

const appReducer = (state: State = initialState, action: Action): State => {
    switch (action.type) {
        case ActionType.SET_USER:
            return {
                ...state,
                user: action.payload
            }
        case ActionType.LOGIN_SUCCESS:
            return {
                ...state,
                user: action.payload
            }
        case ActionType.SET_MOVIE_PAGE:
            return {
                ...state,
                moviePage: action.payload
            }
        default:
            return state;
    }
}

export default appReducer;