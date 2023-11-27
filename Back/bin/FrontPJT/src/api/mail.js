import { localAxios } from "@/utils/http-common";

const local = localAxios;

function sendEmail(param, success, fail) {
    local.post(`mail/send`, param)
        .then(success)
        .catch(fail)
}

function confirmMail(param, success, fail) {
    local.post(`mail/confirm`, param)
        .then(success)
        .catch(fail)
}

export { sendEmail, confirmMail };