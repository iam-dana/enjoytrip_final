import { localAxios } from "@/utils/http-common";

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

function checkCurrentEmail(param, success, fail) {
    local.post(`/user/checkcurrentemail`, param)
        .then(success)
        .catch(fail);  
}

function logout(success, fail) {
    local.get(`/user/logout`)
        .then(success)
        .catch(fail);
}

function islogin(success, fail) {
    local.get(`/user/islogin`)
        .then(success)
        .catch(fail);
}

function isRobot(param, success, fail) {
    local.post(`/user/isrobot`, param)
        .then(success)
        .catch(fail);  
}

export { isRobot, islogin, signupMember, loginMember, idCheckMember, changePw, changeUserInfo, deleteUser, checkCurrentEmail, logout };