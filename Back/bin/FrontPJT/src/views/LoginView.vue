<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { loginMember } from "../api/member";


const id = ref("");
const pw = ref("");
const msg = ref("");

const router = useRouter();

const login = () => {
  const body = {
    userid: id.value, 
    userpassword: pw.value
  }
  loginMember(body, ({ data }) => {
    //console.log(data.userid);
    if (data.msg == null) {
      sessionStorage.setItem("userid", data.userid);
      sessionStorage.setItem("access_token", data.access_token);
      sessionStorage.setItem("refresh_token", data.refresh_token);

      router.push({name: "home"})
    } else {
      if (data.msg == "needPwdChange") {
        console.log(data.msg);
        sessionStorage.setItem("userid", data.userid);
        sessionStorage.setItem("access_token", data.access_token);
        sessionStorage.setItem("refresh_token", data.refresh_token);
        router.push({ name: "choice" })
      }
      else {
        alert(data.msg);
      }
      // msg.value = data.msg;
    }
  })
};
</script>

<template>
    <div class="box">
    ID : <input v-model="id" class="form-control" />

    PW : <input v-model="pw" class="form-control" />
    <!-- <div>{{ msg }}</div> -->
    <button type="button" class="btn btn-outline-info btn-sm" @click="login">
      login
    </button>

    <a href="https://kauth.kakao.com/oauth/authorize?client_id=99dfb3a8f2095c9bb4dd96ce345a7c3e&redirect_uri=https://localhost:8443/user/kakaologin&response_type=code">
      <img src="@/assets/kakao_login_small.png">
    </a>

  </div>
  <br>
  <br>
  <br>
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=daad1a19aba64000fb178eb96ad2889d&redirect_uri=https://localhost:8443/user/kakaologin&response_type=code">
    <img src="@/assets/kakao_login_large.png">
  </a>



</template>

<style scoped>

</style>
