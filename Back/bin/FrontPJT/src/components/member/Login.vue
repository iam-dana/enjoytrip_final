<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const id = ref("");
const pw = ref("");

const router = useRouter();

const login = () => {
    console.log(id.value, pw.value);
    //login ok...
    sessionStorage.setItem("loginId", id.value);
    router.push({ name: "/" });
};
</script>

<template>
  <div class="box">
    ID : <input v-model="id" class="form-control" />

    PW : <input v-model="pw" class="form-control" />
    <button type="button" class="btn btn-outline-info btn-sm" @click="login">
      login
    </button>

    

  </div>
</template>

<style scoped>
</style>