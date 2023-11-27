<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailPlan, deletePlan } from "@/api/plan";

const route = useRoute();

const plan = ref({});

function getPlan(contentid) {
    detailPlan(
        contentid,
        ({data}) => {
            console.log(data);
            plan.value = data;
        },
        (err) => {
            console.log(err);
        }
    )
}

onMounted(() => {
    console.log(route.params.contentid);
    getPlan(route.params.contentid);
})

const router = useRouter();

function moveList() {
    router.push({name:'plan-list'})
}

function moveUpdate() {
    router.push({ name: 'plan-update', params: { contentid: plan.contentid } })
}

function delPlan(contentid) {
    deletePlan(
        contentid,
        ({data}) => {
            console.log(data);
            alert(data.msg);
            moveList();
        },
        (err) => {
            console.log(err);
        }
    )
}

</script>

<template>
    <div>
        <h1>계획 보기</h1>
        <table class="table">
        <thead>
            <tr>
            <th scope="col">제목</th>
            <th scope="col">{{ plan.title }}</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <th scope="row">여행자</th>
            <td>{{ plan.userid }}</td>
            </tr>
            <tr>
            <th scope="row">작성일</th>
            <td>{{ plan.timestamp }}</td>
            </tr>
            <tr>
            <th scope="row">내용</th>
            <td>{{ plan.content }}</td>
            </tr>
            <tr>
            <th scope="row">언급한 사용자</th>
            <td>{{ plan.mentionList }}</td>
            </tr>
        </tbody>
        </table>
        <button class="btn btn-outline-primary btn-sm" @click="moveUpdate">
            계획 수정
        </button>
        <button class="btn btn-outline-primary btn-sm" @click="delPlan(plan.contentid)">
            계획 삭제
        </button>
        <button class="btn btn-outline-primary btn-sm" @click="moveList">
            목록으로
        </button>
    </div>
</template>

<style scoped>

</style>