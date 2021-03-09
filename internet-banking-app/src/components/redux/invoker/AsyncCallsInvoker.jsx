import axios from "axios";
import serviceUrlData from "../../common/service-url.json";

let internalAccountsUrl =
  serviceUrlData.devURI + serviceUrlData.internalAccountsListURI;
let externalAccountsUrl =
  serviceUrlData.devURI + serviceUrlData.externalAccountsListURI;

//Get the Internal Accounts
function getInternalAccounts(input,headers) {
  return axios.post(internalAccountsUrl, JSON.stringify(input.data), {
    headers,
  });
}

//Get the External Accounts
function getExternalAccounts(input,headers) {
  return axios.post(externalAccountsUrl, JSON.stringify(input.data), {
    headers,
  });
}


//Invoking Async Operations
export function getCustomerAccounts(input) {
    const headers = {
        Authorization: "Bearer " + input.token,
        "Content-Type": "application/json",
      };

  Promise.all([
    getInternalAccounts(input,headers),
    getExternalAccounts(input,headers),
  ])
    .then(function (results) {
      input.callbackSuccess(results);
    })
    .catch(function (error) {
      console.log(error);
      input.callbackFailure(error);
    });
}
