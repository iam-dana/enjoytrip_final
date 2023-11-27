import { localAxios } from "@/utils/http-common";
``
const local = localAxios;

function signupMember(param, success, fail) {
    local.post(`/user/signup`, param)
        .then(success)
        .catch (fail);
} 

function loginMember(param, success, fail) {
    // console.log(param);
    local.post(`/user/login`, param)
        .then(success)
        .catch(fail);
} 

function idCheckMember(param, success, fail) {
    console.log(param);
    local.post(`/user/idcheck`, param)
        .then(success)
        .catch(fail);  
}

function changePw(param, success, fail) {
    console.log(param);
    local.post(`/user/changepassword`, param)
        .then(success)
        .catch(fail);  
}

function changeUserInfo(param, success, fail) {
    console.log(param);
    local.post(`/user/changeuserinfo`, param)
        .then(success)
        .catch(fail);  
}

function deleteUser(param, success, fail) {
    local.post(`/user/deleteuser`, param)
        .then(success)
        .catch(fail);  
}

export { signupMember, loginMember, idCheckMember, changePw, changeUserInfo, deleteUser };