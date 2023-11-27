<script setup>
import { ref } from "vue";
const isNeedLogin = ref(true);

if (sessionStorage.getItem("userid")) {
  isNeedLogin.value = false;
} else {
  isNeedLogin.value = true;
}

function logout() {
  if (sessionStorage.getItem("userid")) {
    sessionStorage.removeItem("userid");
    sessionStorage.removeItem("access_token");
    sessionStorage.removeItem("refresh_token");

  }
}

</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <router-link :to="{name:'home'}"><img width="80" height="80" src="@/assets/new_main_icon.png"></router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'plan'}"><img width="40" height="40" src="@/assets/plan_icon.png"></router-link>
        </a>
      </li>
      <li v-if="isNeedLogin" class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'login'}"><img width="40" height="40" src="@/assets/login_icon.png"></router-link>
        </a>
      </li>
      <li v-if="isNeedLogin" class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'signup'}"><img width="40" height="40" src="@/assets/signup_icon.png"></router-link>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'mention'}"><img width="40" height="40" src="@/assets/mention_icon.png"></router-link>
        </a>
      </li>
      <li v-if="!isNeedLogin" class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'todos'}">TODO List</router-link>
        </a>
      </li>
      <!-- <li v-if="!isNeedLogin" class="nav-item"> -->
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'changePassword'}">비밀번호 변경</router-link>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">
            <router-link :to="{name:'changeuserinfo'}">회원정보 변경</router-link>
        </a>
      </li>
      <li v-if="!isNeedLogin" class="nav-item">
        <button @click="logout" aria-current="page">
           로그아웃
        </button>
      </li>
    </ul>
        </div>
      </div>
    </nav>


    <!-- <div class="d-flex flex-column flex-shrink-0 bg-light" style="width: 4.5rem;" >
    <a href="/" class="d-block p-3 link-dark text-decoration-none" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Icon-only">
      <router-link :to="{name:'home'}"><img width="45" height="50" src="@/assets/main_logo.png"></router-link>
    </a>
    <ul class="nav nav-pills nav-flush flex-column mb-auto text-center">
      <li>
        <a href="#" class="nav-link py-3 border-bottom" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Dashboard">
            <router-link :to="{name:'plan'}"><img width="40" height="40" src="@/assets/plan_icon.png"></router-link>
        </a>
      </li>
      <li>
        <a href="#" class="nav-link py-3 border-bottom" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Orders">
            <router-link :to="{name:'login'}"><img width="40" height="40" src="@/assets/login_icon.png"></router-link>
        </a>
      </li>
      <li>
        <a href="#" class="nav-link py-3 border-bottom" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Products">
            <router-link :to="{name:'signup'}"><img width="40" height="40" src="@/assets/signup_icon.png"></router-link>
        </a>
      </li>
      <li>
        <a href="#" class="nav-link py-3 border-bottom" title="" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-original-title="Customers">
            <router-link :to="{name:'mention'}"><img width="40" height="40" src="@/assets/mention_icon.png"></router-link>
        </a>
      </li>
    </ul> -->
    <!-- <div class="dropdown border-top">
      <a href="#" class="d-flex align-items-center justify-content-center p-3 link-dark text-decoration-none dropdown-toggle" id="dropdownUser3" data-bs-toggle="dropdown" aria-expanded="false">
        <img src="https://github.com/mdo.png" alt="mdo" width="24" height="24" class="rounded-circle">
      </a>
      <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser3">
        <li><a class="dropdown-item" href="#">New project...</a></li>
        <li><a class="dropdown-item" href="#">Settings</a></li>
        <li><a class="dropdown-item" href="#">Profile</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">Sign out</a></li>
      </ul>
    </div> -->


</template>

<style scoped>

</style>