import { localAxios } from "@/utils/http-common";

const local = localAxios;

function listMention(success, fail) {
    const param = {
        userid: sessionStorage.getItem("userid"),
        access_token: sessionStorage.getItem("access_token")
    };
    local.post(`/mention/list`, param).then(success).catch(fail);
}

function updateMention(param, success, fail) {
    param.access_token = sessionStorage.getItem("access_token");
    param.userid = sessionStorage.getItem("userid");
    local.post(`/mention/update`, param).then(success).catch(fail);
}

export { listMention, updateMention }