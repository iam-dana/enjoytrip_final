<script setup>
// import { localAxios } from "@/utils/http-common";

// localAxios.interceptors.response.use(
//   response => response,
//   error => {
//     if (error.response && error.response.status === 302) {
//       // 로그인 페이지로 리다이렉트
//       window.location.href = '/user/login'; // 혹은 Vue Router를 통해 로그인 페이지로 이동
//     }
//     return Promise.reject(error);
//   }
// );
</script>

<template>
    <div>
        <div>플랜 View 입니다.</div>
        <router-view></router-view>
    </div>
</template>

<style scoped>

</style>