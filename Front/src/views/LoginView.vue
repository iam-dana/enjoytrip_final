<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { loginMember, isRobot} from "../api/member";
import LoadScript from 'vue-plugin-load-script'
import { localAxios } from "@/utils/http-common";
import vueRecaptcha from 'vue3-recaptcha2';

// const serviceKey = import.meta.env.VITE_KAKAO_LOGIN_SERVICE_KEY;
// const loginLink = `https://kauth.kakao.com/oauth/authorize?client_id=${serviceKey}&redirect_uri=https://localhost:8443/user/kakaologin&response_type=code`;
// const googleKey = import.meta.env.VITE_GOOGLE_CAPTCHA_KEY;

// const id = ref("");
// const pw = ref("");
// const msg = ref("");

// const router = useRouter();

// //카카오 로그인 버튼 클릭 시 실행되는 함수
// async function kakaoLogin() {
//     window.location.href = loginLink;
// }

// const login = () => {
//   const body = {
//     userid: id.value, 
//     userpassword: pw.value
//   }
//   console.log(body);
//   loginMember(body, ({ data }) => {
//     //console.log(data.userid);
//     if (data.msg == null) {
//       alert("로그인 성공");
//       router.push({name: "home"})
//     } else {
//       if (data.msg == "needPwdChange") {
//         console.log(data.msg);
//         router.push({ name: "choice" })
//       }
//       else {
//         alert(data.msg);
//       }
//       // msg.value = data.msg;
//     }
//   })
// };



export default {
  setup() {
    const serviceKey = import.meta.env.VITE_KAKAO_LOGIN_SERVICE_KEY;
    const loginLink = `https://kauth.kakao.com/oauth/authorize?client_id=${serviceKey}&redirect_uri=https://localhost:8443/user/kakaologin&response_type=code`;
    const googleKey = import.meta.env.VITE_GOOGLE_CAPTCHA_KEY;

    const id = ref("");
    const pw = ref("");
    const msg = ref("");

    const router = useRouter();

    //카카오 로그인 버튼 클릭 시 실행되는 함수
    async function kakaoLogin() {
        window.location.href = loginLink;
    }

    const login = () => {
      const body = {
        userid: id.value, 
        userpassword: pw.value
      }
      console.log(body);
      loginMember(body, ({ data }) => {
        //console.log(data.userid);
        if (data.msg == null) {
          alert("로그인 성공");
          router.push({name: "home"})
        } else {
          if (data.msg == "needPwdChange") {
            console.log(data.msg);
            router.push({ name: "choice" })
          }
          else {
            alert(data.msg);
          }
          // msg.value = data.msg;
        }
      })
    };
    return {
      serviceKey, loginLink, googleKey, id, pw, msg, router, kakaoLogin, login
    }

  },
  name: 'app',
  components: {
    vueRecaptcha,
  },
  data() {
	return {
		showRecaptcha: true,
    loadingTimeout: 30000,  // 30 seconds

	}
  },
  methods: {
    recaptchaVerified(response) {
      const body = {
        "rResponse" : response
    }
      isRobot(body, ({ data }) => {
      console.log(data)
      })
	},
    recaptchaExpired() {
      const body = {
        "rResponse" : "만료"
    }
      isRobot(body, ({ data }) => {
      console.log(data)
      })
    },
    recaptchaFailed() {
    },
    recaptchaError(reason) {
    }
  }
};

</script>

<template>
    <div class="box">
    ID : <input v-model="id" class="form-control" />

    PW : <input type="password" v-model="pw" class="form-control" />
    <!-- <div>{{ msg }}</div> -->
    <button type="button" class="btn btn-outline-info btn-sm" @click="login">
      login
    </button>
  </div>
  <br>
    <img @click="kakaoLogin" src="@/assets/kakao_login_large.png">

    <vue-recaptcha v-show="showRecaptcha" sitekey="6LfiTBcpAAAAABD8UuV3vAW0apxnoDreSuWugTkV"
		 size="normal" 
		 theme="light"
		 hl="ko"
		 :loading-timeout="0"
		 @verify="recaptchaVerified"
		 @expire="recaptchaExpired"
		 @fail="recaptchaFailed"
		 @error="recaptchaError"
		 ref="vueRecaptcha">
  </vue-recaptcha>


</template>

<style scoped>

</style>
