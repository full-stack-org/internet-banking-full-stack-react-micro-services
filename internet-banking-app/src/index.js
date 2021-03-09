import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter as Router } from "react-router-dom";
import { createStore, applyMiddleware } from "redux";
import { Provider } from "react-redux";
import thunkMiddleware from "redux-thunk";
import {composeWithDevTools} from 'redux-devtools-extension'
import rootReducer from "./components/redux/reducer";

const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(thunkMiddleware)));

ReactDOM.render(
  <Router>
    <React.StrictMode>
    <Provider store={store}>
      <App />
      </Provider>
    </React.StrictMode>
  </Router>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
