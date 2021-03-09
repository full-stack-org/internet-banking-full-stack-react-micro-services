import { combineReducers } from "redux";
import LoginReducer from "./LoginReducer";

const allReducers = combineReducers({
  loginReducer: LoginReducer,
});

export default allReducers;
