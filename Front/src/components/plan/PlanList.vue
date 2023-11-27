<script setup>
import { ref, onMounted } from "vue";
import { listPlan, writePlanView } from "@/api/plan";
import { useRouter } from "vue-router";
import { localAxios } from "@/utils/http-common";

const plans = ref([]);

localAxios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 302) {
      // 로그인 페이지로 리다이렉트
      window.location.href = '/user/login'; // 혹은 Vue Router를 통해 로그인 페이지로 이동
    }
    return Promise.reject(error);
  }
);

onMounted(() => {
    listPlan(
        ({data}) => {
            console.log(data.planList);
            plans.value = data.planList;
        },
        (err) => {
            console.log(err);
        }
    )
})

const router = useRouter()

function moveWrite() {
    router.push({ name: "plan-write" })
}

</script>

<template>
    <div>
        <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
            여행 계획 하기
        </button>

        <table class="table">
            <thead>
                <tr class="text-conter">
                    <th scope="col">여행 계획 제목</th>
                    <th scope="col">여행자</th>
                    <th scope="col">작성일</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-conter" v-for="plan in plans" :key="plan.contentid">
                    <td>
                        <router-link :to="{name:'plan-view', params:{contentid:plan.contentid}}">
                            {{ plan.title }}
                        </router-link>
                    </td>
                    <td>{{ plan.userid }}</td>
                    <td>{{ plan.timestamp }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>

</style>