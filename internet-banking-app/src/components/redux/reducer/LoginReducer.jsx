import {
    GET_LOGIN_REQUEST,
    GET_LOGIN_SUCCESS,
    GET_LOGIN_FAILURE,
  } from "../constants/InternetConstants";

  const initialData = {
    isLoading: false,
    data: {},
    error: "",
  };
  
  export const loginReducer = (state = initialData, action) => {
    switch (action.type) {
      case GET_LOGIN_REQUEST:
        return {
          ...state,
          isLoading: true,
        };
      case GET_LOGIN_SUCCESS:
        return {
          isLoading: false,
          data: action.payload,
          error: "",
        };
      case GET_LOGIN_FAILURE:
        return {
          isLoading: false,
          data: {},
          error: action.payload,
        };
      default:
        return state;
    }
  };
  
  export default loginReducer;