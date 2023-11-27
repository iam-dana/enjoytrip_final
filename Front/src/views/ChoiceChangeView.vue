<script setup>

import { useRouter } from "vue-router";
const router = useRouter();

function moveToChange() {
    router.push({ name: "changePassword" });
}

function moveToHome() {
    router.push({ name: "home" });
}

</script>

<template>
    <div>
        비밀번호를 설정한 지 3개월이 지났습니다.
        <br>
        <button @click="moveToChange">변경하기</button> <button @click="moveToHome">다음에 변경하기</button>
    </div>
</template>

<style scoped>

</style>