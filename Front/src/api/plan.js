import { localAxios } from "@/utils/http-common";

const local = localAxios;

function listPlan(success, fail) {
    local.get(`/plan/list`).then(success).catch(fail);
}

function detailPlan(contentid, success, fail) {
    local.get(`/plan/view/${contentid}`).then(success).catch(fail);
}

function writePlan(param, success, fail) {
    local.post(`/plan/regist`, param).then(success).catch(fail);
}

function writePlanView(success, fail) {
    local.get(`plan/regist`).then(success).catch(fail);
}

function updatePlanView(contentid, success, fail) {
    local.get(`/plan/update/${contentid}`).then(success).catch(fail);
}

function updatePlan(param, success, fail) {
    local.post(`/plan/update`, param).then(success).catch(fail);
}

function deletePlan(contentid, success, fail) {
    local.get(`/plan/delete/${contentid}`).then(success).catch(fail);
}

export { listPlan, detailPlan, writePlan, writePlanView, updatePlanView, updatePlan, deletePlan }