import { combineReducers } from 'redux';
import appReducer from './index';


const reducers = combineReducers({
    app: appReducer
});

export default reducers;
export type RootState = ReturnType<typeof reducers>;
