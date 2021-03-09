import axios from "axios";

export function fetchAPIDataPost(input) {
  const headers = {
    Authorization: "Bearer " + input.token,
    "Content-Type": "application/json",
  };

  axios
    .post(input.url, JSON.stringify(input.data), {
      headers,
    })
    .then((response) => {
      console.log("Final Response: ", response.data);
      input.callbackSuccess(response.data);
    })
    .catch((error) => {
      console.log("Error Message is: ", error.message);
      input.callbackFailure(error);
    });
}

export function fetchAPIDataPostWithOutJWTToken(input) {
  const headers = {
    "Content-Type": "application/json",
  };

  axios
    .post(input.url, JSON.stringify(input.data), {
      headers,
    })
    .then((response) => {
      console.log("Final Response: ", response.data);
      input.callbackSuccess(response.data);
    })
    .catch((error) => {
      console.log("Error Message is: ", error.message);
      input.callbackFailure(error);
    });
}
