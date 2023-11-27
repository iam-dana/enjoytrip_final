import { localAxios } from "@/utils/http-common";

const local = localAxios;

function listMention(success, fail) {
    local.get(`/mention/list`).then(success).catch(fail);
}

function updateMention(param, success, fail) {
    local.post(`/mention/update`, param).then(success).catch(fail);
}

export { listMention, updateMention }